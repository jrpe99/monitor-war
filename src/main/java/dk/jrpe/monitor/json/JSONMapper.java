package dk.jrpe.monitor.json;

import dk.jrpe.monitor.db.httpaccess.to.JsonHTTPAccessTO;
import dk.jrpe.monitor.service.command.Command;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.codehaus.jackson.map.ObjectMapper;

/**
 *
 * @author Jörgen Persson
 * @param <T>
 */
public class JSONMapper<T> {
    private static final ObjectMapper jsonMapper = new ObjectMapper();

    public static Command toObject(String json, Class clazz) {
        try {
            return (Command)jsonMapper.readValue(json, clazz);
        } catch (IOException ex) {
            Logger.getLogger(JSONMapper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static JsonHTTPAccessTO toJsonHTTPAccessTO(String json) {
        try {
            return (JsonHTTPAccessTO)jsonMapper.readValue(json, JsonHTTPAccessTO.class);
        } catch (IOException ex) {
            Logger.getLogger(JSONMapper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static String toJSON(Object object) {
        try {
            return jsonMapper.writeValueAsString(object);
        } catch (IOException ex) {
            Logger.getLogger(JSONMapper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
