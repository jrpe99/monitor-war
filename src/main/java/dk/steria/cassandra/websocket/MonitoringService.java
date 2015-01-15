package dk.steria.cassandra.websocket;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import javax.websocket.Session;

/**
 *
 * @author Jörgen Persson
 */
class MonitoringService {
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
    
    public static MonitoringService getInstance() {
        return InstanceHolder.instance;
    }
    
    void stop() {
        monitorTimer.cancel();
        monitorTimer.purge();
    }

    private void start() {
        for (MonitoringTask monitoringTask : monitoringTaskList) {
            monitorTimer.schedule(monitoringTask, 0, monitoringTask.getTimeInterval());
        }
    }

    void addSession(Session session) {
        for (MonitoringTask monitoringTask : monitoringTaskList) {
            monitoringTask.addSession(session);
        }
    }
    
    void removeSession(Session session) {
        for (MonitoringTask monitoringTask : monitoringTaskList) {
            monitoringTask.removeSession(session);
        }
    }
}
