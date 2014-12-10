package dk.steria.cassandra.db;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import dk.steria.cassandra.db.json.ResultAdapter;
import java.util.List;

/**
 *
 * @author jorperss
 */
public class CassandraDAO {
    public String getHttpSuccess(ConnectionHandler conn) {
        String cql = "SELECT ip_address, successful_requests from httpaccess.http_success";
        ResultSet resultList = conn.execute(cql);
        List<Row> allRows = resultList.all();

        for (Row row : allRows) {
            System.out.println(String.format("%-30s\t%-20s", row.getString("ip_address"), row.getLong("successful_requests")));
        }
        return ResultAdapter.toJSON(allRows);
    }
}
