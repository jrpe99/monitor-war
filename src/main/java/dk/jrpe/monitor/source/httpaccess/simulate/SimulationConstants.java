package dk.jrpe.monitor.source.httpaccess.simulate;

import java.util.ArrayList;

public class SimulationConstants {

    public static final ArrayList<String> HTTP_STATUS_LIST;
    static {
        HTTP_STATUS_LIST = new ArrayList<>();        
        HTTP_STATUS_LIST.add("200");
        HTTP_STATUS_LIST.add("200");
        HTTP_STATUS_LIST.add("200");
        HTTP_STATUS_LIST.add("200");
        HTTP_STATUS_LIST.add("200");
        HTTP_STATUS_LIST.add("200");
        HTTP_STATUS_LIST.add("200");
        HTTP_STATUS_LIST.add("200");
        HTTP_STATUS_LIST.add("200");
        HTTP_STATUS_LIST.add("200");
        HTTP_STATUS_LIST.add("200");
        HTTP_STATUS_LIST.add("200");
        HTTP_STATUS_LIST.add("200");
        HTTP_STATUS_LIST.add("200");
        HTTP_STATUS_LIST.add("200");
        HTTP_STATUS_LIST.add("200");
        HTTP_STATUS_LIST.add("200");
        HTTP_STATUS_LIST.add("200");
        HTTP_STATUS_LIST.add("200");
        HTTP_STATUS_LIST.add("404");
        HTTP_STATUS_LIST.add("404");
        HTTP_STATUS_LIST.add("403");
        HTTP_STATUS_LIST.add("403");
        HTTP_STATUS_LIST.add("302");
    }
    
    public static final ArrayList<String> IP_ADDRESS_LIST;
    static {
        IP_ADDRESS_LIST = new ArrayList<>();
        IP_ADDRESS_LIST.add("10.45.234.56");
        IP_ADDRESS_LIST.add("10.45.234.56");
        IP_ADDRESS_LIST.add("10.45.234.56");
        IP_ADDRESS_LIST.add("10.45.234.56");
        IP_ADDRESS_LIST.add("10.45.234.56");
        IP_ADDRESS_LIST.add("10.45.234.56");
        IP_ADDRESS_LIST.add("10.45.234.56");
        IP_ADDRESS_LIST.add("10.45.234.56");

        IP_ADDRESS_LIST.add("120.45.24.5");
        IP_ADDRESS_LIST.add("120.45.24.5");
        IP_ADDRESS_LIST.add("120.45.24.5");
        IP_ADDRESS_LIST.add("120.45.24.5");

        IP_ADDRESS_LIST.add("142.65.234.56");
        IP_ADDRESS_LIST.add("142.65.234.56");
        IP_ADDRESS_LIST.add("142.65.234.56");
        IP_ADDRESS_LIST.add("142.65.234.56");

        IP_ADDRESS_LIST.add("140.65.234.56");
        IP_ADDRESS_LIST.add("140.65.234.56");

        IP_ADDRESS_LIST.add("106.45.23.5");
        IP_ADDRESS_LIST.add("106.45.23.5");
        IP_ADDRESS_LIST.add("106.45.23.5");
        IP_ADDRESS_LIST.add("106.45.23.5");

        IP_ADDRESS_LIST.add("223.45.21.56");
        IP_ADDRESS_LIST.add("223.45.21.56");
        IP_ADDRESS_LIST.add("223.45.21.56");
        IP_ADDRESS_LIST.add("223.45.21.56");
        IP_ADDRESS_LIST.add("223.45.21.56");
        IP_ADDRESS_LIST.add("223.45.21.56");
        IP_ADDRESS_LIST.add("223.45.21.56");

        IP_ADDRESS_LIST.add("220.45.21.56");
        IP_ADDRESS_LIST.add("220.45.21.56");
        IP_ADDRESS_LIST.add("220.45.21.56");
        IP_ADDRESS_LIST.add("220.45.21.56");
        
        IP_ADDRESS_LIST.add("123.43.234.56");
        IP_ADDRESS_LIST.add("123.43.234.56");
        IP_ADDRESS_LIST.add("123.43.234.56");
        IP_ADDRESS_LIST.add("123.43.234.56");
        IP_ADDRESS_LIST.add("123.43.234.56");
        
        IP_ADDRESS_LIST.add("90.15.14.12");
        IP_ADDRESS_LIST.add("90.15.14.12");
        IP_ADDRESS_LIST.add("90.15.14.12");
        
        IP_ADDRESS_LIST.add("111.75.24.22");
        IP_ADDRESS_LIST.add("111.75.24.22");
        IP_ADDRESS_LIST.add("111.75.24.22");
        IP_ADDRESS_LIST.add("111.75.24.22");
        
        IP_ADDRESS_LIST.add("211.45.234.156");
        IP_ADDRESS_LIST.add("211.45.234.156");
        IP_ADDRESS_LIST.add("211.45.234.156");
        IP_ADDRESS_LIST.add("211.45.234.156");
        IP_ADDRESS_LIST.add("211.45.234.156");
        IP_ADDRESS_LIST.add("211.45.234.156");
        IP_ADDRESS_LIST.add("211.45.234.156");
    }
    
    public static final ArrayList<String> ACTION_LIST;
    static {
        ACTION_LIST = new ArrayList<>();
        ACTION_LIST.add("POST");
        ACTION_LIST.add("GET");
    }
    
    public static final ArrayList<String> URL_LIST;
    static {
        URL_LIST = new ArrayList<>();
        URL_LIST.add("/test");
        URL_LIST.add("/test?p=34");
        URL_LIST.add("/test/all?f=456");
        URL_LIST.add("/test/cart?id=4234");
        URL_LIST.add("/test/cart?id=122");
        URL_LIST.add("/test/cart?id=122");
        URL_LIST.add("/test/cart?id=22");
        URL_LIST.add("/test/cart?id=13");
        URL_LIST.add("/test/cart?id=23");
        URL_LIST.add("/test/cart?id=56");
        URL_LIST.add("/test/cart?id=123");
    }
}
