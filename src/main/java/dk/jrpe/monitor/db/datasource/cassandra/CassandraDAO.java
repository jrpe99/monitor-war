package dk.jrpe.monitor.db.datasource.cassandra;

public class CassandraDAO {

    CassandraConnectionHandler conn = null;
    
    public CassandraDAO(CassandraConnectionHandler conn) {
        this.conn = conn;
    }
}
