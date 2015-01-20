package dk.steria.cassandra.source.httpaccess.to;

/**
 *
 * @author Jörgen Persson
 */
public class HTTPAccessTO {
    private String httpStatus;
    private String ipAddress;
    private String action;
    private String url;
    private String date;
    private String dateToMinute;    

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
    
    private HTTPAccessTO(HTTPAccessTOBuilder builder) {
        this.httpStatus = builder.httpStatus;
        this.ipAddress = builder.ipAddress;
        this.date = builder.date;
        this.dateToMinute = builder.dateToMinute;
        this.action = builder.action;
        this.url = builder.url;
    }
    
    public static class HTTPAccessTOBuilder{
        private String httpStatus;
        private String ipAddress;
        private String date;
        private String dateToMinute;    

        private String action = "GET";
        private String url = "/";
        
        public HTTPAccessTOBuilder(String httpStatus, String ipAddress, String date, String dateToMinute) {
            this.httpStatus = httpStatus;
            this.ipAddress = ipAddress;
            this.date = date;
            this.dateToMinute = dateToMinute;
        }

        public HTTPAccessTOBuilder setAction(String action) {
            this.action = action;
            return this;
        }

        public HTTPAccessTOBuilder setUrl(String url) {
            this.url = url;
            return this;
        }
        
        public HTTPAccessTO build() {
            return new HTTPAccessTO(this);
        }
    }    
}
