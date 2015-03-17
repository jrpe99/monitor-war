package dk.jrpe.monitor.db.cassandra;

import dk.jrpe.monitor.db.to.HTTPAccessTO;


public class CassandraWriteDAO extends CassandraDAO {

    public CassandraWriteDAO(CassandraConnectionHandler conn) {
        super(conn);
    }
    
    public void saveHttpAccess(HTTPAccessTO to, int hour, long time) {
        StringBuilder cql = new StringBuilder();
        cql.append("INSERT INTO httpaccess.http_access (hour,ip_address,http_status,action,url,date_to_minute) ");
        cql.append("VALUES ");
        cql.append("(");
        cql.append(hour).append(",");
        cql.append("'").append(to.getIpAddress()).append("',");
        cql.append("'").append(to.getHttpStatus()).append("',");
        cql.append("'").append(to.getAction()).append("',");
        cql.append("'").append(to.getUrl()).append("',");
        cql.append("'").append(time).append("'");
        cql.append(")");
        conn.execute(cql.toString());
    }

    public void updateHttpSuccess(HTTPAccessTO to) {
        StringBuilder cql = new StringBuilder();
        cql.append("UPDATE httpaccess.http_success ");
        cql.append("SET ");
        cql.append("requests=requests+1 ");
        cql.append("WHERE ");
        cql.append("ip_address='").append(to.getIpAddress()).append("'");
        conn.execute(cql.toString());
    }

    public void updateHttpSuccessPerMinute(HTTPAccessTO to) {
        StringBuilder cql = new StringBuilder();
        cql.append("UPDATE httpaccess.http_success_per_minute ");
        cql.append("SET ");
        cql.append("requests=requests+1 ");
        cql.append("WHERE ");
        cql.append("date='").append(to.getDate()).append("' and ");
        cql.append("date_to_minute='").append(to.getDateToMinute()).append("'");
        conn.execute(cql.toString());
    }

    public void updateHttpFailed(HTTPAccessTO to) {
        StringBuilder cql = new StringBuilder();
        cql.append("UPDATE httpaccess.http_failed ");
        cql.append("SET ");
        cql.append("requests=requests+1 ");
        cql.append("WHERE ");
        cql.append("ip_address='").append(to.getIpAddress()).append("'");
        conn.execute(cql.toString());
    }

    public void updateHttpFailedPerMinute(String date, String dateToMinute) {
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
