package dk.jrpe.cassandra.source.httpaccess.to;

import dk.jrpe.cassandra.source.httpaccess.simulate.SimulationConstants;
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
        
        return new HTTPAccessTO.HTTPAccessTOBuilder(httpStatus, ipAddress, date, dateToMinute)
                .setAction(action)
                .setUrl(url)
                .build();
    }
}
