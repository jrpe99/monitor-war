/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.jrpe.monitor.service.input.command;

import dk.jrpe.monitor.service.input.CmdMessage;

/**
 *
 * @author jorperss
 */
public class ChartSubscriptionCmd implements Command {
    public void execute(CmdMessage cmd) {
        System.out.println(cmd.getCommand().toString());
    }
}
