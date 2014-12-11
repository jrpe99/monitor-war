package dk.steria.cassandra.db;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import dk.steria.cassandra.json.PieChartResultAdapter;
import dk.steria.cassandra.json.ResultAdapterHelper;
import java.util.List;

/**
 *
 * @author Jörgen Persson
 */
public class CassandraDAO {
    public String getHttpSuccess(ConnectionHandler conn) {
        String cql = "SELECT ip_address, successful_requests from httpaccess.http_success";
        ResultSet resultList = conn.execute(cql);
        List<Row> rowList = resultList.all();
        ResultAdapterHelper.sortOnLongField(rowList, "successful_requests");

        return PieChartResultAdapter.httpSuccessToJSON(rowList);
    }
    public String getHttpFailure(ConnectionHandler conn) {
        String cql = "SELECT ip_address, failed_requests from httpaccess.http_failure";
        ResultSet resultList = conn.execute(cql);
        List<Row> rowList = resultList.all();
        ResultAdapterHelper.sortOnLongField(rowList, "failed_requests");
        
        return PieChartResultAdapter.httpFailureToJSON(rowList);
    }
}
