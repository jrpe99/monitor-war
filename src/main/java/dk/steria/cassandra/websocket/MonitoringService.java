package dk.steria.cassandra.websocket;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import javax.websocket.Session;

/**
 *
 * @author jorperss
 */
class MonitoringService {
    private final Timer monitorTimer = new Timer();
    private List<Session> sessionList = new ArrayList<>();
    private boolean started = false;
    private static MonitoringService instance = null;
    
    private MonitoringService() {
        
    }
    
    public static MonitoringService getInstance() {
        if(instance == null) {
            instance = new MonitoringService();
        }
        return instance;
    }
    
    synchronized void addSession(Session session) {
        sessionList.add(session);
    }
    
    synchronized void removeSession(Session session) {
        sessionList.remove(session);
    }
    
    synchronized void start(int interval) {
        if(!started) {
            monitorTimer.schedule(new MonitoringTask(this.sessionList), interval);
            started = true;
        }
    }

    synchronized void stop() {
        if(started) {
            started = false;
            monitorTimer.cancel();
            monitorTimer.purge();
        }
    }
}
