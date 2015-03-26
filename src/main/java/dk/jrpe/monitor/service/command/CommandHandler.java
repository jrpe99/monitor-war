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
     * Concrete command implementations.
     */
    public enum CommandEnum {

        /**
         * Command for subscribing to one or more charts. The subscription is
         * stored in the web-socket session object, and only charts subscribed 
         * for, are sent to the specific client (session).
         */
        CHART_SUBSCRIPTION((cmdHandler -> {
            Command cmd = JSONMapper.toObject(cmdHandler.getJson(), ChartSubscriptionCmd.class);
            if(cmd != null) cmd.execute(cmdHandler);
        })),
        
        /**
         * The following commands, are for sending HTTP access data to the monitoring 
         * application. This is used when a client is not storing data directly in
         * a common database, like when in memory database is used.
         */
        SEND_HTTP_SUCCESS_DATA((cmdHandler -> { new SendHttpSuccessDataCmd().execute(cmdHandler); })),
        SEND_HTTP_SUCCESS_PER_MINUTE_DATA((cmdHandler -> { new SendHttpSuccessPerMinuteDataCmd().execute(cmdHandler); })),
        SEND_HTTP_FAILED_DATA((cmdHandler -> { new SendHttpFailedDataCmd().execute(cmdHandler); })),
        SEND_HTTP_FAILED_PER_MINUTE_DATA((cmdHandler -> { new SendHttpFailedPerMinuteDataCmd().execute(cmdHandler); }));
        
        
        private final Consumer<CommandHandler> cmdHandler;
        
        CommandEnum(Consumer<CommandHandler> cmdHandler) {
            this.cmdHandler = cmdHandler;
        }
        
        public void execute(CommandHandler cmdHandler) {
            this.cmdHandler.accept(cmdHandler);
        }
    }

    private static final ObjectMapper jsonMapper = new ObjectMapper();

    private CommandEnum command;
    private Session session;
    private String json;

    private void executeConcreteCommand() {
        System.out.println("Execute concrete command: " + this.command.toString());
        this.command.execute(this);
    }

    /**
     * Decode the JSON string to a concrete command object and execute the concrete command.
     * Example JSON:
     * {"command":"CHART_SUBSCRIPTION","chartSubscription":["LINE_SUCCESS_AND_FAILED", "PIE_SUCCESS"]}
     * @param json
     * @param session
     * @throws IllegalArgumentException 
     *      if the JSON is null. 
     *      if the JSON does not contain a command.
     *      if the JSON cannot be parsed.
     */
    public static void execute(String json, Session session) throws IllegalArgumentException {
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
            cmd.setSession(session);
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
     * Set the command. Should match one of the values in CommandEnum.
     * @param command
     */
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
