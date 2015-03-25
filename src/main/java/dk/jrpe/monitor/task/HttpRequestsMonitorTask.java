package dk.jrpe.monitor.task;

import dk.jrpe.monitor.db.datasource.DataSource;
import dk.jrpe.monitor.db.httpaccess.to.HTTPAccessTO;
import dk.jrpe.monitor.service.chart.ChartEnum;
import dk.jrpe.monitor.util.SortHelper;
import dk.jrpe.monitor.websocket.WebSocketHelper;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.Session;

/**
 * Task for monitoring total HTTP Success/Failure request per IP address.
 * Reads the Cassandra database and generates output in JSON,
 * which is sent to all clients. The JSON is adapted for the
 * Chart.js library.
 * 
 * Four charts are generated:
 * 1. Pie Chart for HTTP Success per IP address
 * 2. Radar Chart for HTTP Success per IP address
 * 3. Pie Chart for HTTP Failure per IP address
 * 4. Radar Chart for HTTP Failure per IP address
 * 
 * @author Jörgen Persson
 */
public class HttpRequestsMonitorTask extends MonitoringTask {

    public HttpRequestsMonitorTask(DataSource dataSource, List<Session> sessionList, int delay) {
        super(dataSource, sessionList, delay);
    }
    
    @Override
    public void run() {
        try {
            List<HTTPAccessTO> successRowList = SortHelper.onRequestCount(this.getDataSouce().getHttpSuccess());
            List<HTTPAccessTO> failedRowList = SortHelper.onRequestCount(this.getDataSouce().getHttpFailed());

            ChartEnum.PIE_SUCCESS.toJSON(successRowList);
            ChartEnum.RADAR_SUCCESS.toJSON(successRowList);
            ChartEnum.PIE_FAILED.toJSON(failedRowList);
            ChartEnum.RADAR_FAILED.toJSON(failedRowList);

            /* Send to all clients */
            WebSocketHelper.sendChartListToAll(this.getSessionList(), 
                ChartEnum.PIE_SUCCESS,
                ChartEnum.RADAR_SUCCESS,
                ChartEnum.PIE_FAILED,
                ChartEnum.RADAR_FAILED
            );
        } catch (Throwable ex) {
            Logger.getLogger(WebSocketHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
