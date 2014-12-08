package dk.steria.cassandra.websocket;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;
import javax.websocket.Session;

/**
 *
 * @author jorperss
 */
class MonitoringTask extends TimerTask {
    private List<Session> sessionList = new ArrayList<>();

    MonitoringTask(List<Session> sessionList) {
        this.sessionList = sessionList;
    }

    @Override
    public void run() {
        
    }
}
