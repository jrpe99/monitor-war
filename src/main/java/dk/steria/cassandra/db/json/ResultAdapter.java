package dk.steria.cassandra.db.json;

import com.datastax.driver.core.Row;
import java.util.List;

/**
 *
 * @author Jörgen Persson
 */
public class ResultAdapter {
    public static String httpSuccessToJSON(List<Row> rowList) {
        return toJSON(rowList, "successful_requests", "00", "FA");
    }
    
    public static String httpFailureToJSON(List<Row> rowList) {
        return toJSON(rowList, "failed_requests", "FA", "00");
    }
    
    private static String toJSON(List<Row> rowList, String httpCountField,String colorSeed1, String colorSeed2) {
        StringBuilder json = new StringBuilder();
        
        json.append("{\""+httpCountField+"\": [");

        if(!rowList.isEmpty()) {
            sort(rowList, httpCountField);

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

    public static void sort(List<Row> rowList, String onField) {
        rowList.sort((row1, row2)-> {
            Long field1 = row1.getLong(onField);
            Long field2 = row2.getLong(onField);
            return field2.compareTo(field1);
        });
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
