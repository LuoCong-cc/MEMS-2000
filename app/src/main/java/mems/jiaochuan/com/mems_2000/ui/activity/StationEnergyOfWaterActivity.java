package mems.jiaochuan.com.mems_2000.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import mems.jiaochuan.com.mems_2000.R;
import mems.jiaochuan.com.mems_2000.api.GlobalConstant;
import mems.jiaochuan.com.mems_2000.model.WaterBean;

/**
 * @author 刘建阳
 * @time 2016/9/26 9:53
 */
public class StationEnergyOfWaterActivity extends Activity {

    private ArrayList<WaterBean.WaterData> datas;

    @BindView(R.id.lv_water)
    ListView lv_water;
    @BindView(R.id.tv_title)
    TextView tv_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_water);

        ButterKnife.bind(this);

        String station = getIntent().getStringExtra("station");
        tv_title.setText(station+"水能耗");

        getDataFromServer();

    }

    /**
     * 从服务端获取数据
     */
    public void getDataFromServer() {

        HttpUtils utils = new HttpUtils();
        utils.send(HttpRequest.HttpMethod.GET, GlobalConstant.WATER_DATAS_URL, new RequestCallBack<String>() {

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                //请求成功
                String result = responseInfo.result;
                processData(result);

            }

            @Override
            public void onFailure(HttpException error, String msg) {
                // 请求失败
                error.printStackTrace();
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
            }
        });

    }

    /**
     * 请求服务器数据成功后处理json数据
     *
     * @param result 服务端返回的json数据
     */
    protected void processData(String result) {
        Gson gson = new Gson();
        WaterBean waterBean = gson.fromJson(result, WaterBean.class);

        datas = waterBean.data;
        if (datas != null) {
            //给ListView添加适配器
            lv_water.setAdapter(new MyWaterAdapter());
            //给ListView设置点击事件
            lv_water.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    String detailwater = datas.get(i).detailwater;

                    /*Intent intent = new Intent(getApplicationContext(), WaterDetailActivity.class);
                    intent.putExtra("detailwater", detailwater);
                    startActivity(intent);*/
                }
            });

        }
    }

    class MyWaterAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return datas.size();
        }

        @Override
        public WaterBean.WaterData getItem(int i) {
            return datas.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder holder;
            if (view == null) {
                view = View.inflate(getApplicationContext(), R.layout.list_item_water, null);
                holder = new ViewHolder();
                holder.tvTitle = (TextView) view.findViewById(R.id.tv_item_title);
                holder.tvData = (TextView) view.findViewById(R.id.tv_data);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }

            WaterBean.WaterData item = getItem(i);
            holder.tvTitle.setText(item.title);
            holder.tvData.setText("用水量为："+ item.energycount);
            return view;
        }
    }

    static class ViewHolder {
        public TextView tvTitle;
        public TextView tvData;
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
