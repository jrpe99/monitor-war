package dk.jrpe.monitor.service.input;

import dk.jrpe.monitor.json.JSONMapper;
import dk.jrpe.monitor.service.input.command.ChartSubscriptionCmd;
import java.util.function.Consumer;
import javax.websocket.Session;

/**
 * 
 * @author Jörgen Persson
 */
public class CommandHandler {
    public enum CommandEnum {
        CHART_SUBSCRIPTION((cmdHandler -> {
            JSONMapper.toObject(cmdHandler.getJson(), ChartSubscriptionCmd.class).execute(cmdHandler);
        }));
        
        private final Consumer<CommandHandler> cmdHandler;
        
        CommandEnum(Consumer<CommandHandler> cmdHandler) {
            this.cmdHandler = cmdHandler;
        }
        
        public void execute(CommandHandler cmdHandler) {
            this.cmdHandler.accept(cmdHandler);
        }
    }

    private Session session;
    private CommandEnum command;
    private String json;

    public void execute() {
        this.command.execute(this);
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public CommandEnum getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = CommandEnum.valueOf(command);
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }
    
    @Override
    public String toString() {
        return command.toString();
    }
}
