package dk.jrpe.monitor.db.cassandra.schema;


import dk.jrpe.monitor.db.cassandra.CassandraConnectionHandler;

public class HTTPAccessSchema {
    public static void main(String[] args) {
        new HTTPAccessSchema().createSchema();
    }

    public void createSchema() {
        try (CassandraConnectionHandler connectionHandler = new CassandraConnectionHandler()) {
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
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}