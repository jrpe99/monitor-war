package dk.jrpe.cassandra;

public class CassandraDAO {

    ConnectionHandler conn = null;
    
    public CassandraDAO(ConnectionHandler conn) {
        this.conn = conn;
    }
}
