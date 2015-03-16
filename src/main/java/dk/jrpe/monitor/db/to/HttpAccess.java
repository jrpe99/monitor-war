package dk.jrpe.monitor.db.to;

/**
 *
 * @author jorperss
 */
public class HttpAccess {
    private final String ipAddress;
    private final Long requests;

    public HttpAccess(String ipAddress, Long requests) {
        this.ipAddress = ipAddress;
        this.requests = requests;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public Long getRequests() {
        return requests;
    }
}
