package dk.jrpe.monitor.db.strategy;

import com.datastax.driver.core.Row;
import dk.jrpe.monitor.db.to.HttpAccess;
import java.util.List;

/**
 *
 * @author Jörgen Persson
 */
public interface DataSourceStrategy {
    public List<HttpAccess> getHttpSuccess();
    public List<HttpAccess> getHttpFailed();
    public List<Row> getHttpSuccessPerMinute(String date, String from, String to);
    public List<Row> getHttpFailedPerMinute(String date, String from, String to);
    public void open();
    public void close();
}
