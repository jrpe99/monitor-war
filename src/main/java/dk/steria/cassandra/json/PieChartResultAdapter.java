package dk.steria.cassandra.json;

import com.datastax.driver.core.Row;
import java.util.List;

/**
 * Generates JSON for Chart.js types Pie & Doughnut and Polar.
 * JSON example:
    {	
        "chart": "pie",
	"failed_requests": [ 
            {
                    "value": 71, 
                    "color":"#FA000", 
                    "highlight": "#FF5A5E", 
                    "label": "223.45.21.56"    
            },    
            {        
                    "value": 52,        
                    "color":"#FA1700",        
                    "highlight": "#FF5A5E",        
                    "label": "10.45.234.56"    
            },    
            {        
                    "value": 50,
                    "color":"#FA2e00",        
                    "highlight": "#FF5A5E",        
                    "label": "142.65.234.56"    
            }
            ]
    }
 * 
 * @author Jörgen Persson
 */
public class PieChartResultAdapter {
    public static String httpSuccessToJSON(List<Row> rowList) {
        return toJSON(rowList, "successful_requests", "00", "FA");
    }
    
    public static String httpFailureToJSON(List<Row> rowList) {
        return toJSON(rowList, "failed_requests", "FA", "00");
    }
    
    private static String toJSON(List<Row> rowList, String httpCountField,String colorSeed1, String colorSeed2) {
        StringBuilder json = new StringBuilder();
        
        json.append("{");
        json.append("\"chart\": \"pie\",");
        json.append("\"").append(httpCountField).append("\": [");

        if(!rowList.isEmpty()) {
            int index = 0;
            int size = rowList.size();
            int colorIndex = 254 / size;
            int color = 0;

            while(index < size) {
                String hexColor = colorSeed1 + Integer.toHexString(color) + colorSeed2;
                Row row = rowList.get(index);

                Long httpCount = row.getLong(httpCountField);
                String ipAddress = row.getString("ip_address");

                createJSONObject(json, ipAddress, httpCount, hexColor);
                
                index++; 
                color=color+colorIndex;
                if(index < size)json.append(",");
            }
        }
        
        json.append("]}");
        
        return json.toString();
    }

    private static void createJSONObject(StringBuilder json, String ipAddress, Long requestCount, String hexColor) {
        json.append("    {");
        json.append("        \"value\": ").append(requestCount).append(",");
        json.append("        \"color\":\"#").append(hexColor).append("\",");
        json.append("        \"highlight\": \"#FF5A5E\",");
        json.append("        \"label\": \"").append(ipAddress).append("\"");
        json.append("    }");
    }
}
