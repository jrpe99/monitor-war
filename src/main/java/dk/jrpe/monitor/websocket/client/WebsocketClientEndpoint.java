package dk.jrpe.monitor.websocket.client;

import java.io.IOException;
import java.net.URI;
import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

/**
 * Used by a client sending information the monitoring end-point.
 * @author Jörgen Persson
 */
@ClientEndpoint
public class WebsocketClientEndpoint {
    Session session = null;
    public WebsocketClientEndpoint(URI endpointURI) {
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            container.connectToServer(this, endpointURI);
        } catch (DeploymentException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param session 
     */
    @OnOpen
    public void onOpen(Session session) {
        System.out.println("Open websocket");
        this.session = session;
    }

    /**
     * @param userSession the session which is getting closed.
     * @param reason the reason for connection close
     */
    @OnClose
    public void onClose(Session userSession, CloseReason reason) {
        System.out.println("Close websocket");
        this.session = null;
    }

    /**
     * @param message
     */
    @OnMessage
    public void onMessage(String message) {
        System.out.println(message);
    }
    
    public void send(String data) throws IOException, EncodeException {
        this.session.getBasicRemote().sendText(data);
    }
}
