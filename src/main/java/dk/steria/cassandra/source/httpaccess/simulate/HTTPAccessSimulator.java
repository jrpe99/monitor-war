package dk.steria.cassandra.source.httpaccess.simulate;

import java.util.Random;

import dk.steria.cassandra.db.ConnectionHandler;
import dk.steria.cassandra.db.HTTPAccessDAO;
import dk.steria.cassandra.source.httpaccess.to.HTTPAccessTOFactory;

public class HTTPAccessSimulator {
    public static void main(String[] args) {
        new HTTPAccessSimulator().simulate();
    }

    public void simulate() {
        try (ConnectionHandler conn = new ConnectionHandler()) {
            Random random = new Random();
            conn.connect();
            HTTPAccessDAO httpAccessDAO = new HTTPAccessDAO(conn);
            while(true) {
                httpAccessDAO.create(HTTPAccessTOFactory.createSimulated());
                
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