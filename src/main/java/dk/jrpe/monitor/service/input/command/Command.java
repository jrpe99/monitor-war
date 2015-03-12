package dk.jrpe.monitor.service.input.command;

/**
 *
 * @author Jörgen Persson
 */
public abstract class Command {
    private String command;

    public abstract void execute();

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }
}
