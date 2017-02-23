package mems.jiaochuan.com.mems_2000.api;

/**
 * @author 刘建阳
 * @time 2016/8/11 10:33
 */
public class GlobalConstant {

    /**
     * 请求服务器的地址URL
     */
    public static final String SERVER_URL = "http://192.168.1.119:8080/MEMS_2000/";

//    public static final String SERVER_URL = "http://10.0.2.2:8080/MEMS_2000/";

    /**
     * 报警信息请求接口
     */
    public static final String WARNING_DATAS_URL = SERVER_URL + "/warningdata.json";

    /**
     * 分项水能耗请求接口
     */
    public static final String WATER_DATAS_URL = SERVER_URL + "/waterdata.json";

    /**
     * 分项点能耗请求接口
     */
    public static final String ELECTRIC_DATA_URL = SERVER_URL + "electricdata.json";


}
