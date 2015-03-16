package dk.jrpe.monitor.task;

import dk.jrpe.monitor.db.strategy.DataSourceStrategy;
import dk.jrpe.monitor.db.cassandra.CassandraConnectionHandler;
import java.util.List;
import javax.websocket.Session;

/**
 * Parent of all monitoring tasks.
 * 
 * @author Jörgen Persson
 */
public abstract class MonitoringTask implements Runnable {

    private int delay = 1000;
    private List<Session> sessionList = null;

    private DataSourceStrategy dataSource = null;

    MonitoringTask(DataSourceStrategy dataSource, List<Session> sessionList, int delay) {
        this.sessionList = sessionList;
        this.delay = delay;
        this.dataSource = dataSource;
        this.dataSource.open();
    }
    
    public List<Session> getSessionList() {
        return sessionList;
    }

    public int getDelay() {
        return delay;
    }

    public DataSourceStrategy getDataSouce() {
        return dataSource;
    }
    
    public void cancel() {
        this.dataSource.close();
    }
}
