package dk.steria.cassandra.db;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import java.util.List;

/**
 *
 * @author Jörgen Persson
 */
public class CassandraDAO {
    public List<Row> getHttpSuccess(ConnectionHandler conn) {
        String cql = "SELECT ip_address, requests from httpaccess.http_success";
        ResultSet resultList = conn.execute(cql);
        return resultList.all();
    }
    public List<Row> getHttpFailure(ConnectionHandler conn) {
        String cql = "SELECT ip_address, requests from httpaccess.http_failure";
        ResultSet resultList = conn.execute(cql);
        return resultList.all();
    }
    public List<Row> getHttpAccessInterval(ConnectionHandler conn, int hour) {
        String cql = "SELECT * from httpaccess.http_access where hour = "+hour;
        ResultSet resultList = conn.execute(cql);
        return resultList.all();
    }
}
