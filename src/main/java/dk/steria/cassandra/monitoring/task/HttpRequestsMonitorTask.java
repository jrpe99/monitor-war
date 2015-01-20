package dk.steria.cassandra.monitoring.task;

import com.datastax.driver.core.Row;
import dk.steria.cassandra.db.CassandraReadDAO;
import dk.steria.cassandra.output.json.PieChartResult;
import dk.steria.cassandra.output.json.RadarChartResult;
import dk.steria.cassandra.output.json.ResultHelper;
import dk.steria.cassandra.websocket.util.WebSocketHelper;
import java.util.List;

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

    public HttpRequestsMonitorTask(int timeInterval) {
        super(timeInterval);
    }
    
    @Override
    public void run() {
        CassandraReadDAO dao = new CassandraReadDAO(this.getCassandraConnection());

        List<Row> rowList = dao.getHttpSuccess();
        ResultHelper.sortOnLongField(rowList, "requests");
        String successPieChartJSONResult = PieChartResult.httpSuccessToJSON(rowList);
        String successRadarChartJSONResult = RadarChartResult.httpSuccessToJSON(rowList);

        rowList = dao.getHttpFailure();
        ResultHelper.sortOnLongField(rowList, "requests");
        String failedPieChartJSONResult = PieChartResult.httpFailureToJSON(rowList);
        String failedRadarChartJSONResult = RadarChartResult.httpFailureToJSON(rowList);

        /* Send to all clients */
        WebSocketHelper.sendToAll(this.getSessionList(), 
            successPieChartJSONResult,
            successRadarChartJSONResult,
            failedPieChartJSONResult,
            failedRadarChartJSONResult
        );
    }
}