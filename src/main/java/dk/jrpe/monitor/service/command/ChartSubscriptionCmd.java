package dk.jrpe.monitor.service.command;

import dk.jrpe.monitor.service.chart.ChartSubscriptionHandler;
import java.util.List;

/**
 *
 * @author Jörgen Persson
 */
public class ChartSubscriptionCmd extends Command {
    private List<String> chartSubscription;

    @Override public void execute(CommandHandler cmdHandler) {
        ChartSubscriptionHandler.subscribe(cmdHandler.getSession(), this.chartSubscription);
    }
    
    public List<String> getChartSubscription() {
        return this.chartSubscription;
    }

    public void setChartSubscription(List<String> chartSubscription) {
        this.chartSubscription = chartSubscription;
    }

    @Override
    public String toString() {
        return "ChartSubscriptionCmd [" + "chartSubscription " + chartSubscription + " " + "getCommand " + getCommand() + "]";
    }
}
