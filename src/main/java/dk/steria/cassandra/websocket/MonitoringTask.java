package dk.steria.cassandra.websocket;

import dk.steria.cassandra.db.ConnectionHandler;
import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.websocket.Session;

/**
 *
 * @author Jörgen Persson
 */
abstract class MonitoringTask extends TimerTask {

    int timeInterval = 1000;
    List<Session> sessionList = new CopyOnWriteArrayList<>();
    ConnectionHandler conn = null;

    MonitoringTask(int timeInterval) {
        this.timeInterval = timeInterval;
    }
    
    @Override
    public boolean cancel() {
        if(conn != null) {
            conn.close();
        }
        return super.cancel();
    }
    
    void connectToCassandra() {
        if(conn == null) {
            conn = new ConnectionHandler();
            conn.connect();
        }
    }
    
    void addSession(Session session) {
        sessionList.add(session);
    }
    
    void removeSession(Session session) {
        sessionList.remove(session);
    }

    int getTimeInterval() {
        return timeInterval;
    }
}
