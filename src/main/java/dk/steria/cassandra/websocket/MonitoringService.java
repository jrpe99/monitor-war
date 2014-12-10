package dk.steria.cassandra.websocket;

import java.util.Timer;
import javax.websocket.Session;

/**
 *
 * @author Jörgen Persson
 */
class MonitoringService {
    private final Timer monitorTimer = new Timer();
    private boolean started = false;
    private static MonitoringService instance = null;
    private MonitoringTask monitoringTask = null;

    private MonitoringService() {
        this.monitoringTask = new MonitoringTask();
        start(1000);
    }
    
    public static MonitoringService getInstance() {
        if(instance == null) {
            instance = new MonitoringService();
        }
        return instance;
    }
    
    void addSession(Session session) {
        this.monitoringTask.addSession(session);
    }
    
    void removeSession(Session session) {
        this.monitoringTask.removeSession(session);
    }
    
    private void start(int interval) {
        if(!started) {
            monitorTimer.schedule(this.monitoringTask, 0, interval);
            started = true;
        }
    }

    private void stop() {
        if(started) {
            started = false;
            monitorTimer.cancel();
            monitorTimer.purge();
        }
    }
}
