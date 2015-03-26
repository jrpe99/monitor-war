package dk.jrpe.monitor.task;

import dk.jrpe.monitor.db.datasource.DataSource;
import dk.jrpe.monitor.db.httpaccess.to.HTTPAccessTO;
import dk.jrpe.monitor.service.chart.ChartEnum;
import dk.jrpe.monitor.service.chart.json.LineChartJSONAdapter;
import dk.jrpe.monitor.websocket.WebSocketHelper;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.Session;

/**
 * Task for monitoring total HTTP Success/Failure access per minute.
 * Reads the Datasource and generates output in JSON,
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

    public HttpRequestsPerMinuteMonitorTask(DataSource dataSource, List<Session> sessionList, int delay) {
        super(dataSource, sessionList, delay);
    }
    
    @Override
    public void run() {
        try {
            HashMap<String, List<HTTPAccessTO>> dataSetMap = new HashMap<>();

            /* Create FROM and TO date (30 minutes in the past) */
            ZonedDateTime now = ZonedDateTime.now().withSecond(0).withNano(0);
            DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
            String date = formatter.format(now);
            formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
            String to = formatter.format(now);
            String from = formatter.format(now.minusMinutes(30));

            List<HTTPAccessTO> successList = this.getDataSouce().getHttpSuccessPerMinute(date, from, to);
            List<HTTPAccessTO> failedList = this.getDataSouce().getHttpFailedPerMinute(date, from, to);
            dataSetMap.put("Success", successList);
            dataSetMap.put("Failed", failedList);

            /* Get JSON for the Line chart */
            String json = LineChartJSONAdapter.toJSON(dataSetMap, 30);
            ChartEnum lineChart = ChartEnum.valueOf(ChartEnum.LINE_SUCCESS_AND_FAILED.toString());
            lineChart.setJson(json);

            /* Send to all clients */
            WebSocketHelper.sendChartListToAll(this.getSessionList(), lineChart);

        } catch (Throwable ex) {
            Logger.getLogger(WebSocketHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
