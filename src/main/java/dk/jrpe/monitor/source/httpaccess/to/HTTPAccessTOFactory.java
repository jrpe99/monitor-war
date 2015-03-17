package dk.jrpe.monitor.source.httpaccess.to;

import dk.jrpe.monitor.db.to.HTTPAccessTO;
import dk.jrpe.monitor.source.httpaccess.simulate.SimulationConstants;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

/**
 *
 * @author jorperss
 */
public class HTTPAccessTOFactory {

    private HTTPAccessTOFactory() {
    }

    public static HTTPAccessTO createSimulated() {
        Random random = new Random();
        int index = random.nextInt(SimulationConstants.HTTP_STATUS_LIST.size());
        String httpStatus = SimulationConstants.HTTP_STATUS_LIST.get(index);
        
        index = random.nextInt(9) * 10;
        String ipAddress = "1" + index + ".";
        index = random.nextInt(9) * 10;
        ipAddress = ipAddress + "132.100." + "2" + index;
        
        index = random.nextInt(SimulationConstants.ACTION_LIST.size());
        String action = SimulationConstants.ACTION_LIST.get(index);
        
        index = random.nextInt(SimulationConstants.URL_LIST.size());
        String url = SimulationConstants.URL_LIST.get(index);
        
        ZonedDateTime now = ZonedDateTime.now().withSecond(0).withNano(0);
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        String date = formatter.format(now);
            
        formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        String dateToMinute = formatter.format(now);
        //httpStatus, ipAddress, date, dateToMinute
        return new HTTPAccessTO.HTTPAccessTOBuilder(ipAddress)
                .setHttpStatus(httpStatus)
                .setDate(date)
                .setDateToMinute(dateToMinute)
                .setAction(action)
                .setUrl(url)
                .build();
    }
}
