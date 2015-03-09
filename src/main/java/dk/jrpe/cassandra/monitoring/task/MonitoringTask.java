package dk.jrpe.cassandra.monitoring.task;

import dk.jrpe.cassandra.ConnectionHandler;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.websocket.Session;

/**
 * Parent of all monitoring tasks.
 * 
 * @author Jörgen Persson
 */
public abstract class MonitoringTask implements Runnable {

    private int delay = 1000;
    private List<Session> sessionList = null;

    private ConnectionHandler conn = null;

    /**
     * Initialize the monitor task.
     * 1. Set monitoring interval
     * 2. Connect to the Cassandra database
     * 
     * @param delay 
     */
    MonitoringTask(List<Session> sessionList, int delay) {
        this.sessionList = sessionList;
        this.delay = delay;
        connectToCassandra();
    }
    
    public List<Session> getSessionList() {
        return sessionList;
    }

    public int getDelay() {
        return delay;
    }

    public synchronized ConnectionHandler getCassandraConnection() {
        if(this.conn == null) {
            connectToCassandra();
        }
        return conn;
    }

    public void cancel() {
        if(conn != null) {
            conn.close();
        }
    }

    private void connectToCassandra() {
        if(conn == null) {
            conn = new ConnectionHandler();
            conn.connect();
        }
    }
}
