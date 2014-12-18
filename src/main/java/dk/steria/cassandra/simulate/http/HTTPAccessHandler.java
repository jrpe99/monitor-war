package dk.steria.cassandra.simulate.http;

import java.util.Random;

import dk.steria.cassandra.db.ConnectionHandler;
import java.text.DateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalField;
import java.util.Calendar;

public class HTTPAccessHandler {
    public static void main(String[] args) {
        HTTPAccessHandler client = new HTTPAccessHandler();
        //client.createSchema();
        client.simulate();
    }

    public void simulate() {
        ConnectionHandler conn = null;
        try {
            Random random = new Random();
            conn = new ConnectionHandler();
            conn.connect();
            while(true) {
                int index = random.nextInt(SimulationConstants.httpStatusList.size());
                String httpStatus = SimulationConstants.httpStatusList.get(index);
                
                index = random.nextInt(9)*10;
                String ipAddress = "1"+index+".";
                index = random.nextInt(9)*10;
                ipAddress = ipAddress + "132.100."+"2"+index;
                
                index = random.nextInt(SimulationConstants.actionList.size());
                String action = SimulationConstants.actionList.get(index);
                
                index = random.nextInt(SimulationConstants.urlList.size());
                String url = SimulationConstants.urlList.get(index);
                
                ZonedDateTime now = ZonedDateTime.now().withSecond(0).withNano(0);
                int hour = now.getHour();
                long epoch = now.toEpochSecond();
                
                logHttpAccess(conn, hour, httpStatus, ipAddress, action, url, epoch);

                DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
                String date = formatter.format(now);
                
                formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
                String dateToMinute = formatter.format(now);
                
                if(httpStatus.equals("200")) {
                    logHttpSuccess(conn, ipAddress);
                    logHttpSuccessPerMinute(conn, date, dateToMinute);
                } else {
                    logHttpFailed(conn, ipAddress);
                    logHttpFailedPerMinute(conn, date, dateToMinute);
                }
                
                int sleepTime = random.nextInt(500);
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                }
            }
        } finally {
            if(conn != null)conn.close();
        }
    }
    
    public void logHttpAccess(ConnectionHandler conn, int hour, String httpStatus, String ipAddress, String action, String url, long time) {
        StringBuilder cql = new StringBuilder(); 
        cql.append("INSERT INTO httpaccess.http_access (hour,ip_address,http_status,action,url,date_to_minute) ");
        cql.append("VALUES ");
        cql.append("(");
        cql.append(hour).append(",");
        cql.append("'").append(ipAddress).append("',");
        cql.append("'").append(httpStatus).append("',");
        cql.append("'").append(action).append("',");
        cql.append("'").append(url).append("',");
        cql.append("'").append(time).append("'");
        cql.append(")");
        conn.execute(cql.toString());
    }
    
    public void logHttpSuccess(ConnectionHandler conn, String ipAddress) {
        StringBuilder cql = new StringBuilder(); 
        cql.append("UPDATE httpaccess.http_success ");
        cql.append("SET ");
        cql.append("requests=requests+1 ");
        cql.append("WHERE ");
        cql.append("ip_address='").append(ipAddress).append("'");
        conn.execute(cql.toString());
    }
    
    public void logHttpSuccessPerMinute(ConnectionHandler conn, String date, String dateToMinute) {
        StringBuilder cql = new StringBuilder(); 
        cql.append("UPDATE httpaccess.http_success_per_minute ");
        cql.append("SET ");
        cql.append("requests=requests+1 ");
        cql.append("WHERE ");
        cql.append("date='").append(date).append("' and ");
        cql.append("date_to_minute='").append(dateToMinute).append("'");
        conn.execute(cql.toString());
    }
    
    public void logHttpFailed(ConnectionHandler conn, String ipAddress) {
        StringBuilder cql = new StringBuilder(); 
        cql.append("UPDATE httpaccess.http_failed ");
        cql.append("SET ");
        cql.append("requests=requests+1 ");
        cql.append("WHERE ");
        cql.append("ip_address='").append(ipAddress).append("'");
        conn.execute(cql.toString());
    }

        public void logHttpFailedPerMinute(ConnectionHandler conn, String date, String dateToMinute) {
        StringBuilder cql = new StringBuilder(); 
        cql.append("UPDATE httpaccess.http_failed_per_minute ");
        cql.append("SET ");
        cql.append("requests=requests+1 ");
        cql.append("WHERE ");
        cql.append("date='").append(date).append("' and ");
        cql.append("date_to_minute='").append(dateToMinute).append("'");
        conn.execute(cql.toString());
    }
    

    public void createSchema() {
        ConnectionHandler connectionHandler = new ConnectionHandler();
        try {
            connectionHandler.connect();
            connectionHandler.execute("CREATE KEYSPACE IF NOT EXISTS httpaccess WITH replication = {'class':'SimpleStrategy', 'replication_factor':2};");

            StringBuilder logHttpAccessTable = new StringBuilder();
            logHttpAccessTable.append("CREATE TABLE IF NOT EXISTS httpaccess.http_access (");
            logHttpAccessTable.append("    hour                INT,");
            logHttpAccessTable.append("    ip_address          VARCHAR,");
            logHttpAccessTable.append("    http_status         VARCHAR,");
            logHttpAccessTable.append("    action              VARCHAR,");
            logHttpAccessTable.append("    url                 VARCHAR,");
            logHttpAccessTable.append("    date_to_minute      TIMESTAMP,");
            logHttpAccessTable.append("    PRIMARY KEY (hour, date_to_minute)");
            logHttpAccessTable.append(")");
            connectionHandler.execute(logHttpAccessTable.toString());

            StringBuilder logSuccessPerTimeSliceTable = new StringBuilder();
            logSuccessPerTimeSliceTable.append("CREATE TABLE IF NOT EXISTS httpaccess.http_success_per_minute (");
            logSuccessPerTimeSliceTable.append("    date                    TIMESTAMP,");
            logSuccessPerTimeSliceTable.append("    date_to_minute          TIMESTAMP,");
            logSuccessPerTimeSliceTable.append("    requests                COUNTER,");
            logSuccessPerTimeSliceTable.append("    PRIMARY KEY (date, date_to_minute)");
            logSuccessPerTimeSliceTable.append(") WITH CLUSTERING ORDER BY (date_to_minute DESC)");
            connectionHandler.execute(logSuccessPerTimeSliceTable.toString());
            
            StringBuilder logFailedPerTimeSliceTable = new StringBuilder();
            logFailedPerTimeSliceTable.append("CREATE TABLE IF NOT EXISTS httpaccess.http_failed_per_minute (");
            logFailedPerTimeSliceTable.append("    date                    TIMESTAMP,");
            logFailedPerTimeSliceTable.append("    date_to_minute          TIMESTAMP,");
            logFailedPerTimeSliceTable.append("    requests                COUNTER,");
            logFailedPerTimeSliceTable.append("    PRIMARY KEY (date, date_to_minute)");
            logFailedPerTimeSliceTable.append(") WITH CLUSTERING ORDER BY (date_to_minute DESC)");
            connectionHandler.execute(logFailedPerTimeSliceTable.toString());
            
            StringBuilder logSuccessTable = new StringBuilder();
            logSuccessTable.append("CREATE TABLE IF NOT EXISTS httpaccess.http_success (");
            logSuccessTable.append("    ip_address              VARCHAR,");
            logSuccessTable.append("    requests                COUNTER,");
            logSuccessTable.append("    PRIMARY KEY (ip_address)");
            logSuccessTable.append(")");
            connectionHandler.execute(logSuccessTable.toString());
            
            StringBuilder logFailureTable = new StringBuilder();
            logFailureTable.append("CREATE TABLE IF NOT EXISTS httpaccess.http_failed (");
            logFailureTable.append("    ip_address              VARCHAR,");
            logFailureTable.append("    requests                COUNTER,");
            logFailureTable.append("    PRIMARY KEY (ip_address)");
            logFailureTable.append(")");
            connectionHandler.execute(logFailureTable.toString());
        } finally {
            connectionHandler.close();
        }
    }

//    public void querySchema() {
//        CassandraConnectionHandler connectionHandler = new CassandraConnectionHandler();
////        ResultSet results = session.execute("SELECT * FROM simplex.playlists "
////                + "WHERE id = 2cc9ccb7-6221-4ccb-8387-f22b6a1b354d;");
//        ResultSet results = session.execute("SELECT * FROM simplex.playlists;");
//        System.out.println(String.format("%-30s\t%-20s\t%-20s\n%s", "title", "album", "artist",
//                "-------------------------------+-----------------------+--------------------"));
//        for (Row row : results) {
//            System.out.println(String.format("%-30s\t%-20s\t%-20s", row.getString("title"), row.getString("album"),
//                    row.getString("artist")));
//        }
//        System.out.println();
//    }
}