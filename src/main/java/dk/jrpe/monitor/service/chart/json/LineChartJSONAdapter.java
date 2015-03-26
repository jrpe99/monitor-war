package dk.jrpe.monitor.service.chart.json;

import dk.jrpe.monitor.db.httpaccess.to.HTTPAccessTO;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Generates JSON for Chart.js type Line.
 * JSON example:
    {
        "chart": "line",
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
public class LineChartJSONAdapter {
    public static String toJSON(HashMap<String,List<HTTPAccessTO>> dataSetMap, int period) {
        StringBuilder json = new StringBuilder();
        json.append("{");
        json.append("\"chart\": \"line\",");
        json.append("\"dataset\": \"").append("requests_per_minute").append("\",");
        json.append("\"data\": {");
        json.append("\"labels\":").append(createLabelList(period)).append(",");
        json.append("\"datasets\": [");

        int index = 0;
        int size = dataSetMap.size();
        for (Map.Entry<String, List<HTTPAccessTO>> entrySet : dataSetMap.entrySet()) {
            String key = entrySet.getKey();
            List<HTTPAccessTO> value = entrySet.getValue();
            
            dataSetTOJSON(json, key, createDataList(value));
                    
            index++; 
            if(index < size){
                json.append(",");
            }
        }
        json.append("]");
        json.append("   }");
        json.append("}");

        return json.toString();
    }

    private static String dataSetTOJSON(StringBuilder json, String dataSetName, String dataList) {
        json.append("{");
        json.append("   \"label\": \"").append(dataSetName).append("\",");
        json.append("   \"fillColor\": \"rgba(50,190,0,0.2)\",");
        json.append("   \"strokeColor\": \"rgba(220,220,220,1)\",");
        json.append("   \"pointColor\": \"rgba(220,220,220,1)\",");
        json.append("   \"pointStrokeColor\": \"#fff\",");
        json.append("   \"pointHighlightFill\": \"#fff\",");
        json.append("   \"pointHighlightStroke\": \"rgba(220,220,220,1)\",");
        json.append("   \"data\":").append(dataList);
        json.append("}");
        return json.toString();
    }

    private static String createDataList(List<HTTPAccessTO> list) {
        StringBuilder dataList = new StringBuilder();
        int index = 0;
        int size = list.size();
        dataList.append("[");
        while(index < size) {
            HTTPAccessTO to = list.get(index);
            Long requests = to.getRequests();
            dataList.append(requests);
            index++; 
            if(index < size){
                dataList.append(",");
            }
        }
        dataList.append("]");
        return dataList.toString();
    }

    private static String createLabelList(int period) {
        StringBuilder labelList = new StringBuilder();
        int index = 0;
        int size = period;
        labelList.append("[");
        while(index < size) {
            labelList.append("\"").append(index).append("\"");
            index++; 
            if(index < size){
                labelList.append(",");
            }
        }
        labelList.append("]");
        return labelList.toString();
    }
}
