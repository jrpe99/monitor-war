package dk.jrpe.monitor.service.input;

import dk.jrpe.monitor.service.input.command.ChartSubscriptionCmd;
import java.util.List;
import java.util.function.Consumer;

/**
 *
 * @author Jörgen Persson
 */
public class CmdMessage {
    public enum Command {
        CHART_SUBSCRIPTION((cmd -> {new ChartSubscriptionCmd().execute(cmd);}));
        
        private Consumer<CmdMessage> cmdMsg;
        
        Command(Consumer<CmdMessage> cmdMsg) {
            this.cmdMsg = cmdMsg;
        }
        
        public void execute(CmdMessage cmdMsg) {
            this.cmdMsg.accept(cmdMsg);
        }
    }

    private Command command;
    private List<String> chartSubscription;

    public void execute() {
        this.command.execute(this);
    }
    
    public List<String> getChartSubscription() {
        return chartSubscription;
    }

    public void setChartSubscription(List<String> chartSubscription) {
        this.chartSubscription = chartSubscription;
    }

    public Command getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = Command.valueOf(command);
    }

    @Override
    public String toString() {
        return command.toString();
    }
}
