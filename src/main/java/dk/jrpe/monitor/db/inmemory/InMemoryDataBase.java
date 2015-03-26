package dk.jrpe.monitor.db.inmemory;

import dk.jrpe.monitor.db.httpaccess.to.HTTPAccessTO;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.stream.Collectors;

/**
 *
 * @author Jörgen Persson
 */
public class InMemoryDataBase {
    private static class InstanceHolder {
        static final InMemoryDataBase instance = new InMemoryDataBase();
    }

    /*
      <IP Address, RequestCount>
    */
    private final ConcurrentHashMap<String,Long> httpSuccess = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String,Long> httpFailed = new ConcurrentHashMap<>();

    /*
      HTTP Access success <Date to minute, RequestCount>
    */
    private final ConcurrentSkipListMap<String,Long> httpSuccessPerMinute = new ConcurrentSkipListMap<>();
    /*
      HTTP Access failed <Date to minute, RequestCount>
    */
    private final ConcurrentSkipListMap<String,Long> httpFailedPerMinute = new ConcurrentSkipListMap<>();

    public static InMemoryDataBase getInstance() {
        return InstanceHolder.instance;
    }

    public List<HTTPAccessTO> getHttpSuccess() {
        return this.httpSuccess.entrySet().stream()
                .map((entry) -> new HTTPAccessTO.Builder()
                        .setIPAdress(entry.getKey())
                        .setRequests(entry.getValue()).build())
                .collect(Collectors.toList());
    }

    public List<HTTPAccessTO> getHttpFailed() {
        return this.httpFailed.entrySet().stream()
                .map((entry) -> new HTTPAccessTO.Builder()
                        .setIPAdress(entry.getKey())
                        .setRequests(entry.getValue()).build())
                .collect(Collectors.toList());
    }

    public List<HTTPAccessTO> getHttpSuccessPerMinute(String date, String from, String to) {
        return getHttpAccessPerMinute(this.httpSuccessPerMinute.tailMap(from));
    }
    
    public List<HTTPAccessTO> getHttpFailedPerMinute(String date, String from, String to) {
        return getHttpAccessPerMinute(this.httpFailedPerMinute.tailMap(from));
    }

    public void updateHttpSuccess(HTTPAccessTO to) {
        Long requests = this.httpSuccess.putIfAbsent(to.getIpAddress(), to.getRequests());
        if(requests != null) {
            this.httpSuccess.put(to.getIpAddress(), ++requests);
        }
    }

    public void updateHttpSuccessPerMinute(HTTPAccessTO to) {
        Long requests = this.httpSuccessPerMinute.putIfAbsent(to.getDateToMinute(), to.getRequests());
        if(requests != null) {
            this.httpSuccessPerMinute.put(to.getDateToMinute(), ++requests);
        }
    }

    public void updateHttpFailed(HTTPAccessTO to) {
        Long requests = this.httpFailed.putIfAbsent(to.getIpAddress(), to.getRequests());
        if(requests != null) {
            this.httpFailed.put(to.getIpAddress(), ++requests);
        }
    }

    public void updateHttpFailedPerMinute(HTTPAccessTO to) {
        Long requests = this.httpFailedPerMinute.putIfAbsent(to.getDateToMinute(), to.getRequests());
        if(requests != null) {
            this.httpFailedPerMinute.put(to.getDateToMinute(), ++requests);
        }
    }

    private List<HTTPAccessTO> getHttpAccessPerMinute(ConcurrentNavigableMap<String, Long> periodMap) {
        return periodMap.values().stream()
            .map((value) -> new HTTPAccessTO.Builder().setRequests(value).build())
            .collect(Collectors.toList());
    }    
}
