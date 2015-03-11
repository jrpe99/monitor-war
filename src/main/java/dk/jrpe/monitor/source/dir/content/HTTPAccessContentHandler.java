package dk.jrpe.monitor.source.dir.content;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;

/**
 *
 * @author Jörgen Persson
 */
public class HTTPAccessContentHandler implements ContentHandler {

    @Override
    public void parse(Path dir, Path file) throws IOException, ParseException {
        Path absolutePath = Paths.get(dir.toString(), dir.getFileSystem().getSeparator(), file.toString());
        BufferedReader reader = Files.newBufferedReader(absolutePath, StandardCharsets.UTF_8);
        String line;
        while((line = reader.readLine()) != null) {
            System.out.println(line);
            ApacheLogParser.parse(line);
        }
    }
}
