package dk.jrpe.monitor.db.dao.httpaccess;

import dk.jrpe.monitor.db.datasource.cassandra.CassandraConnectionHandler;

public class CassandraDAO {

    CassandraConnectionHandler conn = null;
    
    public CassandraDAO(CassandraConnectionHandler conn) {
        this.conn = conn;
    }
}
