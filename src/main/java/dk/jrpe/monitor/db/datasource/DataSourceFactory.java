package dk.jrpe.monitor.db.datasource;

/**
 * Create current data source
 * @author Jörgen Persson
 */
public class DataSourceFactory {
    public static DataSource getDataSource() {
        return new CassandraDataSource();
    }
}
