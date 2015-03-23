package dk.jrpe.monitor.service.command;

import dk.jrpe.monitor.json.JSONMapper;
import java.io.IOException;
import java.util.function.Consumer;
import javax.websocket.Session;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * The command handler holds concrete command implementations
 * represented by the command ENUM. 
 * 
 * @author Jörgen Persson
 */
public class CommandHandler {

    /**
     * Add concrete command implementations in the CommandEnum.
     */
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

    private static final ObjectMapper jsonMapper = new ObjectMapper();
    private Session session;
    private CommandEnum command;
    private String json;

    private void executeConcreteCommand() {
        this.command.execute(this);
    }

    /**
     * Decode the JSON string to a concrete command object and execute the concrete command.
     * Example JSON:
     * {"command":"CHART_SUBSCRIPTION","chartSubscription":["LINE_SUCCESS_AND_FAILED", "PIE_SUCCESS"]}
     * @param json
     * @throws IllegalArgumentException 
     *      if the JSON is null. 
     *      if the JSON does not contain a command.
     *      if the JSON cannot be parsed.
     */
    public static void execute(String json) throws IllegalArgumentException {
        if(json == null || !json.contains("command")) {
            throw new IllegalArgumentException();
        }
        try {
            String commandString;
            // Check if there is more data then the command string.
            if(json.contains(",")) {
                // Parse the command string
                commandString = json.substring(0,json.indexOf(","))+"}";
            } else {
                commandString = json;
                json = null;
            }
            CommandHandler cmd = jsonMapper.readValue(commandString, CommandHandler.class);
            cmd.setJson(json);
            cmd.executeConcreteCommand();
        } catch (IOException ex) {
            throw new IllegalArgumentException(ex);
        }
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
    /**
     * Decode the JSON string to a concrete command object.
     * Example JSON:
     * {"command":"CHART_SUBSCRIPTION","chartSubscription"}
     * @param commandJson
     */
    public void setCommand(String commandJson) {
        this.command = CommandEnum.valueOf(commandJson);
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
