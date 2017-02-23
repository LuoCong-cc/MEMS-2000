package mems.jiaochuan.com.mems_2000.model;

import java.util.ArrayList;

/**
 * @author 刘建阳
 * @time 2016/9/26 14:14
 */
public class WaterBean {

    public ArrayList<WaterData> data;

    public class WaterData {
        public int id;
        public String title;
        public String energycount;
        public String time;
        public String detailwater;

    }

}
