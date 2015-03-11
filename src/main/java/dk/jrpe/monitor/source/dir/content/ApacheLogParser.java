package dk.jrpe.monitor.source.dir.content;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ApacheLogParser {

    /**
     * Structures Apache combined access log
     *
     * @return regex
     */
    static String getAccessLogRegex() {
        String regex1 = "^([\\d.]+)"; // Client IP
        String regex2 = " (\\S+)"; // -
        String regex3 = " (\\S+)"; // -
        String regex4 = " \\[([\\w:/]+\\s[+\\-]\\d{4})\\]"; // Date
        String regex5 = " \"(.+?)\""; // request method and url
        String regex6 = " (\\d{3})"; // HTTP code
        String regex7 = " (\\d+|(.+?))"; // Number of bytes
        String regex8 = " \"([^\"]+|(.+?))\""; // Referer
        String regex9 = " \"([^\"]+|(.+?))\""; // Agent

        return regex1 + regex2 + regex3;// + regex4 + regex5 + regex6 + regex7 + regex8 + regex9;
    }

    public static void parse(String line) throws ParseException {

        SimpleDateFormat accesslogDateFormat = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss Z");

//        Pattern accessLogPattern = Pattern.compile(getAccessLogRegex(), Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        Pattern accessLogPattern = Pattern.compile(getAccessLogRegex());
        Matcher accessLogEntryMatcher = accessLogPattern.matcher(line);

        if (!accessLogEntryMatcher.matches()) {
            System.out.println("Line couldn't be parsed");
        } else {
            System.out.println("Line : "
                    + accessLogEntryMatcher.group(1) + " "
                    + accessLogEntryMatcher.group(4) + " "
                    + accessLogEntryMatcher.group(5));
        }
    }
}
