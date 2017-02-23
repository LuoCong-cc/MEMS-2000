package mems.jiaochuan.com.mems_2000.model;

import java.util.ArrayList;

/**
 * @author 刘建阳
 * @time 2016/10/25 14:15
 */

public class ElectricDetailBean {

    public ArrayList<ElectricDetailData> data;

    public class ElectricDetailData {
        public int id;
        public String title;
        public String energycount;

    }

}
