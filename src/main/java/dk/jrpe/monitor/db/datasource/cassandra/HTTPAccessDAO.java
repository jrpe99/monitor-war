package dk.jrpe.monitor.db.datasource.cassandra;

import dk.jrpe.monitor.db.datasource.DataSource;
import dk.jrpe.monitor.db.to.HTTPAccessTO;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

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
        ZonedDateTime now = ZonedDateTime.now().withSecond(0).withNano(0);
        int hour = now.getHour();
        long epoch = now.toEpochSecond();

        this.dataSource.saveHttpAccess(to, hour, epoch);

        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        String date = formatter.format(now);

        formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        String dateToMinute = formatter.format(now);

        if(to.getHttpStatus().equals("200")) {
            this.dataSource.updateHttpSuccess(to);
            this.dataSource.updateHttpSuccessPerMinute(to);
        } else {
            this.dataSource.updateHttpFailed(to);
            this.dataSource.updateHttpFailedPerMinute(date, dateToMinute);
        }
    }
}
