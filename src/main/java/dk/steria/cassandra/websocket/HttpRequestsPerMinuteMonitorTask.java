package dk.steria.cassandra.websocket;

import com.datastax.driver.core.Row;
import dk.steria.cassandra.db.CassandraReadDAO;
import dk.steria.cassandra.json.LineChartResultAdapter;
import dk.steria.cassandra.websocket.util.WebSocketHelper;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import javax.websocket.Session;

/**
 *
 * @author jorperss
 */
public class HttpRequestsPerMinuteMonitorTask extends MonitoringTask {

    public HttpRequestsPerMinuteMonitorTask(int timeInterval) {
        super(timeInterval);
    }
    
    @Override
    public void run() {
        connectToCassandra();
        
        CassandraReadDAO dao = new CassandraReadDAO(this.conn);

        ZonedDateTime now = ZonedDateTime.now().withSecond(0).withNano(0);
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        String date = formatter.format(now);
        formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        String to = formatter.format(now);
        String from = formatter.format(now.minusMinutes(30));

        HashMap<String, List<Row>> dataSetMap = new HashMap<>();
        
        List<Row> successList = dao.getHttpSuccessPerMinute(date, from, to);
        List<Row> failedList = dao.getHttpFailedPerMinute(date, from, to);
        dataSetMap.put("Success", successList);
        dataSetMap.put("Failed", failedList);
        String json = LineChartResultAdapter.toJSON(dataSetMap, 30);
        
        this.sessionList.stream().forEach((session) -> {
            WebSocketHelper.send(session, json);
        });
    }
}
