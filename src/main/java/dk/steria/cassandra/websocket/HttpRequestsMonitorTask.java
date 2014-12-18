/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.steria.cassandra.websocket;

import com.datastax.driver.core.Row;
import dk.steria.cassandra.db.CassandraDAO;
import dk.steria.cassandra.json.PieChartResultAdapter;
import dk.steria.cassandra.json.RadarChartResultAdapter;
import dk.steria.cassandra.json.ResultAdapterHelper;
import dk.steria.cassandra.websocket.util.WebSocketHelper;
import java.util.List;
import javax.websocket.Session;

/**
 *
 * @author jorperss
 */
public class HttpRequestsMonitorTask extends MonitoringTask {

    public HttpRequestsMonitorTask(int timeInterval) {
        super(timeInterval);
    }
    
    @Override
    public void run() {
        connectToCassandra();
        
        CassandraDAO dao = new CassandraDAO();

        List<Row> rowList = dao.getHttpSuccess(conn);
        ResultAdapterHelper.sortOnLongField(rowList, "requests");
        String successPieChartJSONResult = PieChartResultAdapter.httpSuccessToJSON(rowList);
        String successRadarChartJSONResult = RadarChartResultAdapter.httpSuccessToJSON(rowList);

        rowList = dao.getHttpFailure(conn);
        ResultAdapterHelper.sortOnLongField(rowList, "requests");
        String failedPieChartJSONResult = PieChartResultAdapter.httpFailureToJSON(rowList);
        String failedRadarChartJSONResult = RadarChartResultAdapter.httpFailureToJSON(rowList);

        this.sessionList.stream().forEach((session) -> {
            WebSocketHelper.send(session, successPieChartJSONResult);
            WebSocketHelper.send(session, successRadarChartJSONResult);
            WebSocketHelper.send(session, failedPieChartJSONResult);
            WebSocketHelper.send(session, failedRadarChartJSONResult);
        });
    }
}
