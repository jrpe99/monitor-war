package dk.jrpe.monitor.service.command;

/**
 *
 * @author Jörgen Persson
 */
public abstract class Command {
    private String command;

    public abstract void execute(CommandHandler cmdHandler);

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }
}
