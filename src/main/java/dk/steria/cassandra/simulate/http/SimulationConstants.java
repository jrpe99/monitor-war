package dk.steria.cassandra.simulate.http;

import java.util.ArrayList;

public class SimulationConstants {

    static final ArrayList<String> httpStatusList;
    static {
        httpStatusList = new ArrayList<String>();        
        httpStatusList.add("200");
        httpStatusList.add("200");
        httpStatusList.add("200");
        httpStatusList.add("200");
        httpStatusList.add("200");
        httpStatusList.add("200");
        httpStatusList.add("200");
        httpStatusList.add("200");
        httpStatusList.add("200");
        httpStatusList.add("200");
        httpStatusList.add("200");
        httpStatusList.add("200");
        httpStatusList.add("200");
        httpStatusList.add("200");
        httpStatusList.add("200");
        httpStatusList.add("200");
        httpStatusList.add("200");
        httpStatusList.add("200");
        httpStatusList.add("200");
        httpStatusList.add("404");
        httpStatusList.add("404");
        httpStatusList.add("403");
        httpStatusList.add("403");
        httpStatusList.add("302");
    }
    
    static final ArrayList<String> ipAddressList;
    static {
        ipAddressList = new ArrayList<String>();
        ipAddressList.add("10.45.234.56");
        ipAddressList.add("10.45.234.56");
        ipAddressList.add("10.45.234.56");
        ipAddressList.add("10.45.234.56");
        ipAddressList.add("10.45.234.56");
        ipAddressList.add("10.45.234.56");
        ipAddressList.add("10.45.234.56");
        ipAddressList.add("10.45.234.56");

        ipAddressList.add("120.45.24.5");
        ipAddressList.add("120.45.24.5");
        ipAddressList.add("120.45.24.5");
        ipAddressList.add("120.45.24.5");

        ipAddressList.add("142.65.234.56");
        ipAddressList.add("142.65.234.56");
        ipAddressList.add("142.65.234.56");
        ipAddressList.add("142.65.234.56");

        ipAddressList.add("140.65.234.56");
        ipAddressList.add("140.65.234.56");

        ipAddressList.add("106.45.23.5");
        ipAddressList.add("106.45.23.5");
        ipAddressList.add("106.45.23.5");
        ipAddressList.add("106.45.23.5");

        ipAddressList.add("223.45.21.56");
        ipAddressList.add("223.45.21.56");
        ipAddressList.add("223.45.21.56");
        ipAddressList.add("223.45.21.56");
        ipAddressList.add("223.45.21.56");
        ipAddressList.add("223.45.21.56");
        ipAddressList.add("223.45.21.56");

        ipAddressList.add("220.45.21.56");
        ipAddressList.add("220.45.21.56");
        ipAddressList.add("220.45.21.56");
        ipAddressList.add("220.45.21.56");
        
        ipAddressList.add("123.43.234.56");
        ipAddressList.add("123.43.234.56");
        ipAddressList.add("123.43.234.56");
        ipAddressList.add("123.43.234.56");
        ipAddressList.add("123.43.234.56");
        
        ipAddressList.add("90.15.14.12");
        ipAddressList.add("90.15.14.12");
        ipAddressList.add("90.15.14.12");
        
        ipAddressList.add("111.75.24.22");
        ipAddressList.add("111.75.24.22");
        ipAddressList.add("111.75.24.22");
        ipAddressList.add("111.75.24.22");
        
        ipAddressList.add("211.45.234.156");
        ipAddressList.add("211.45.234.156");
        ipAddressList.add("211.45.234.156");
        ipAddressList.add("211.45.234.156");
        ipAddressList.add("211.45.234.156");
        ipAddressList.add("211.45.234.156");
        ipAddressList.add("211.45.234.156");
    }
    
    static final ArrayList<String> actionList;
    static {
        actionList = new ArrayList<String>();
        actionList.add("POST");
        actionList.add("GET");
    }
    
    static final ArrayList<String> urlList;
    static {
        urlList = new ArrayList<String>();
        urlList.add("/test");
        urlList.add("/test?p=34");
        urlList.add("/test/all?f=456");
        urlList.add("/test/cart?id=4234");
        urlList.add("/test/cart?id=122");
        urlList.add("/test/cart?id=122");
        urlList.add("/test/cart?id=22");
        urlList.add("/test/cart?id=13");
        urlList.add("/test/cart?id=23");
        urlList.add("/test/cart?id=56");
        urlList.add("/test/cart?id=123");
    }
}
