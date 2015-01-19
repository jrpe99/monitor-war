package dk.steria.cassandra.websocket.util;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.EncodeException;
import javax.websocket.Session;

/**
 *
 * @author jorperss
 */
public class WebSocketHelper {
    
    public static void sendToAll(List<Session> sessionList, final String... jsonParam) {
        if(sessionList.size() > 0) {
            sessionList.stream().forEach((session) -> {
                for (String json : jsonParam) {
                    WebSocketHelper.send(session, json);
                }
            });
        }
    }
    
    public static void send(Session session, String msg) {
        try {
            System.out.println("Send to session : " + session.getId());
            System.out.println("Message : " + msg);
            session.getBasicRemote().sendObject(msg);
        } catch (IOException | EncodeException ex) {
            Logger.getLogger(WebSocketHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
