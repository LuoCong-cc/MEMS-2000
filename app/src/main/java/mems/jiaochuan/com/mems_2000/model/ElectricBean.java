package mems.jiaochuan.com.mems_2000.model;

import java.util.ArrayList;

/**
 * @author 刘建阳
 * @time 2016/10/25 14:02
 */

public class ElectricBean {

    public ArrayList<ElectricData> data;

    public class ElectricData {
        public int id;
        public String title;
        public String energycount;
        public String detailelectric;

    }

}
