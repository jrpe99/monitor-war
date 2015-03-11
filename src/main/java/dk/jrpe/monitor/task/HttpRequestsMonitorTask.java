package dk.jrpe.monitor.task;

import com.datastax.driver.core.Row;
import dk.jrpe.monitor.cassandra.db.CassandraReadDAO;
import dk.jrpe.monitor.output.ChartEnum;
import dk.jrpe.monitor.output.json.PieChartResult;
import dk.jrpe.monitor.output.json.RadarChartResult;
import dk.jrpe.monitor.output.json.ResultHelper;
import dk.jrpe.monitor.websocket.WebSocketHelper;
import java.util.List;
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

    public HttpRequestsMonitorTask(List<Session> sessionList, int delay) {
        super(sessionList, delay);
    }
    
    @Override
    public void run() {
        CassandraReadDAO dao = new CassandraReadDAO(this.getCassandraConnection());

        List<Row> successRowList = ResultHelper.sortOnLongField(dao.getHttpSuccess(), "requests");
        List<Row> failedRowList = ResultHelper.sortOnLongField(dao.getHttpFailure(), "requests");

        /* Send to all clients */
        WebSocketHelper.sendToAll(this.getSessionList(), 
            ChartEnum.PIE_SUCCESS.toJSON(successRowList),
            ChartEnum.RADAR_SUCCESS.toJSON(successRowList),
            ChartEnum.PIE_FAILED.toJSON(failedRowList),
            ChartEnum.RADAR_FAILED.toJSON(failedRowList)
        );
    }
}