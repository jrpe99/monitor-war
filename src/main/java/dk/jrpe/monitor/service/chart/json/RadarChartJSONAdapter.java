package dk.jrpe.monitor.service.chart.json;

import dk.jrpe.monitor.db.httpaccess.to.HTTPAccessTO;
import java.util.List;

/**
 * Generates JSON for Chart.js type Radar.
 * JSON example:
    {
        "chart": "radar",
        "data": {
            labels: ["223.45.21.56", "10.45.234.56", "142.65.234.56"],
            datasets: [
                {
                    label: "successful_requests",
                    fillColor: "rgba(220,220,220,0.2)",
                    strokeColor: "rgba(220,220,220,1)",
                    pointColor: "rgba(220,220,220,1)",
                    pointStrokeColor: "#fff",
                    pointHighlightFill: "#fff",
                    pointHighlightStroke: "rgba(220,220,220,1)",
                    data: [65, 59, 90]
                }
            ]
        }
    } 
 * 
 * @author Jörgen Persson
 */
public class RadarChartJSONAdapter {
    public static String httpSuccessToJSON(List<HTTPAccessTO> rowList) {
        return toJSON(rowList, "successful_requests");
    }
    
    public static String httpFailureToJSON(List<HTTPAccessTO> rowList) {
        return toJSON(rowList, "failed_requests");
    }
    
    private static String toJSON(List<HTTPAccessTO> rowList, String dataSet) {
        StringBuilder json = new StringBuilder();

        json.append("{");
        json.append("\"chart\": \"radar\",");
        json.append("\"dataset\": \"").append(dataSet).append("\",");
        json.append("\"data\": {");
        if(!rowList.isEmpty()) {
            StringBuilder labelList = new StringBuilder(); 
            StringBuilder dataList = new StringBuilder();
            createLists(rowList, labelList, dataList);
            
            json.append("\"labels\":").append(labelList).append(",");
            json.append("\"datasets\": [");
            json.append("{");
            json.append("   \"label\": \"").append(dataSet).append("\",");
            json.append("   \"fillColor\": \"rgba(50,190,0,0.2)\",");
            json.append("   \"strokeColor\": \"rgba(220,220,220,1)\",");
            json.append("   \"pointColor\": \"rgba(220,220,220,1)\",");
            json.append("   \"pointStrokeColor\": \"#fff\",");
            json.append("   \"pointHighlightFill\": \"#fff\",");
            json.append("   \"pointHighlightStroke\": \"rgba(220,220,220,1)\",");
            json.append("   \"data\":").append(dataList);
            json.append("}");
            json.append("]");
        }

        json.append("   }");
        json.append("}");
        
        return json.toString();
    }

    private static void createLists(List<HTTPAccessTO> rowList, StringBuilder labelList, StringBuilder dataList) {
        int index = 0;
        int size = rowList.size();
        labelList.append("[");
        dataList.append("[");
        while(index < size) {
            HTTPAccessTO row = rowList.get(index);

            String ipAddress = row.getIpAddress();
            Long httpRequestCount = row.getRequests();

            labelList.append("\"").append(ipAddress).append("\"");
            dataList.append(httpRequestCount);

            index++; 
            if(index < size){
                labelList.append(",");
                dataList.append(",");
            }
        }

        labelList.append("]");
        dataList.append("]");
    }
}
