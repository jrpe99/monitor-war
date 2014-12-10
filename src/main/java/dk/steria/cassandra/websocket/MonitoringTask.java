package dk.steria.cassandra.websocket;

import dk.steria.cassandra.db.CassandraDAO;
import dk.steria.cassandra.db.ConnectionHandler;
import dk.steria.cassandra.websocket.util.WebSocketHelper;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.websocket.Session;

/**
 *
 * @author jorperss
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
        String jsonResult = dao.getHttpSuccess(conn);
        
        for(Session session: this.sessionList) {
            WebSocketHelper.send(session, jsonResult);
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
