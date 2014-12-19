package dk.steria.cassandra.source.httpaccess.main;

import dk.steria.cassandra.db.CassandraWriteDAO;
import java.util.Random;

import dk.steria.cassandra.db.ConnectionHandler;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class HTTPAccessSimulator {
    public static void main(String[] args) {
        new HTTPAccessSimulator().simulate();
    }

    public void simulate() {
        ConnectionHandler conn = null;
        try {
            Random random = new Random();
            conn = new ConnectionHandler();
            conn.connect();
            CassandraWriteDAO dao = new CassandraWriteDAO(conn);
            
            while(true) {
                int index = random.nextInt(SimulationConstants.httpStatusList.size());
                String httpStatus = SimulationConstants.httpStatusList.get(index);
                
                index = random.nextInt(9)*10;
                String ipAddress = "1"+index+".";
                index = random.nextInt(9)*10;
                ipAddress = ipAddress + "132.100."+"2"+index;
                
                index = random.nextInt(SimulationConstants.actionList.size());
                String action = SimulationConstants.actionList.get(index);
                
                index = random.nextInt(SimulationConstants.urlList.size());
                String url = SimulationConstants.urlList.get(index);
                
                ZonedDateTime now = ZonedDateTime.now().withSecond(0).withNano(0);
                int hour = now.getHour();
                long epoch = now.toEpochSecond();
                
                dao.logHttpAccess(hour, httpStatus, ipAddress, action, url, epoch);

                DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
                String date = formatter.format(now);
                
                formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
                String dateToMinute = formatter.format(now);
                
                if(httpStatus.equals("200")) {
                    dao.logHttpSuccess(ipAddress);
                    dao.logHttpSuccessPerMinute(date, dateToMinute);
                } else {
                    dao.logHttpFailed(ipAddress);
                    dao.logHttpFailedPerMinute(date, dateToMinute);
                }
                
                int sleepTime = random.nextInt(500);
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                }
            }
        } finally {
            if(conn != null)conn.close();
        }
    }
}