/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.jrpe.monitor.service.output;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import javax.websocket.Session;

/**
 *
 * @author Jörgen Persson
 */
public class ChartSubscription {
    public void subscribe(Session session, List<String> chartTypes) {
        session.getUserProperties().put(Constant.CHART_SUBSCRIPTION, subscribe(chartTypes));
    }
    
    public Set<ChartEnum> subscribe(List<String> chartTypes) {
        List<ChartEnum> list = new ArrayList<>();
        for(String chartName: chartTypes) {
            list.add(ChartEnum.valueOf(chartName));
        }
        return EnumSet.copyOf(list);
    }
}
