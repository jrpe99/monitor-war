package dk.jrpe.monitor.task;

import com.datastax.driver.core.Row;
import dk.jrpe.monitor.cassandra.db.CassandraReadDAO;
import dk.jrpe.monitor.service.output.ChartEnum;
import dk.jrpe.monitor.service.output.json.LineChartResult;
import dk.jrpe.monitor.websocket.WebSocketHelper;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        try {
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
            ChartEnum lineChart = ChartEnum.valueOf(ChartEnum.LINE_SUCCESS_AND_FAILED.toString());
            lineChart.setJson(json);

            /* Send to all clients */
            WebSocketHelper.sendChartListToAll(this.getSessionList(), lineChart);

        } catch (Throwable ex) {
            Logger.getLogger(WebSocketHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
