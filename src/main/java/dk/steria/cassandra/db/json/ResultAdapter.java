package dk.steria.cassandra.db.json;

import com.datastax.driver.core.Row;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Jörgen Persson
 */
public class ResultAdapter {
    public static String toJSON(List<Row> allRows) {
        StringBuilder json = new StringBuilder();
        
        json.append("{\"graph\": [");

        if(!allRows.isEmpty()) {
            allRows.sort((row1, row2)-> {
                Long successfulRequests1 = row1.getLong("successful_requests");
                Long successfulRequests2 = row2.getLong("successful_requests");
                return successfulRequests1.compareTo(successfulRequests2);
            });


            int index = 0;
            int size = allRows.size();
            int colorIndex = 254 / size;
            int color = 0;

            while(index < size) {
                createObject(allRows.get(index), json, color);
                index++; 
                color=color+colorIndex;
                if(index < size)json.append(",");
            }
        }
        
        json.append("]}");
        
        return json.toString();
    }

    private static void createObject(Row row, StringBuilder json, int color) {
        Long successfulRequests = row.getLong("successful_requests");
        String ipAddress = row.getString("ip_address");
        String hexColor = Integer.toHexString(color);
        
        json.append("    {");
        json.append("        \"value\": ").append(successfulRequests).append(",");
        json.append("        \"color\":\"#00").append(hexColor).append("FA\",");
        json.append("        \"highlight\": \"#FF5A5E\",");
        json.append("        \"label\": \"").append(ipAddress).append("\"");
        json.append("    }");
    }
    
    public String toJSONTestData() {
        StringBuilder msg = new StringBuilder();
        msg.append("{\"graph\": [");
        msg.append("    {");
        msg.append("        \"value\": 300,");
        msg.append("        \"color\":\"#F7464A\",");
        msg.append("        \"highlight\": \"#FF5A5E\",");
        msg.append("        \"label\": \"Red\"");
        msg.append("    },");
        msg.append("    {");
        msg.append("        \"value\": 50,");
        msg.append("        \"color\":\"#46BFBD\",");
        msg.append("        \"highlight\": \"#5AD3D1\",");
        msg.append("        \"label\": \"Green\"");
        msg.append("    },");
        msg.append("    {");
        msg.append("        \"value\": 100,");
        msg.append("        \"color\":\"#949FB1\",");
        msg.append("        \"highlight\": \"#A8B3C5\",");
        msg.append("        \"label\": \"Grey\"");
        msg.append("    }");
        msg.append("]}");
        return msg.toString();
    }
}
