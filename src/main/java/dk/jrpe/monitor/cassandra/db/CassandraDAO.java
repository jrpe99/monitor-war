package dk.jrpe.monitor.cassandra.db;

public class CassandraDAO {

    ConnectionHandler conn = null;
    
    public CassandraDAO(ConnectionHandler conn) {
        this.conn = conn;
    }
}