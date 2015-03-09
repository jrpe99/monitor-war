package dk.jrpe.cassandra.monitoring.service;

import dk.jrpe.cassandra.monitoring.task.HttpRequestsMonitorTask;
import dk.jrpe.cassandra.monitoring.task.HttpRequestsPerMinuteMonitorTask;
import dk.jrpe.cassandra.monitoring.task.MonitoringTask;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
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
    
//    private final Timer monitorTimer = new Timer();
    private ScheduledExecutorService monitorTimer = null;
    
    private final List<MonitoringTask> monitoringTaskList = new ArrayList<>();
    private List<Session> sessionList = new CopyOnWriteArrayList<>();

    private MonitoringService() {
        this.monitoringTaskList.add(new HttpRequestsMonitorTask(sessionList, 1000));
        this.monitoringTaskList.add(new HttpRequestsPerMinuteMonitorTask(sessionList, 1000));
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
        sessionList.add(session);
    }
    
    /**
     * Remove a WebSocket session from each monitoring task.
     *
     * @param session
     */
    public void removeSession(Session session) {
        sessionList.remove(session);
    }

    /**
     * Stop the monitoring timer.
     */
    public void stop() {
        monitorTimer.shutdown();
        monitoringTaskList.stream().forEach((monitoringTask) -> {
            monitoringTask.cancel();
        });
    }

    /**
     * Schedule all monitoring tasks.
     */
    private void start() {
        monitorTimer = Executors.newScheduledThreadPool(monitoringTaskList.size());
        monitoringTaskList.stream().forEach((monitoringTask) -> {
            monitorTimer.scheduleWithFixedDelay(monitoringTask, 0, monitoringTask.getDelay(), TimeUnit.MILLISECONDS);
        });
    }
}
