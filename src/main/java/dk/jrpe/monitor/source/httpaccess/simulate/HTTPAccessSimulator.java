package dk.jrpe.monitor.source.httpaccess.simulate;

import java.util.Random;

import dk.jrpe.monitor.db.datasource.cassandra.HTTPAccessDAO;
import dk.jrpe.monitor.db.datasource.DataSource;
import dk.jrpe.monitor.db.datasource.DataSourceFactory;
import dk.jrpe.monitor.source.httpaccess.to.HTTPAccessTOFactory;

public class HTTPAccessSimulator {
    private final DataSource dataSource = DataSourceFactory.getDataSource();
    public static void main(String[] args) {
        new HTTPAccessSimulator().simulate();
    }

    public void simulate() {
        this.dataSource.open();
        try (DataSource localDataSource = this.dataSource;) {
            Random random = new Random();
            HTTPAccessDAO httpAccessDAO = new HTTPAccessDAO(localDataSource);
            while(true) {
                httpAccessDAO.saveAndUpdate(HTTPAccessTOFactory.createSimulated());
                
                int sleepTime = random.nextInt(500);
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}