package dk.steria.cassandra.monitoring.service;

import dk.steria.cassandra.monitoring.task.HttpRequestsMonitorTask;
import dk.steria.cassandra.monitoring.task.HttpRequestsPerMinuteMonitorTask;
import dk.steria.cassandra.monitoring.task.MonitoringTask;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import javax.websocket.Session;

/**
 * General purpose monitoring service.
 * 
 * The service is a Singelton. It is responsible for creating monitor tasks,
 * which are added to a monitor task list. When all tasks have been added
 * they are scheduled (started) using a Timer.
 * 
 * @author Jörgen Persson
 */
public class MonitoringService {
    private static class InstanceHolder {
        static final MonitoringService instance = new MonitoringService();
    }
    
    private final Timer monitorTimer = new Timer();
    
    private final List<MonitoringTask> monitoringTaskList = new ArrayList<>();

    private MonitoringService() {
        this.monitoringTaskList.add(new HttpRequestsMonitorTask(1000));
        this.monitoringTaskList.add(new HttpRequestsPerMinuteMonitorTask(1000));
        start();
    }
    
    /**
     * Return the instance of the monitoring service.
     * @return
     */
    public static MonitoringService getInstance() {
        return InstanceHolder.instance;
    }
    
    /**
     * Add a WebSocket session to each monitoring task.
     * 
     * @param session
     */
    public void addSession(Session session) {
        for (MonitoringTask monitoringTask : monitoringTaskList) {
            monitoringTask.addSession(session);
        }
    }
    
    /**
     * Remove a WebSocket session from each monitoring task.
     *
     * @param session
     */
    public void removeSession(Session session) {
        for (MonitoringTask monitoringTask : monitoringTaskList) {
            monitoringTask.removeSession(session);
        }
    }

    /**
     * Stop the monitoring timer.
     */
    public void stop() {
        monitorTimer.cancel();
        monitorTimer.purge();
    }

    /**
     * Schedule all monitoring tasks.
     */
    private void start() {
        for (MonitoringTask monitoringTask : monitoringTaskList) {
            monitorTimer.schedule(monitoringTask, 0, monitoringTask.getTimeInterval());
        }
    }
}