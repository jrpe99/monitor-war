package dk.jrpe.monitor.service.chart;

import dk.jrpe.monitor.service.MonitorConstant;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import javax.websocket.Session;

/**
 *
 * @author Jörgen Persson
 */
public class ChartSubscriptionHandler {
    public static void subscribe(Session session, List<String> chartTypes) {
        synchronized(session) {
            session.getUserProperties().put(MonitorConstant.CHART_SUBSCRIPTION, subscribe(chartTypes));
        }
    }
    
    public static Set<ChartEnum> subscribe(List<String> chartTypes) {
        List<ChartEnum> list = new ArrayList<>();
        chartTypes.stream().forEach((chartName) -> {
            list.add(ChartEnum.valueOf(chartName));
        });
        return EnumSet.copyOf(list);
    }
}
