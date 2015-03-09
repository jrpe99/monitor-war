package dk.jrpe.cassandra.db;

public class CassandraDAO {

    ConnectionHandler conn = null;
    
    public CassandraDAO(ConnectionHandler conn) {
        this.conn = conn;
    }
}
