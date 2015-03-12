package dk.jrpe.monitor.service.input.command;

import dk.jrpe.monitor.service.input.CommandHandler;

/**
 *
 * @author Jörgen Persson
 */
public abstract class Command {
    private String command;

    public abstract void execute(CommandHandler cmdMsg);

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }
}
