package dk.jrpe.monitor.db.to;

/**
 * Immutable TO object for HTTP access data.
 * @author Jörgen Persson
 */
public class HTTPAccessTO {
    private final String httpStatus;
    private final String ipAddress;
    private final String action;
    private final String url;
    private final String date;
    private final String dateToMinute;    
    private final Long requests;
    
    public String getHttpStatus() {
        return httpStatus;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public String getAction() {
        return action;
    }

    public String getUrl() {
        return url;
    }

    public String getDate() {
        return date;
    }

    public String getDateToMinute() {
        return dateToMinute;
    }

    public Long getRequests() {
        return requests;
    }

    private HTTPAccessTO(HTTPAccessTOBuilder builder) {
        this.httpStatus = builder.httpStatus;
        this.ipAddress = builder.ipAddress;
        this.date = builder.date;
        this.dateToMinute = builder.dateToMinute;
        this.action = builder.action;
        this.url = builder.url;
        this.requests = builder.requests;
    }
    
    public static class HTTPAccessTOBuilder{
        private String httpStatus;
        private String ipAddress;
        private String date;
        private String dateToMinute;    
        private Long requests;
        
        private String action = "GET";
        private String url = "/";
        
        public HTTPAccessTOBuilder(String ipAddress) {
            this.ipAddress = ipAddress;
        }

        public HTTPAccessTOBuilder setHttpStatus(String httpStatus) {
            this.httpStatus = httpStatus;
            return this;
        }

        public HTTPAccessTOBuilder setDate(String date) {
            this.date = date;
            return this;
        }

        public HTTPAccessTOBuilder setDateToMinute(String dateToMinute) {
            this.dateToMinute = dateToMinute;
            return this;
        }

        public HTTPAccessTOBuilder setAction(String action) {
            this.action = action;
            return this;
        }

        public HTTPAccessTOBuilder setUrl(String url) {
            this.url = url;
            return this;
        }

        public HTTPAccessTOBuilder setRequests(Long requests) {
            this.requests = requests;
            return this;
        }
        
        public HTTPAccessTO build() {
            return new HTTPAccessTO(this);
        }
    }    
}
