package dk.jrpe.monitor.websocket;

import dk.jrpe.monitor.service.chart.ChartEnum;
import dk.jrpe.monitor.service.MonitorConstant;
import java.io.IOException;
import java.util.EnumSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.EncodeException;
import javax.websocket.Session;

/**
 *
 * @author Jörgen Persson
 */
public class WebSocketHelper {
    
    public static void sendChartListToAll(List<Session> sessionList, final ChartEnum ... chartList) {
        if(sessionList.size() > 0) {
            sessionList.stream().forEach((session) -> {
                for (ChartEnum chart : chartList) {
                    EnumSet chartSubscription = (EnumSet)session.getUserProperties().get(MonitorConstant.CHART_SUBSCRIPTION);
                    if(chartSubscription != null && chartSubscription.contains(chart)) {
                        WebSocketHelper.send(session, chart.getJson());
                    }
                }
            });
        }
    }
    
    public static void send(Session session, String json) {
        try {
            System.out.println("Send to session : " + session.getId());
            System.out.println("Message : " + json);
            session.getBasicRemote().sendObject(json);
        } catch (IOException | EncodeException ex) {
            Logger.getLogger(WebSocketHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
