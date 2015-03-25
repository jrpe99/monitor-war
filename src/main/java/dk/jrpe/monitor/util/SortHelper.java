package dk.jrpe.monitor.util;

import dk.jrpe.monitor.db.httpaccess.to.HTTPAccessTO;
import java.util.List;

/**
 * @author Jörgen Persson
 */
public class SortHelper {
    public static List<HTTPAccessTO> onRequestCount(List<HTTPAccessTO> list) {
        list.sort((to1, to2)-> {
            Long field1 = to1.getRequests();
            Long field2 = to2.getRequests();
            return field2.compareTo(field1);
        });
        return list;
    }
    
    public static List<HTTPAccessTO> onDateToMinute(List<HTTPAccessTO> list) {
        list.sort((to1, to2)-> {
            String field1 = to1.getDateToMinute();
            String field2 = to2.getDateToMinute();
            return field2.compareTo(field1);
        });
        return list;
    }
    
    public static double round(double num, int multipleOf) {
        return Math.floor((num +  (double)multipleOf / 2) / multipleOf) * multipleOf;
    }    
    
    public static void main(String[] args) {
        System.out.println("3 round to 5: " + SortHelper.round(3, 5));
        System.out.println("9 round to 10: " + SortHelper.round(9, 10));
        System.out.println("11 round to 15: "+ SortHelper.round(11, 15));
        System.out.println("5 round to 20: " + SortHelper.round(5, 20));
        System.out.println("6.2 round to 10: " + SortHelper.round(6.2, 10));        
    }
}
