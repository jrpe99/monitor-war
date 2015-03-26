package dk.jrpe.monitor.db.datasource.cassandra;

import dk.jrpe.monitor.db.cassandra.CassandraConnectionHandler;
import dk.jrpe.monitor.db.cassandra.dao.httpaccess.CassandraHTTPAccessReadDAO;
import dk.jrpe.monitor.db.cassandra.dao.httpaccess.CassandraHTTPAccessWriteDAO;
import com.datastax.driver.core.Row;
import dk.jrpe.monitor.db.datasource.DataSource;
import dk.jrpe.monitor.db.httpaccess.to.HTTPAccessTO;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Jörgen Persson
 */
public class CassandraDataSource implements DataSource {

    private CassandraConnectionHandler conn = null;
    private CassandraHTTPAccessReadDAO readDao = null;
    private CassandraHTTPAccessWriteDAO writeDao = null;
    
    @Override public List<HTTPAccessTO> getHttpSuccess() {
        return adaptHttpAccess(this.readDao.getHttpSuccess());
    }

    @Override public List<HTTPAccessTO> getHttpFailed() {
        return adaptHttpAccess(this.readDao.getHttpFailed());
    }
    
    @Override public List<HTTPAccessTO> getHttpSuccessPerMinute(String date, String from, String to) {
        return adaptHttpAccess(this.readDao.getHttpSuccessPerMinute(date, from, to));
    }

    @Override public List<HTTPAccessTO> getHttpFailedPerMinute(String date, String from, String to) {
        return adaptHttpAccess(this.readDao.getHttpFailedPerMinute(date, from, to));
    }

    @Override public void saveHttpAccess(HTTPAccessTO to, int hour) {
        this.writeDao.saveHttpAccess(to, hour);
    }

    @Override public void updateHttpSuccess(HTTPAccessTO to) {
        this.writeDao.updateHttpSuccess(to);
    }

    @Override public void updateHttpSuccessPerMinute(HTTPAccessTO to) {
        this.writeDao.updateHttpSuccessPerMinute(to);
    }

    @Override public void updateHttpFailed(HTTPAccessTO to) {
        this.writeDao.updateHttpFailed(to);
    }

    @Override public void updateHttpFailedPerMinute(HTTPAccessTO to) {
        this.writeDao.updateHttpFailedPerMinute(to);
    }

    @Override public void open() {
        if(this.conn == null) {
            this.conn = new CassandraConnectionHandler();
            this.conn.connect();
            this.readDao = new CassandraHTTPAccessReadDAO(this.conn);
            this.writeDao = new CassandraHTTPAccessWriteDAO(this.conn);
        }
    }

    @Override public void close() {
        if(this.conn != null) {
            this.conn.close();
        }
    }

    private List<HTTPAccessTO> adaptHttpAccess(List<Row> list) {
        return list.stream().map(row -> 
            new HTTPAccessTO.Builder()
                .setIPAdress(row.getString("ip_address"))
                .setRequests(row.getLong("requests")).build()
        ).collect(Collectors.toList());
    }
}
