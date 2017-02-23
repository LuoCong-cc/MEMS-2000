package mems.jiaochuan.com.mems_2000.model;

import java.util.ArrayList;

/**
 * 报警信息列表数据
 * @author 刘建阳
 * @time 2016/8/11 8:59
 */
public class WarningMenu {

    public ArrayList<WarningData> data;
    public String more;

    public class WarningData {
        public int id;
        public String icon;
        public String title;
        public String des;
        public String time;
        public String detailurl;

    }

}
