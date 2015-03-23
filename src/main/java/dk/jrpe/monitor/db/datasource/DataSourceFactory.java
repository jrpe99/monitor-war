package dk.jrpe.monitor.db.datasource;

import dk.jrpe.monitor.db.datasource.cassandra.CassandraDataSource;
import dk.jrpe.monitor.db.datasource.inmemory.InMemoryDataSource;

/**
 * Factory Enum for monitoring data source
 * @author Jörgen Persson
 */
public enum DataSourceFactory {
    CASSANDRA(new CassandraDataSource()), IN_MEMORY(new InMemoryDataSource());
    
    private final DataSource dataSource;
    DataSourceFactory(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    public static DataSource getDefault() {
//        return CASSANDRA.get();
        return IN_MEMORY.get();
    }
    
    public DataSource get() {
        return this.dataSource;
    }
}
