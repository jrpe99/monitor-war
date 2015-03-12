package dk.jrpe.monitor.service.input.command;

import dk.jrpe.monitor.service.input.CommandHandler;
import dk.jrpe.monitor.service.output.ChartSubscriptionHandler;
import java.util.List;

/**
 *
 * @author Jörgen Persson
 */
public class ChartSubscriptionCmd extends Command {
    private List<String> chartSubscription;

    @Override public void execute(CommandHandler cmdMsg) {
        System.out.println("Execute command");
        System.out.println(toString());
        ChartSubscriptionHandler.subscribe(cmdMsg.getSession(), this.chartSubscription);
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
