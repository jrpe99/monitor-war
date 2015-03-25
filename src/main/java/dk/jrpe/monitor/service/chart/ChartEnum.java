package dk.jrpe.monitor.service.chart;

import dk.jrpe.monitor.db.httpaccess.to.HTTPAccessTO;
import dk.jrpe.monitor.service.chart.json.PieChartJSONAdapter;
import dk.jrpe.monitor.service.chart.json.RadarChartJSONAdapter;
import java.util.List;
import java.util.function.Function;

/**
 * Each ENUM holds a Function representing the strategy to convert 
 * a list of rows from the Cassandra database to JSON.
 * 
 * @author Jörgen Persson
 */
public enum ChartEnum {
    PIE_SUCCESS(1, (list -> {return PieChartJSONAdapter.httpSuccessToJSON(list);})),
    PIE_FAILED(2, (list -> {return PieChartJSONAdapter.httpFailureToJSON(list);})),
    RADAR_SUCCESS(3, (list -> {return RadarChartJSONAdapter.httpSuccessToJSON(list);})),
    RADAR_FAILED(4, (list -> {return RadarChartJSONAdapter.httpFailureToJSON(list);})),
    LINE_SUCCESS_AND_FAILED(5, (list -> {
        // Does not have the same interface yet ...
        throw new UnsupportedOperationException();
    }));
    
    private final int type;
    private final Function<List<HTTPAccessTO>, String> chartFunction;
    private String json;
    
    ChartEnum(int type, Function<List<HTTPAccessTO>, String> chartFunction) {
        this.type = type;
        this.chartFunction = chartFunction;
    }
   
    /**
     * Convert the list of rows to JSON
     * @param list
     */
    public void toJSON(List<HTTPAccessTO> list) {
        this.json = chartFunction.apply(list);
    }
    
    public int getType() {
        return this.type;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }
}
