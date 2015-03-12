/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.jrpe.monitor.websocket;

import dk.jrpe.monitor.service.input.CmdMessage;
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

    private static final ObjectMapper jsonMapper = new ObjectMapper();
    
    /**
     * Decode the JSON string to a command object.
     * Example JSON:
     * {"command":"CHART_SUBSCRIPTION","chartSubscription":["LINE_SUCCESS_AND_FAILED", "PIE_SUCCESS"]}
     * @param json
     * @return
     * @throws DecodeException
     */
    @Override public CmdMessage decode(String json) throws DecodeException {
        CmdMessage cmd = null;
        String commandString;
        try {
            // Check if there is more data then the command string.
            if(json.contains(",")) {
                // Parse the command string
                commandString = json.substring(0,json.indexOf(","))+"}";
            } else {
                commandString = json;
                json = null;
            }
            cmd = jsonMapper.readValue(commandString, CmdMessage.class);
            cmd.setJson(json);
        } catch (IOException ex) {
            Logger.getLogger(JSONDecoder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cmd;
    }
    
    @Override
    public boolean willDecode(String json) {
        if(json.contains("command")) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void init(EndpointConfig config) {
    }

    @Override
    public void destroy() {
    }
}
