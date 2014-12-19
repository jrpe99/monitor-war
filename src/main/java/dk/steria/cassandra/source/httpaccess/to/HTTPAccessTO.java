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

    public void setHttpStatus(String httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDateToMinute() {
        return dateToMinute;
    }

    public void setDateToMinute(String dateToMinute) {
        this.dateToMinute = dateToMinute;
    }
}
