package dk.jrpe.cassandra.monitoring.task;

import com.datastax.driver.core.Row;
import dk.jrpe.cassandra.db.CassandraReadDAO;
import dk.jrpe.cassandra.output.json.LineChartResult;
import dk.jrpe.cassandra.websocket.util.WebSocketHelper;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import javax.websocket.Session;

/**
 * Task for monitoring HTTP total HTTP Success/Failure access per minute.
 * Reads the Cassandra database and generates output in JSON,
 * which is sent to all clients. The JSON is adapted for the
 * Chart.js library.
 * 
 * One Chart is generated:
 * 
 * 1. Line chart with two data sets. One for Success and one for Failed.
 * 
 * @author Jörgen Persson
 */
public class HttpRequestsPerMinuteMonitorTask extends MonitoringTask {

    public HttpRequestsPerMinuteMonitorTask(List<Session> sessionList, int delay) {
        super(sessionList, delay);
    }
    
    @Override
    public void run() {
        HashMap<String, List<Row>> dataSetMap = new HashMap<>();

        CassandraReadDAO dao = new CassandraReadDAO(this.getCassandraConnection());

        /* Create FROM and TO date (30 minutes in the past) */
        ZonedDateTime now = ZonedDateTime.now().withSecond(0).withNano(0);
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        String date = formatter.format(now);
        formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        String to = formatter.format(now);
        String from = formatter.format(now.minusMinutes(30));

        /* Query the Cassandra database */
        List<Row> successList = dao.getHttpSuccessPerMinute(date, from, to);
        List<Row> failedList = dao.getHttpFailedPerMinute(date, from, to);
        dataSetMap.put("Success", successList);
        dataSetMap.put("Failed", failedList);
        
        /* Get JSON for the Line chart */
        String json = LineChartResult.toJSON(dataSetMap, 30);
        
        /* Send to all clients */
        WebSocketHelper.sendToAll(this.getSessionList(), json);
    }
}
