package dk.jrpe.monitor.db.strategy;

import com.datastax.driver.core.Row;
import dk.jrpe.monitor.db.cassandra.CassandraReadDAO;
import dk.jrpe.monitor.db.cassandra.CassandraConnectionHandler;
import dk.jrpe.monitor.db.to.HttpAccess;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jörgen Persson
 */
public class CassandraDataSource implements DataSource {

    private CassandraConnectionHandler conn = null;
    private CassandraReadDAO readDao = null;
    @Override public List<HttpAccess> getHttpSuccess() {
        return adaptHttpAccess(this.readDao.getHttpSuccess());
    }

    @Override public List<HttpAccess> getHttpFailed() {
        return adaptHttpAccess(this.readDao.getHttpFailed());
    }
    
    @Override public List<Row> getHttpSuccessPerMinute(String date, String from, String to) {
        return this.readDao.getHttpSuccessPerMinute(date, from, to);
    }

    @Override public List<Row> getHttpFailedPerMinute(String date, String from, String to) {
        return this.readDao.getHttpSuccessPerMinute(date, from, to);
    }

    @Override public void open() {
        if(this.conn == null) {
            this.conn = new CassandraConnectionHandler();
            this.conn.connect();
            this.readDao = new CassandraReadDAO(this.conn);
        }
    }

    @Override public void close() {
        if(this.conn != null) {
            this.conn.close();
        }
    }

    private List<HttpAccess> adaptHttpAccess(List<Row> list) {
        List<HttpAccess> adaptedList = new ArrayList<>();
        list.stream().forEach(row -> {
            adaptedList.add(new HttpAccess(row.getString("ip_address"), row.getLong("requests")));
        });
        return adaptedList;
    }
}
