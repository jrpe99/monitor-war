package dk.jrpe.monitor.service.input.command;

import dk.jrpe.monitor.service.input.CmdMessage;

/**
 *
 * @author Jörgen Persson
 */
public interface Command {
    public void execute(CmdMessage cmd);
}
