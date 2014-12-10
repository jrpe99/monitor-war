package dk.steria.cassandra.db;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import dk.steria.cassandra.db.json.ResultAdapter;
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
        ResultAdapter.sort(rowList, "successful_requests");

        return ResultAdapter.httpSuccessToJSON(rowList);
    }
    public String getHttpFailure(ConnectionHandler conn) {
        String cql = "SELECT ip_address, failed_requests from httpaccess.http_failure";
        ResultSet resultList = conn.execute(cql);
        List<Row> rowList = resultList.all();
        ResultAdapter.sort(rowList, "failed_requests");
        
        return ResultAdapter.httpFailureToJSON(rowList);
    }
}
