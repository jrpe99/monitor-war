package dk.steria.cassandra.db;

public class CassandraWriteDAO extends CassandraDAO {

    public CassandraWriteDAO(ConnectionHandler conn) {
        super(conn);
    }
    
    public void logHttpAccess(int hour, String httpStatus, String ipAddress, String action, String url, long time) {
        StringBuilder cql = new StringBuilder();
        cql.append("INSERT INTO httpaccess.http_access (hour,ip_address,http_status,action,url,date_to_minute) ");
        cql.append("VALUES ");
        cql.append("(");
        cql.append(hour).append(",");
        cql.append("'").append(ipAddress).append("',");
        cql.append("'").append(httpStatus).append("',");
        cql.append("'").append(action).append("',");
        cql.append("'").append(url).append("',");
        cql.append("'").append(time).append("'");
        cql.append(")");
        conn.execute(cql.toString());
    }

    public void logHttpSuccess(String ipAddress) {
        StringBuilder cql = new StringBuilder();
        cql.append("UPDATE httpaccess.http_success ");
        cql.append("SET ");
        cql.append("requests=requests+1 ");
        cql.append("WHERE ");
        cql.append("ip_address='").append(ipAddress).append("'");
        conn.execute(cql.toString());
    }

    public void logHttpSuccessPerMinute(String date, String dateToMinute) {
        StringBuilder cql = new StringBuilder();
        cql.append("UPDATE httpaccess.http_success_per_minute ");
        cql.append("SET ");
        cql.append("requests=requests+1 ");
        cql.append("WHERE ");
        cql.append("date='").append(date).append("' and ");
        cql.append("date_to_minute='").append(dateToMinute).append("'");
        conn.execute(cql.toString());
    }

    public void logHttpFailed(String ipAddress) {
        StringBuilder cql = new StringBuilder();
        cql.append("UPDATE httpaccess.http_failed ");
        cql.append("SET ");
        cql.append("requests=requests+1 ");
        cql.append("WHERE ");
        cql.append("ip_address='").append(ipAddress).append("'");
        conn.execute(cql.toString());
    }

    public void logHttpFailedPerMinute(String date, String dateToMinute) {
        StringBuilder cql = new StringBuilder();
        cql.append("UPDATE httpaccess.http_failed_per_minute ");
        cql.append("SET ");
        cql.append("requests=requests+1 ");
        cql.append("WHERE ");
        cql.append("date='").append(date).append("' and ");
        cql.append("date_to_minute='").append(dateToMinute).append("'");
        conn.execute(cql.toString());
    }
}
