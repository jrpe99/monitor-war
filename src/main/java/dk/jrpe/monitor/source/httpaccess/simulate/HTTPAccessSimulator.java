package dk.jrpe.monitor.source.httpaccess.simulate;

import java.util.Random;

import dk.jrpe.monitor.db.dao.httpaccess.HTTPAccessDAO;
import dk.jrpe.monitor.db.datasource.DataSource;
import dk.jrpe.monitor.db.datasource.DataSourceFactory;
import dk.jrpe.monitor.db.dao.httpaccess.to.HTTPAccessTO;
import dk.jrpe.monitor.source.httpaccess.to.HTTPAccessTOFactory;
import dk.jrpe.monitor.websocket.client.WebsocketClientEndpoint;
import dk.jrpe.monitor.websocket.client.to.HTTPAccessDataWrapper;
import java.net.URI;

public class HTTPAccessSimulator {
    private final DataSource dataSource = DataSourceFactory.getDefault();
    public static void main(String[] args) {
        new HTTPAccessSimulator().simulateWithMonitorServer();
    }

    public void simulateWithCassandra() {
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
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
    
    public void simulateWithMonitorServer() {
        try {
            WebsocketClientEndpoint client = new WebsocketClientEndpoint(new URI("ws://localhost:8080/Cassandra/monitordata"));
            client.send("Send");
            //while (true) {
                HTTPAccessTO to = HTTPAccessTOFactory.createSimulated();
                if (to.getHttpStatus().equals("200")) {
                    client.send(new HTTPAccessDataWrapper(to, HTTPAccessDataWrapper.Type.HTTP_SUCCESS));
                    client.send(new HTTPAccessDataWrapper(to, HTTPAccessDataWrapper.Type.HTTP_SUCCESS_PER_MINUTE));
                } else {
                    client.send(new HTTPAccessDataWrapper(to, HTTPAccessDataWrapper.Type.HTTP_FAILED));
                    client.send(new HTTPAccessDataWrapper(to, HTTPAccessDataWrapper.Type.HTTP_FAILED_PER_MINUTE));
                }
            //}
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}