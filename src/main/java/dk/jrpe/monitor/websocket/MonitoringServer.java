package dk.jrpe.monitor.websocket;

import dk.jrpe.monitor.service.MonitoringService;
import dk.jrpe.monitor.service.input.CommandHandler;
import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 * WebSocket end-point for the monitoring service.
 * 
 * @author Jörgen Persson
 */
@ServerEndpoint(
    value = "/monitor",
    decoders = {JSONDecoder.class}
)
public class MonitoringServer {

    /**
     * Create the monitoring service when the WebSocket end-point is created.
     */
    private static final MonitoringService monitorService = MonitoringService.getInstance();
            
    /**
     * When a new client connects, add the client session to the
     * monitoring service.
     * 
     * @param session
     */
    @OnOpen
    public void handleOpenConnection(Session session) {
        monitorService.addSession(session);
    }

    /**
     * When a client disconnects, remove the client session from the
     * monitoring service.
     *
     * @param session
     * @param reason
     */
    @OnClose
    public void handleClosedConnection(Session session, CloseReason reason) {
        monitorService.removeSession(session);
    }

    /**
     * Handle commands sent from the client.
     * @param cmd
     * @param session
     */
    @OnMessage
    public void handleMessage(CommandHandler cmd, Session session){
        cmd.setSession(session);
        monitorService.handleCommand(cmd);
    }
}