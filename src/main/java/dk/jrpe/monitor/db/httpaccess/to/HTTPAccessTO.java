package dk.jrpe.monitor.db.httpaccess.to;

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
    private final String dateTime;    
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

    public String getDateTime() {
        return dateTime;
    }

    public Long getRequests() {
        return requests;
    }

    public HTTPAccessTO(JsonHTTPAccessTO to) {
        this.httpStatus = to.getHttpStatus();
        this.ipAddress = to.getIpAddress();
        this.date = to.getDate();
        this.dateToMinute = to.getDateToMinute();
        this.dateTime = to.getDateTime();
        this.action = to.getAction();
        this.url = to.getUrl();
        this.requests = to.getRequests();
    }
    
    private HTTPAccessTO(Builder builder) {
        this.httpStatus = builder.httpStatus;
        this.ipAddress = builder.ipAddress;
        this.date = builder.date;
        this.dateToMinute = builder.dateToMinute;
        this.dateTime = builder.dateTime;
        this.action = builder.action;
        this.url = builder.url;
        this.requests = builder.requests;
    }
    
    public static class Builder{
        private String httpStatus;
        private String ipAddress;
        private String date;
        private String dateTime;
        private String dateToMinute;    
        private Long requests = new Long("0");
        
        private String action = "GET";
        private String url = "/";
        
        public Builder setHttpStatus(String httpStatus) {
            this.httpStatus = httpStatus;
            return this;
        }

        public Builder setIPAdress(String ipAddress) {
            this.ipAddress = ipAddress;
            return this;
        }

        public Builder setDate(String date) {
            this.date = date;
            return this;
        }

        public Builder setDateTime(String dateTime) {
            this.dateTime = dateTime;
            return this;
        }

        public Builder setDateToMinute(String dateToMinute) {
            this.dateToMinute = dateToMinute;
            return this;
        }

        public Builder setAction(String action) {
            this.action = action;
            return this;
        }

        public Builder setUrl(String url) {
            this.url = url;
            return this;
        }

        public Builder setRequests(Long requests) {
            this.requests = requests;
            return this;
        }
        
        public HTTPAccessTO build() {
            return new HTTPAccessTO(this);
        }
    }

    @Override
    public String toString() {
        return "HTTPAccessTO{" + "httpStatus=" + httpStatus + ", ipAddress=" + ipAddress + ", action=" + action + ", url=" + url + ", date=" + date + ", dateToMinute=" + dateToMinute + ", dateTime=" + dateTime + ", requests=" + requests + '}';
    }
}
