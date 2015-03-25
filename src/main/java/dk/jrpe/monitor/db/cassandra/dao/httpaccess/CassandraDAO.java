package dk.jrpe.monitor.db.cassandra.dao.httpaccess;

import dk.jrpe.monitor.db.cassandra.CassandraConnectionHandler;

public class CassandraDAO {

    CassandraConnectionHandler conn = null;
    
    public CassandraDAO(CassandraConnectionHandler conn) {
        this.conn = conn;
    }
}
