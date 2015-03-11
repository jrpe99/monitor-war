package dk.jrpe.monitor.source.dir.content;

import java.io.IOException;
import java.nio.file.Path;
import java.text.ParseException;

/**
 *
 * @author Jörgen Persson
 */
public interface ContentHandler {
    public void parse(Path dir, Path file) throws IOException, ParseException;
}
