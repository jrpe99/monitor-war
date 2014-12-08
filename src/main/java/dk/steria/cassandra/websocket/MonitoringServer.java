package dk.steria.cassandra.websocket;

import java.util.ArrayList;
import java.util.List;
import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(
    value = "/monitor"
)
public class MonitoringServer {

    private static final MonitoringService monitorService = MonitoringService.getInstance();
            
    @OnOpen
    public void handleOpenConnection(Session session, CloseReason reason) {
        monitorService.start(1000);
    }

    @OnClose
    public void handleClosedConnection(Session session, CloseReason reason) {
        if(session.getOpenSessions().isEmpty()) {
            monitorService.stop();
        }
    }

    @OnMessage
    public void handleMessage(String message, Session session){
    }
}