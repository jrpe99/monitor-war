/*
 */
package dk.steria.cassandra.output.json;

import com.datastax.driver.core.Row;
import java.util.List;
import java.util.function.Function;

/**
 *
 * @author Jörgen Persson
 */
public enum ChartEnum {
    PIE_SUCCESS((list -> {return PieChartResult.httpSuccessToJSON(list);})), 
    PIE_FAILED((list -> {return PieChartResult.httpFailureToJSON(list);})), 
    RADAR_SUCCESS((list -> {return RadarChartResult.httpSuccessToJSON(list);})),
    RADAR_FAILED((list -> {return RadarChartResult.httpFailureToJSON(list);}));
    
    private final Function<List<Row>, String> chartFunction;
    
    ChartEnum(final Function<List<Row>, String> chartFunction) {
        this.chartFunction = chartFunction;
    }
    
    public String toJSON(List<Row> list) {
        return chartFunction.apply(list);
    }
}
