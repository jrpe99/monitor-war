package dk.jrpe.monitor.db.datasource;

import dk.jrpe.monitor.db.datasource.cassandra.CassandraDataSource;

/**
 * Create current data source
 * @author Jörgen Persson
 */
public class DataSourceFactory {
    public static DataSource getDataSource() {
        return new CassandraDataSource();
    }
}
