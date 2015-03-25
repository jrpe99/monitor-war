package dk.jrpe.monitor.db.datasource.inmemory;

import dk.jrpe.monitor.db.inmemory.InMemoryDataBase;
import dk.jrpe.monitor.db.datasource.DataSource;
import dk.jrpe.monitor.db.httpaccess.to.HTTPAccessTO;
import java.util.List;

/**
 *
 * @author Jörgen Persson
 */
public class InMemoryDataSource implements DataSource {

    private final InMemoryDataBase dataBase = InMemoryDataBase.getInstance();

    @Override public List<HTTPAccessTO> getHttpSuccess() {
        return dataBase.getHttpSuccess();
    }

    @Override
    public List<HTTPAccessTO> getHttpFailed() {
        return dataBase.getHttpFailed();
    }

    @Override public List<HTTPAccessTO> getHttpSuccessPerMinute(String date, String from, String to) {
        return dataBase.getHttpSuccessPerMinute(date, from, to);
    }
    
    @Override public List<HTTPAccessTO> getHttpFailedPerMinute(String date, String from, String to) {
        return dataBase.getHttpFailedPerMinute(date, from, to);
    }

    @Override public void saveHttpAccess(HTTPAccessTO to, int hour) {
        // Not used
    }

    @Override public void updateHttpSuccess(HTTPAccessTO to) {
        dataBase.updateHttpSuccess(to);
    }

    @Override public void updateHttpSuccessPerMinute(HTTPAccessTO to) {
        dataBase.updateHttpSuccessPerMinute(to);
    }

    @Override public void updateHttpFailed(HTTPAccessTO to) {
        dataBase.updateHttpFailed(to);
    }

    @Override public void updateHttpFailedPerMinute(HTTPAccessTO to) {
        dataBase.updateHttpFailedPerMinute(to);
    }

    @Override public void open() {
        // NOT USED
    }

    @Override public void close() throws Exception {
        // NOT USED
    }
}
