package dk.steria.cassandra.simulate.http;

import java.util.Random;

import dk.steria.cassandra.db.ConnectionHandler;

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
                
                index = random.nextInt(SimulationConstants.ipAddressList.size());
                String ipAddress = SimulationConstants.ipAddressList.get(index);
                
                index = random.nextInt(SimulationConstants.actionList.size());
                String action = SimulationConstants.actionList.get(index);
                
                index = random.nextInt(SimulationConstants.urlList.size());
                String url = SimulationConstants.urlList.get(index);
                
                logHttpAccess(conn, httpStatus, ipAddress, action, url);
                
                if(httpStatus.equals("200")) {
                    logHttpSuccess(conn, ipAddress);
                } else {
                    logHttpFailure(conn, ipAddress);
                }
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                }
            }
        } finally {
            if(conn != null)conn.close();
        }
    }
    
    public void logHttpAccess(ConnectionHandler conn, String httpStatus, String ipAddress, String action, String url) {
        StringBuilder cql = new StringBuilder(); 
        cql.append("INSERT INTO httpaccess.http_access (ip_address,http_status,action,url,date_to_minute) ");
        cql.append("VALUES ");
        cql.append("(");
        cql.append("'"+ipAddress+"',");
        cql.append("'"+httpStatus+"',");
        cql.append("'"+action+"',");
        cql.append("'"+url+"',");
        cql.append("'"+System.currentTimeMillis()+"'");
        cql.append(")");
        conn.execute(cql.toString());
    }
    
    public void logHttpSuccess(ConnectionHandler conn, String ipAddress) {
        StringBuilder cql = new StringBuilder(); 
        cql.append("UPDATE httpaccess.http_success ");
        cql.append("SET ");
        cql.append("successful_requests=successful_requests+1 ");
        cql.append("WHERE ");
        cql.append("ip_address='"+ipAddress+"'");
        conn.execute(cql.toString());
    }
    
    public void logHttpFailure(ConnectionHandler conn, String ipAddress) {
        StringBuilder cql = new StringBuilder(); 
        cql.append("UPDATE httpaccess.http_failure ");
        cql.append("SET ");
        cql.append("failed_requests=failed_requests+1 ");
        cql.append("WHERE ");
        cql.append("ip_address='"+ipAddress+"'");
        conn.execute(cql.toString());
    }
    
    public void createSchema() {
        ConnectionHandler connectionHandler = new ConnectionHandler();
        try {
            connectionHandler.connect();
            connectionHandler.execute("CREATE KEYSPACE IF NOT EXISTS httpaccess WITH replication = {'class':'SimpleStrategy', 'replication_factor':3};");

            StringBuilder logHttpAccessTable = new StringBuilder();
            logHttpAccessTable.append("CREATE TABLE IF NOT EXISTS httpaccess.http_access (");
            logHttpAccessTable.append("    ip_address          VARCHAR,");
            logHttpAccessTable.append("    http_status         VARCHAR,");
            logHttpAccessTable.append("    action              VARCHAR,");
            logHttpAccessTable.append("    url                 VARCHAR,");
            logHttpAccessTable.append("    date_to_minute      TIMESTAMP,");
            logHttpAccessTable.append("    PRIMARY KEY (ip_address, date_to_minute)");
            logHttpAccessTable.append(") WITH CLUSTERING ORDER BY (date_to_minute DESC)");
            connectionHandler.execute(logHttpAccessTable.toString());

            StringBuilder logSuccessTable = new StringBuilder();
            logSuccessTable.append("CREATE TABLE IF NOT EXISTS httpaccess.http_success (");
            logSuccessTable.append("    ip_address              VARCHAR,");
            logSuccessTable.append("    successful_requests     COUNTER,");
            logSuccessTable.append("    PRIMARY KEY (ip_address)");
            logSuccessTable.append(")");
            connectionHandler.execute(logSuccessTable.toString());
            
            StringBuilder logFailureTable = new StringBuilder();
            logFailureTable.append("CREATE TABLE IF NOT EXISTS httpaccess.http_failure (");
            logFailureTable.append("    ip_address              VARCHAR,");
            logFailureTable.append("    failed_requests         COUNTER,");
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