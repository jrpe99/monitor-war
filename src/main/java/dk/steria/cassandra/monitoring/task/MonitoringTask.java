package dk.steria.cassandra.monitoring.task;

import dk.steria.cassandra.db.ConnectionHandler;
import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.websocket.Session;

/**
 * Parent of all monitoring tasks.
 * 
 * @author Jörgen Persson
 */
public abstract class MonitoringTask extends TimerTask {

    private int timeInterval = 1000;
    private List<Session> sessionList = new CopyOnWriteArrayList<>();

    private ConnectionHandler conn = null;

    /**
     * Initialize the monitor task.
     * 1. Set monitoring interval
     * 2. Connect to the Cassandra database
     * 
     * @param timeInterval 
     */
    MonitoringTask(int timeInterval) {
        this.timeInterval = timeInterval;
        connectToCassandra();
    }
    
    @Override
    public boolean cancel() {
        if(conn != null) {
            conn.close();
        }
        return super.cancel();
    }
    
    public List<Session> getSessionList() {
        return sessionList;
    }

    public void addSession(Session session) {
        sessionList.add(session);
    }
    
    public void removeSession(Session session) {
        sessionList.remove(session);
    }

    public int getTimeInterval() {
        return timeInterval;
    }

    public synchronized ConnectionHandler getCassandraConnection() {
        if(this.conn == null) {
            connectToCassandra();
        }
        return conn;
    }

    private void connectToCassandra() {
        if(conn == null) {
            conn = new ConnectionHandler();
            conn.connect();
        }
    }
}
