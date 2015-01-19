package dk.steria.cassandra.adapter.json;

import com.datastax.driver.core.Row;
import java.util.List;

/**
 * @author Jörgen Persson
 */
public class ResultAdapterHelper {
    public static void sortOnLongField(List<Row> rowList, String onField) {
        rowList.sort((row1, row2)-> {
            Long field1 = row1.getLong(onField);
            Long field2 = row2.getLong(onField);
            return field2.compareTo(field1);
        });
    }
    
    public static double round(double num, int multipleOf) {
        return Math.floor((num +  (double)multipleOf / 2) / multipleOf) * multipleOf;
    }    
    
    public static void main(String[] args) {
        System.out.println("3 round to 5: " + ResultAdapterHelper.round(3, 5));
        System.out.println("9 round to 10: " + ResultAdapterHelper.round(9, 10));
        System.out.println("11 round to 15: "+ ResultAdapterHelper.round(11, 15));
        System.out.println("5 round to 20: " + ResultAdapterHelper.round(5, 20));
        System.out.println("6.2 round to 10: " + ResultAdapterHelper.round(6.2, 10));        
    }
}
