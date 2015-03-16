package dk.jrpe.monitor.db.cassandra;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Cluster.Builder;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.Session;

public class CassandraConnectionHandler implements AutoCloseable {

    private Cluster cluster;
    private Session session;

    public Session getSession() {
        return this.session;
    }

    public void connect() {
        Builder builder = Cluster.builder();
        builder.addContactPoint("192.168.15.130");
        //builder.addContactPoint("192.168.15.131");
        //builder.addContactPoint("192.168.15.132");
        cluster = builder.build();
        
        Metadata metadata = cluster.getMetadata();
        System.out.printf("Connected to cluster: %s\n", metadata.getClusterName());
        for (Host host : metadata.getAllHosts()) {
            System.out.printf("Datatacenter: %s; Host: %s; Rack: %s\n", host.getDatacenter(), host.getAddress(),
                    host.getRack());
        }
        session = cluster.connect();
    }

    public ResultSet execute(String cql) {
        return session.execute(cql);
    }
    
    @Override
    public void close() {
        if (session != null) {
            session.close();
        }
        
        if (cluster != null) {
            cluster.close();
        }
    }
}