package dk.jrpe.monitor.db.strategy;

/**
 * Create current data source
 * @author Jörgen Persson
 */
public class DataSourceFactory {
    public static DataSource getDataSource() {
        return new CassandraDataSource();
    }
}
