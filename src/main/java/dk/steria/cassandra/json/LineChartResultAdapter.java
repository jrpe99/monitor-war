package dk.steria.cassandra.json;

import com.datastax.driver.core.Row;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

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
public class LineChartResultAdapter {
    private static final int INTERVAL = 5;
    
    public String toJSON(List<Row> rowList) {
        Calendar cal = Calendar.getInstance();
        List<DataItem> dataItemList = new ArrayList<>();
        rowList.stream().forEach((row) -> {
            dataItemList.add(new DataItem(row));
        });
        
        List<DataItem> successHttpAccessList = dataItemList.stream()
                .filter(item -> item.getHttpStatus() == 200)
                .collect(Collectors.toList());
        
        List<DataItem> failedHttpAccessList = dataItemList.stream()
                .filter(item -> item.getHttpStatus() != 200)
                .collect(Collectors.toList());
        
        Map<Integer,List> successTimeSliceCollection = new HashMap<>();
        Map<Integer,List> failedTimeSliceCollection = new HashMap<>();

        successHttpAccessList.stream().forEach((item) -> {
            putInTimeSlice(item, successTimeSliceCollection);
        });
        
        failedHttpAccessList.stream().forEach((item) -> {
            putInTimeSlice(item, failedTimeSliceCollection);
        });
        
        
        
        //createLists(rowList, labelList, dataList);
        //toJSON(rowList, labelList, dataList);
        return "";
    }

    private void putInTimeSlice(DataItem item, Map<Integer,List> timeSliceCollection) {
        int interval = 5;
        int minute = item.getMinute();
        for (int i = INTERVAL; i <= 60; i=i+INTERVAL) {
            if(minute<i && minute > i-INTERVAL) {
                List timeSlice = timeSliceCollection.get(i);
                if(timeSlice != null) {
                    timeSlice.add(item);
                } else {
                    List<DataItem> list = new ArrayList<>();
                    timeSliceCollection.put(i, list);
                }
            }
        }
    }
    
    private static String toJSON(String dataSetName) {
        StringBuilder json = new StringBuilder();
        StringBuilder labelList = new StringBuilder(); 
        labelList.append("[");
        labelList.append("\"5 min\",");
        labelList.append("\"10 min\",");
        labelList.append("\"15 min\",");
        labelList.append("\"20 min\",");
        labelList.append("\"25 min\",");
        labelList.append("\"30 min\",");
        labelList.append("\"35 min\",");
        labelList.append("\"40 min\",");
        labelList.append("\"45 min\",");
        labelList.append("\"50 min\",");
        labelList.append("\"55 min\",");
        labelList.append("]");
                
        StringBuilder dataList = new StringBuilder();

        json.append("{");
        json.append("\"chart\": \"line\",");
        json.append("\"dataset\": \"").append(dataSetName).append("\",");
        json.append("\"data\": {");
        json.append("\"labels\":").append(labelList).append(",");
        json.append("\"datasets\": [");

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

        json.append("]");

        json.append("   }");
        json.append("}");
        
        return json.toString();
    }

    private static void createLists(List<Row> rowList, String httpCountField, StringBuilder labelList, StringBuilder dataList) {
        int index = 0;
        int size = rowList.size();
        labelList.append("[");
        dataList.append("[");
        while(index < size) {
            Row row = rowList.get(index);

            Long httpCount = row.getLong(httpCountField);
            String ipAddress = row.getString("ip_address");

            labelList.append("\"").append(ipAddress).append("\"");
            dataList.append(httpCount);

            index++; 
            if(index < size){
                labelList.append(",");
                dataList.append(",");
            }
        }

        labelList.append("]");
        dataList.append("]");
    }
    
    class DataItem {
        private Integer hour;
        private String ipAddress;
        private Integer httpStatus;
        private String action;
        private String url;
        private Long timestamp;
        private Integer minute;
        
        DataItem(Row row) {
            this.hour = row.getInt("hour");
            this.ipAddress = row.getString("ip_address");
            this.httpStatus = new Integer(row.getString("http_status"));
            this.action = row.getString("action");
            this.url = row.getString("url");
            this.timestamp = row.getLong("date_to_minute");
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(this.timestamp);
            this.minute = cal.get(Calendar.MINUTE);
        }

        public Integer getMinute() {
            return minute;
        }
        
        public Integer getHour() {
            return hour;
        }

        public String getIpAddress() {
            return ipAddress;
        }

        public Integer getHttpStatus() {
            return httpStatus;
        }

        public String getAction() {
            return action;
        }

        public String getUrl() {
            return url;
        }

        public Long getTimestamp() {
            return timestamp;
        }
        
        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final DataItem other = (DataItem) obj;
            if (!Objects.equals(this.minute, other.minute)) {
                return false;
            }
            return true;
        }
    }
    
    class DataSet {
        private String dataSetName;
        private String label;
        private List<Integer> dataList;

        public String getDataSetName() {
            return dataSetName;
        }

        public void setDataSetName(String dataSetName) {
            this.dataSetName = dataSetName;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public List<Integer> getDataList() {
            return dataList;
        }

        public void setDataList(List<Integer> dataList) {
            this.dataList = dataList;
        }
    }
}
