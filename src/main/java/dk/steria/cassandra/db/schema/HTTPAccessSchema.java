package dk.steria.cassandra.db.schema;


import dk.steria.cassandra.db.ConnectionHandler;

public class HTTPAccessSchema {
    public static void main(String[] args) {
        new HTTPAccessSchema().createSchema();
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