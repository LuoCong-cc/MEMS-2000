package mems.jiaochuan.com.mems_2000.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import mems.jiaochuan.com.mems_2000.R;

/**
 * @author 刘建阳
 * @time 2016/9/13 16:11
 */
public class StationEnergyActivity extends Activity {

    @BindView(R.id.gv_home)
    GridView gv_home;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_energy)
    TextView tv_energy;

    private String[] mTitleStrs;

    private int[] mDrawableIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_sub);

        ButterKnife.bind(this);

        // 初始化数据
        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        mTitleStrs = new String[] { "水", "电", "燃气", "热力" };
        mDrawableIds = new int[] { R.mipmap.water_second,
                R.mipmap.electric_second, R.mipmap.gas_second,
                R.mipmap.heat_second };
        final String station = getIntent().getStringExtra("station");
        tv_title.setText(station);
        tv_energy.setText(station+"能耗");

        // 九宫格控件设置数据适配器
        gv_home.setAdapter(new MyAdapter());
        // 注册九宫格单个条目的点击事件
        gv_home.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            // position点中列表条目的索引
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                switch (position) {
                    case 0:
                        Intent waterIntent = new Intent(getApplicationContext(), StationEnergyOfWaterActivity.class);
                        waterIntent.putExtra("station",station);
                        startActivity(waterIntent);
                        break;
                    case 1:
                        Intent electricIntent = new Intent(getApplicationContext(), StationEnergyOfElectricActivity.class);
                        electricIntent.putExtra("station",station);
                        startActivity(electricIntent);

                        break;
                    case 2:
                        Intent gasIntent = new Intent(getApplicationContext(), StationEnergyOfGasActivity.class);
                        gasIntent.putExtra("station",station);
                        startActivity(gasIntent);

                        break;
                    case 3:
                        Intent heatIntent = new Intent(getApplicationContext(), StationEnergyOfHeatActivity.class);
                        heatIntent.putExtra("station",station);
                        startActivity(heatIntent);

                        break;

                }

            }
        });
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mTitleStrs.length;
        }

        @Override
        public Object getItem(int position) {
            return mTitleStrs[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(getApplicationContext(),
                    R.layout.gridview_item, null);
            ImageView ic_icon = (ImageView) view.findViewById(R.id.iv_icon);
            TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
            ic_icon.setImageResource(mDrawableIds[position]);
            tv_title.setText(mTitleStrs[position]);
            return view;
        }

    }

    public void backClick(View view){

        onBackPressed();
        //结束当前页面
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
