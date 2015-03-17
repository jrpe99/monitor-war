package dk.jrpe.monitor.db.datasource.memory;

/**
 *
 * @author Jörgen Persson
 */
public class InMemoryDataBase {
    private static class InstanceHolder {
        static final InMemoryDataBase instance = new InMemoryDataBase();
    }
    
    public InMemoryDataBase getInstance() {
        return InstanceHolder.instance;
    }
}
