package dk.steria.cassandra.websocket;

import com.datastax.driver.core.Row;
import dk.steria.cassandra.db.CassandraDAO;
import dk.steria.cassandra.db.ConnectionHandler;
import dk.steria.cassandra.json.PieChartResultAdapter;
import dk.steria.cassandra.json.RadarChartResultAdapter;
import dk.steria.cassandra.json.ResultAdapterHelper;
import dk.steria.cassandra.websocket.util.WebSocketHelper;
import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.websocket.Session;

/**
 *
 * @author Jörgen Persson
 */
class MonitoringTask extends TimerTask {
    private List<Session> sessionList = new CopyOnWriteArrayList<>();
    private ConnectionHandler conn = null;
    @Override
    public void run() {
        if(conn == null) {
            conn = new ConnectionHandler();
            conn.connect();
        }
        
        CassandraDAO dao = new CassandraDAO();

        List<Row> rowList = dao.getHttpSuccess(conn);
        ResultAdapterHelper.sortOnLongField(rowList, "successful_requests");
        String successPieChartJSONResult = PieChartResultAdapter.httpSuccessToJSON(rowList);
        String successRadarChartJSONResult = RadarChartResultAdapter.httpSuccessToJSON(rowList);

        rowList = dao.getHttpFailure(conn);
        ResultAdapterHelper.sortOnLongField(rowList, "failed_requests");
        String failedPieChartJSONResult = PieChartResultAdapter.httpFailureToJSON(rowList);
        String failedRadarChartJSONResult = RadarChartResultAdapter.httpFailureToJSON(rowList);

        for(Session session: this.sessionList) {
            WebSocketHelper.send(session, successPieChartJSONResult);
            WebSocketHelper.send(session, successRadarChartJSONResult);
            WebSocketHelper.send(session, failedPieChartJSONResult);
            WebSocketHelper.send(session, failedRadarChartJSONResult);
        }
    }

    @Override
    public boolean cancel() {
        if(conn != null) {
            conn.close();
        }
        return super.cancel();
    }
    
    void addSession(Session session) {
        sessionList.add(session);
    }
    
    void removeSession(Session session) {
        sessionList.remove(session);
    }
}
