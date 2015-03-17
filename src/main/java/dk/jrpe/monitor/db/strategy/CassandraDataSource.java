package dk.jrpe.monitor.db.strategy;

import com.datastax.driver.core.Row;
import dk.jrpe.monitor.db.cassandra.CassandraReadDAO;
import dk.jrpe.monitor.db.cassandra.CassandraConnectionHandler;
import dk.jrpe.monitor.db.cassandra.CassandraWriteDAO;
import dk.jrpe.monitor.db.to.HTTPAccessTO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jörgen Persson
 */
public class CassandraDataSource implements DataSource {

    private CassandraConnectionHandler conn = null;
    private CassandraReadDAO readDao = null;
    private CassandraWriteDAO writeDao = null;
    
    @Override public List<HTTPAccessTO> getHttpSuccess() {
        return adaptHttpAccess(this.readDao.getHttpSuccess());
    }

    @Override public List<HTTPAccessTO> getHttpFailed() {
        return adaptHttpAccess(this.readDao.getHttpFailed());
    }
    
    @Override public List<Row> getHttpSuccessPerMinute(String date, String from, String to) {
        return this.readDao.getHttpSuccessPerMinute(date, from, to);
    }

    @Override public List<Row> getHttpFailedPerMinute(String date, String from, String to) {
        return this.readDao.getHttpSuccessPerMinute(date, from, to);
    }

    @Override
    public void setHttpSuccess(HTTPAccessTO to) {

    
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

    private List<HTTPAccessTO> adaptHttpAccess(List<Row> list) {
        List<HTTPAccessTO> adaptedList = new ArrayList<>();
        list.stream().forEach(row -> {
            HTTPAccessTO to = new HTTPAccessTO.HTTPAccessTOBuilder(row.getString("ip_address")).setRequests(row.getLong("requests")).build();
            adaptedList.add(to);
        });
        return adaptedList;
    }
}
