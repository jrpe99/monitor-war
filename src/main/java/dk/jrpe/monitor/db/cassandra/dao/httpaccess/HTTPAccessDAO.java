package dk.jrpe.monitor.db.cassandra.dao.httpaccess;

import dk.jrpe.monitor.db.datasource.DataSource;
import dk.jrpe.monitor.db.httpaccess.to.HTTPAccessTO;
import java.time.ZonedDateTime;

/**
 *
 * @author Jörgen Persson
 */
public class HTTPAccessDAO {

    private DataSource dataSource = null;
    
    public HTTPAccessDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    public void saveAndUpdate(HTTPAccessTO to) {
        System.out.println("Save: " + to.toString());
        ZonedDateTime now = ZonedDateTime.now().withSecond(0).withNano(0);
        int hour = now.getHour();
        long epoch = now.toEpochSecond();

        this.dataSource.saveHttpAccess(to, hour);

        if(to.getHttpStatus().equals("200")) {
            this.dataSource.updateHttpSuccess(to);
            this.dataSource.updateHttpSuccessPerMinute(to);
        } else {
            this.dataSource.updateHttpFailed(to);
            this.dataSource.updateHttpFailedPerMinute(to);
        }
    }
}
