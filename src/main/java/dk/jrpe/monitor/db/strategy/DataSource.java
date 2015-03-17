package dk.jrpe.monitor.db.strategy;

import com.datastax.driver.core.Row;
import dk.jrpe.monitor.db.to.HTTPAccessTO;
import java.util.List;

/**
 *
 * @author Jörgen Persson
 */
public interface DataSource {
    public List<HTTPAccessTO> getHttpSuccess();
    public List<HTTPAccessTO> getHttpFailed();
    public List<Row> getHttpSuccessPerMinute(String date, String from, String to);
    public List<Row> getHttpFailedPerMinute(String date, String from, String to);
    public void setHttpSuccess(HTTPAccessTO to);
public void open();
    public void close();
}
