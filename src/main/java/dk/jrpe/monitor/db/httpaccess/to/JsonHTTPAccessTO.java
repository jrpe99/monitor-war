package dk.jrpe.monitor.db.httpaccess.to;

/**
 * @author Jörgen Persson
 */
public class JsonHTTPAccessTO {
    private String command;
    private String httpStatus;
    private String ipAddress;
    private String action;
    private String url;
    private String date;
    private String dateToMinute;    
    private String dateTime;    
    private Long requests;

    public JsonHTTPAccessTO() {
    }
    
    public JsonHTTPAccessTO(HTTPAccessTO to) {
        this.command ="";
        this.httpStatus = to.getHttpStatus();
        this.ipAddress = to.getIpAddress();
        this.date = to.getDate();
        this.dateToMinute = to.getDateToMinute();
        this.dateTime = to.getDateTime();
        this.action = to.getAction();
        this.url = to.getUrl();
        this.requests = to.getRequests();
    }
    
    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

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

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public Long getRequests() {
        return requests;
    }

    public void setRequests(Long requests) {
        this.requests = requests;
    }
    
    

    @Override
    public String toString() {
        return "JsonHTTPAccessTO [" + "action " + action + " " + "command " + command + " " + "date " + date + " " + "dateTime " + dateTime + " " + "dateToMinute " + dateToMinute + " " + "httpStatus " + httpStatus + " " + "ipAddress " + ipAddress + " " + "requests " + requests + " " + "url " + url + "]";
    }
}
