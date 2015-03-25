package dk.jrpe.monitor.db.datasource;

import dk.jrpe.monitor.db.httpaccess.to.HTTPAccessTO;
import java.util.List;

/**
 *
 * @author Jörgen Persson
 */
public interface DataSource extends AutoCloseable {
    public List<HTTPAccessTO> getHttpSuccess();
    public List<HTTPAccessTO> getHttpFailed();
    public List<HTTPAccessTO> getHttpSuccessPerMinute(String date, String from, String to);
    public List<HTTPAccessTO> getHttpFailedPerMinute(String date, String from, String to);

    public void saveHttpAccess(HTTPAccessTO to, int hour);
    public void updateHttpSuccess(HTTPAccessTO to);
    public void updateHttpSuccessPerMinute(HTTPAccessTO to);
    public void updateHttpFailed(HTTPAccessTO to);
    public void updateHttpFailedPerMinute(HTTPAccessTO to);
    
    public void open();
}
