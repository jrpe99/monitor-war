package dk.jrpe.monitor.db.strategy;

import com.datastax.driver.core.Row;
import dk.jrpe.monitor.db.to.HTTPAccessTO;
import java.util.List;

/**
 *
 * @author Jörgen Persson
 */
public interface DataSource extends AutoCloseable {
    public List<HTTPAccessTO> getHttpSuccess();
    public List<HTTPAccessTO> getHttpFailed();
    public List<Row> getHttpSuccessPerMinute(String date, String from, String to);
    public List<Row> getHttpFailedPerMinute(String date, String from, String to);

    public void saveHttpAccess(HTTPAccessTO to, int hour, long time);
    public void updateHttpSuccess(HTTPAccessTO to);
    public void updateHttpSuccessPerMinute(HTTPAccessTO to);
    public void updateHttpFailed(HTTPAccessTO to);
    public void updateHttpFailedPerMinute(String date, String dateToMinute);
    
    public void open();
}
