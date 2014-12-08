package dk.steria.cassandra.websocket.util;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.EncodeException;
import javax.websocket.Session;

/**
 *
 * @author jorperss
 */
public class WebSocketHelper {
    public static void send(Session session, String msg) {
        try {
            System.out.println("Send to session : " + session.getId());
            session.getBasicRemote().sendObject(msg);
        } catch (IOException | EncodeException ex) {
            Logger.getLogger(WebSocketHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
