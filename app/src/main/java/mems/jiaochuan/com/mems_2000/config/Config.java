package mems.jiaochuan.com.mems_2000.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mems.jiaochuan.com.mems_2000.R;

/**
 * @author 刘建阳
 * @time 2016/10/13 15:53
 */
public class Config {


    /**
     * 地铁三号线的站点
     */
    public static final String[] stations = {
            "太平园站", "红牌楼站", "高升桥站", "衣冠庙站",
            "省体育馆站", "磨子桥站", "新南门站", "春熙路站",
            "市二医院站", "红星桥站", "前锋路站", "李家沱站",
            "驷马桥站", "昭觉寺南路站", "动物园站", "熊猫大道站", "军区总医院站"};


    /**
     * 普通站点的图标
     */
    public static final List<Integer> commonDatas = new ArrayList<Integer>(Arrays.asList(
            R.mipmap.common_station_icon1, R.mipmap.common_station_icon2, R.mipmap.common_station_icon3,
            R.mipmap.common_station_icon4, R.mipmap.common_station_icon1, R.mipmap.common_station_icon2,
            R.mipmap.common_station_icon3, R.mipmap.common_station_icon4));

    /**
     * 换乘站点的图标
     */
    public static final Integer trasferStation = R.mipmap.transfer_station_icon;

    /**
     * 告警等级
     */
    public static final String[] levels = {"优","一级","二级","三级","四级","五级"};

    /**
     * 告警的状态
     */
    public static final String[] isHandle = {"已处理","未处理"};

    /**
     * 系统
     */
    public static final String[] systems = {"电力","水","燃气","暖通"};

    /**
     * 设备名称
     */
    public static final String[] devices = {"空调","电梯","照明"};

    /**
     * 确认人
     */
    public static final String[] personSure = {"张三","李四","王五","赵六"};

    /**
     * 关键字
     */
    public static final String[] keyWord = {"燃气","水","热力","电"};


}
