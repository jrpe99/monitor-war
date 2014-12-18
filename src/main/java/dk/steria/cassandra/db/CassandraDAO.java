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
        String cql = "SELECT ip_address, requests from httpaccess.http_failed";
        ResultSet resultList = conn.execute(cql);
        return resultList.all();
    }
    public List<Row> getHttpAccessInterval(ConnectionHandler conn, int hour) {
        String cql = "SELECT * from httpaccess.http_access where hour = "+hour;
        ResultSet resultList = conn.execute(cql);
        return resultList.all();
    }
    public List<Row> getHttpSuccessPerMinute(ConnectionHandler conn, String date, String from, String to) {
        StringBuilder cql = new StringBuilder();
        cql.append(" SELECT * from httpaccess.http_success_per_minute");
        cql.append(" where");
        cql.append(" date='").append(date).append("'");
        cql.append(" and");
        cql.append(" date_to_minute >= '").append(from).append("'");
        cql.append(" and");
        cql.append(" date_to_minute <= '").append(to).append("'");
        ResultSet resultList = conn.execute(cql.toString());
        return resultList.all();
    }
    public List<Row> getHttpFailedPerMinute(ConnectionHandler conn, String date, String from, String to) {
        StringBuilder cql = new StringBuilder();
        cql.append(" SELECT * from httpaccess.http_failed_per_minute");
        cql.append(" where");
        cql.append(" date='").append(date).append("'");
        cql.append(" and");
        cql.append(" date_to_minute >= '").append(from).append("'");
        cql.append(" and");
        cql.append(" date_to_minute <= '").append(to).append("'");
        ResultSet resultList = conn.execute(cql.toString());
        return resultList.all();
    }
}
