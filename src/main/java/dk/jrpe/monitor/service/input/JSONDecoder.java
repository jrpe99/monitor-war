/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.jrpe.monitor.service.input;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;
import org.codehaus.jackson.map.ObjectMapper;

/**
 *
 * @author jorperss
 */
public class JSONDecoder implements Decoder.Text<CmdMessage> {

    @Override
    public CmdMessage decode(String json) throws DecodeException {
        CmdMessage cmd = null;
        try {
            cmd = new ObjectMapper().readValue(json, CmdMessage.class);
        } catch (IOException ex) {
            Logger.getLogger(JSONDecoder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cmd;
    }

    @Override
    public boolean willDecode(String arg0) {
        return true;
    }

    @Override
    public void init(EndpointConfig config) {
    }

    @Override
    public void destroy() {
    }
}
