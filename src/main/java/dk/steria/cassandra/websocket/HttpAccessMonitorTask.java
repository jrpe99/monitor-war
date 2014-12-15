/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.steria.cassandra.websocket;

import com.datastax.driver.core.Row;
import dk.steria.cassandra.db.CassandraDAO;
import dk.steria.cassandra.json.PieChartResultAdapter;
import dk.steria.cassandra.json.RadarChartResultAdapter;
import dk.steria.cassandra.json.ResultAdapterHelper;
import dk.steria.cassandra.websocket.util.WebSocketHelper;
import java.util.Calendar;
import java.util.List;
import javax.websocket.Session;

/**
 *
 * @author jorperss
 */
public class HttpAccessMonitorTask extends MonitoringTask {

    public HttpAccessMonitorTask(int timeInterval) {
        super(timeInterval);
    }
    
    @Override
    public void run() {
        connectToCassandra();
        
        CassandraDAO dao = new CassandraDAO();

        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        
        List<Row> rowList = dao.getHttpAccessInterval(conn, hour);
        
        
        for(Session session: this.sessionList) {
//            WebSocketHelper.send(session, successPieChartJSONResult);
        }
    }
}
