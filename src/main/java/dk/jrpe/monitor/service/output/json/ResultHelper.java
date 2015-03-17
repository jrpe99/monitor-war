package dk.jrpe.monitor.service.output.json;

import dk.jrpe.monitor.db.to.HTTPAccessTO;
import java.util.List;

/**
 * @author Jörgen Persson
 */
public class ResultHelper {
    public static List<HTTPAccessTO> sortHttpAccess(List<HTTPAccessTO> rowList) {
        rowList.sort((row1, row2)-> {
            Long field1 = row1.getRequests();
            Long field2 = row2.getRequests();
            return field2.compareTo(field1);
        });
        return rowList;
    }
    
    public static double round(double num, int multipleOf) {
        return Math.floor((num +  (double)multipleOf / 2) / multipleOf) * multipleOf;
    }    
    
    public static void main(String[] args) {
        System.out.println("3 round to 5: " + ResultHelper.round(3, 5));
        System.out.println("9 round to 10: " + ResultHelper.round(9, 10));
        System.out.println("11 round to 15: "+ ResultHelper.round(11, 15));
        System.out.println("5 round to 20: " + ResultHelper.round(5, 20));
        System.out.println("6.2 round to 10: " + ResultHelper.round(6.2, 10));        
    }
}
