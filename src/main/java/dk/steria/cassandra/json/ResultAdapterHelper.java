package dk.steria.cassandra.json;

import com.datastax.driver.core.Row;
import java.util.List;

/**
 *
 * @author jorperss
 */
public class ResultAdapterHelper {
    public static void sortOnLongField(List<Row> rowList, String onField) {
        rowList.sort((row1, row2)-> {
            Long field1 = row1.getLong(onField);
            Long field2 = row2.getLong(onField);
            return field2.compareTo(field1);
        });
    }
}
