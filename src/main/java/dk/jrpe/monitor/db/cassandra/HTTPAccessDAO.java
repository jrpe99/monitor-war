package dk.jrpe.monitor.db.cassandra;

import dk.jrpe.monitor.source.httpaccess.to.HTTPAccessTO;
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
    
    public void create(HTTPAccessTO to) {
        ZonedDateTime now = ZonedDateTime.now().withSecond(0).withNano(0);
        int hour = now.getHour();
        long epoch = now.toEpochSecond();

        logHttpAccess(to, hour, epoch);

        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        String date = formatter.format(now);

        formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        String dateToMinute = formatter.format(now);

        if(to.getHttpStatus().equals("200")) {
            logHttpSuccess(to);
            logHttpSuccessPerMinute(to);
        } else {
            logHttpFailed(to);
            logHttpFailedPerMinute(date, dateToMinute);
        }
    }
}
