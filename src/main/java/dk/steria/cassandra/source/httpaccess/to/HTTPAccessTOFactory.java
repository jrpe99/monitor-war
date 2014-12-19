package dk.steria.cassandra.source.httpaccess.to;

import dk.steria.cassandra.source.httpaccess.simulate.SimulationConstants;
import dk.steria.cassandra.source.httpaccess.to.HTTPAccessTO;
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
        HTTPAccessTO httpAccessTO = new HTTPAccessTO();
        Random random = new Random();
        int index = random.nextInt(SimulationConstants.HTTP_STATUS_LIST.size());
        String httpStatus = SimulationConstants.HTTP_STATUS_LIST.get(index);
        httpAccessTO.setHttpStatus(httpStatus);
        
        index = random.nextInt(9) * 10;
        String ipAddress = "1" + index + ".";
        index = random.nextInt(9) * 10;
        ipAddress = ipAddress + "132.100." + "2" + index;
        httpAccessTO.setIpAddress(ipAddress);
        
        index = random.nextInt(SimulationConstants.ACTION_LIST.size());
        String action = SimulationConstants.ACTION_LIST.get(index);
        httpAccessTO.setAction(action);
        
        index = random.nextInt(SimulationConstants.URL_LIST.size());
        String url = SimulationConstants.URL_LIST.get(index);
        httpAccessTO.setUrl(url);
        
        ZonedDateTime now = ZonedDateTime.now().withSecond(0).withNano(0);
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        String date = formatter.format(now);
        httpAccessTO.setDate(date);
            
        formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        String dateToMinute = formatter.format(now);
        httpAccessTO.setDateToMinute(dateToMinute);
        return httpAccessTO;
    }
}
