package dk.jrpe.monitor.source.dir;

import dk.jrpe.monitor.source.dir.content.ContentHandler;
import dk.jrpe.monitor.source.dir.content.HTTPAccessContentHandler;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jörgen Persson
 */
public class FileSource {
    private ContentHandler contentHandler = null;
    public FileSource(ContentHandler c) {
        this.contentHandler = c;
    }
    
    public static void main(String[] args) {
        new FileSource(new HTTPAccessContentHandler()).start();
    }
    
    public void start() {
        try {
            Path dir = Paths.get("D:/tmp/httpaccess");
            WatchService watchService = FileSystems.getDefault().newWatchService();
            dir.register(watchService, ENTRY_MODIFY, ENTRY_CREATE);
            while(true) {
                WatchKey watchKey;
                try {
                    watchKey = watchService.take();
                } catch (InterruptedException x) {
                    return;
                }                
                for (WatchEvent<?> event : watchKey.pollEvents()) {
                    System.out.println("Event: " + event.kind());
                    if (event.kind() == ENTRY_CREATE || event.kind() == ENTRY_MODIFY) {
                        @SuppressWarnings("unchecked")
                        WatchEvent<Path> ev = (WatchEvent<Path>)event;
                        Path file = ev.context();
                        System.out.println("Found new file: " + file);
                        this.contentHandler.parse(dir, file);
                    }
                }
                watchKey.reset();
            }
        } catch (IOException | ParseException ex) {
            Logger.getLogger(FileSource.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
