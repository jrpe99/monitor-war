package dk.jrpe.monitor.websocket.client.to;

import dk.jrpe.monitor.db.dao.httpaccess.to.HTTPAccessTO;
import java.io.Serializable;

/**
 *
 * @author Jörgen Persson
 */
public class HTTPAccessDataWrapper implements Serializable {
    public enum Type {
        HTTP_SUCCESS, HTTP_SUCCESS_PER_MINUTE, HTTP_FAILED, HTTP_FAILED_PER_MINUTE
    }
    private final HTTPAccessTO to;
    private final Type type;
    public HTTPAccessDataWrapper(HTTPAccessTO to, HTTPAccessDataWrapper.Type type) {
        this.to = to;
        this.type = type;
    }
    public HTTPAccessTO getTo() {
        return to;
    }
    public HTTPAccessDataWrapper.Type getType() {
        return type;
    }
}
