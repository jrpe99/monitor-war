package dk.jrpe.monitor.output;

import com.datastax.driver.core.Row;
import dk.jrpe.monitor.output.json.PieChartResult;
import dk.jrpe.monitor.output.json.RadarChartResult;
import java.util.List;
import java.util.function.Function;

/**
 * Each ENUM holds a Function representing the strategy to convert 
 * a list of rows from the Cassandra database to JSON.
 * 
 * @author Jörgen Persson
 */
public enum ChartEnum {
    PIE_SUCCESS(1, (list -> {return PieChartResult.httpSuccessToJSON(list);})),
    PIE_FAILED(2, (list -> {return PieChartResult.httpFailureToJSON(list);})),
    RADAR_SUCCESS(3, (list -> {return RadarChartResult.httpSuccessToJSON(list);})),
    RADAR_FAILED(4, (list -> {return RadarChartResult.httpFailureToJSON(list);})),
    LINE_SUCCESS_AND_FAILED(5, (list -> {
        // Does not have the same interface yet ...
        throw new UnsupportedOperationException();
    }));
    
    private final int type;
    private final Function<List<Row>, String> chartFunction;
    
    ChartEnum(int type, Function<List<Row>, String> chartFunction) {
        this.type = type;
        this.chartFunction = chartFunction;
    }
   
    /**
     * Convert the list of rows to JSON
     * @param list
     * @return
     */
    public String toJSON(List<Row> list) {
        return chartFunction.apply(list);
    }
    
    public int getType() {
        return this.type;
    }
}
