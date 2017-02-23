package mems.jiaochuan.com.mems_2000.ui.view;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import mems.jiaochuan.com.mems_2000.R;

/**
 * @author 刘建阳
 * @time 2016/10/24 14:58
 */

public class MyPopupWindow extends PopupWindow implements AdapterView.OnItemClickListener {

    private Activity mActivity;
    private TextView mTV;
    private String[] mDatas;
    private RelativeLayout mRL;
    private ListView mListView;
    private PopupWindow mPopupWindow;

    public MyPopupWindow(Activity activity, String[] datas, TextView tv, RelativeLayout rl){

        this.mActivity = activity;
        this.mTV = tv;
        this.mDatas = datas;
        this.mRL = rl;
        showPopupWindow();
    }

    private void showPopupWindow() {
        initListView();

        // 显示下拉选择框
        mPopupWindow = new PopupWindow(mListView, mRL.getWidth(), 150);

        mPopupWindow.setOutsideTouchable(true);// 设置外部可触摸
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());// 设置空背景，响应点击事件

        mPopupWindow.setFocusable(true);

        // 显示在指定的控件下
        mPopupWindow.showAsDropDown(mTV, 0, -5);
    }

    // 初始化要显示的数据
    private void initListView() {
        mListView = new ListView(mActivity);
        mListView.setDividerHeight(0);
        mListView.setBackgroundResource(R.mipmap.listview_background);
        mListView.setOnItemClickListener(this);


        MyAdapter myAdapter = new MyAdapter();
        mListView.setAdapter(myAdapter);
    }


    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mDatas.length;
        }

        @Override
        public Object getItem(int position) {
            return mDatas[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(mActivity, R.layout.list_select_item, null);
            }
            TextView tv_item = (TextView) convertView.findViewById(R.id.tv_item);
            tv_item.setText(mDatas[position]);
            return convertView;
        }

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        String string = mDatas[position];
        mTV.setText(string);

        mPopupWindow.dismiss();
    }
}
