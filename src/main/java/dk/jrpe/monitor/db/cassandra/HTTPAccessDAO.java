package dk.jrpe.monitor.db.cassandra;

import dk.jrpe.monitor.db.to.HTTPAccessTO;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Jörgen Persson
 */
public class HTTPAccessDAO extends CassandraWriteDAO {

    public HTTPAccessDAO(CassandraConnectionHandler conn) {
        super(conn);
    }
    
    public void saveAndUpdate(HTTPAccessTO to) {
        ZonedDateTime now = ZonedDateTime.now().withSecond(0).withNano(0);
        int hour = now.getHour();
        long epoch = now.toEpochSecond();

        saveHttpAccess(to, hour, epoch);

        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        String date = formatter.format(now);

        formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        String dateToMinute = formatter.format(now);

        if(to.getHttpStatus().equals("200")) {
            updateHttpSuccess(to);
            updateHttpSuccessPerMinute(to);
        } else {
            updateHttpFailed(to);
            updateHttpFailedPerMinute(date, dateToMinute);
        }
    }
}
