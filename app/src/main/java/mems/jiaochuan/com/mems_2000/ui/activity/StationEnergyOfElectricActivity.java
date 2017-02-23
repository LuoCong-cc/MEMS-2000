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
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mems.jiaochuan.com.mems_2000.R;
import mems.jiaochuan.com.mems_2000.api.GlobalConstant;
import mems.jiaochuan.com.mems_2000.model.ElectricBean;
import mems.jiaochuan.com.mems_2000.model.WaterBean;

/**
 * @author 刘建阳
 * @time 2016/9/26 9:53
 */
public class StationEnergyOfElectricActivity extends Activity {

    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.lv_electric)
    ListView lv_electric;
    private List<ElectricBean.ElectricData> datas;
    private String station;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_electric);

        ButterKnife.bind(this);

        station = getIntent().getStringExtra("station");
        tv_title.setText(station +"电能耗");

        getDataFromServer();

    }

    /**
     * 从服务端获取数据
     */
    public void getDataFromServer() {

        HttpUtils utils = new HttpUtils();
        utils.send(HttpRequest.HttpMethod.GET, GlobalConstant.ELECTRIC_DATA_URL, new RequestCallBack<String>() {

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
        ElectricBean electricBean = gson.fromJson(result, ElectricBean.class);

        datas = electricBean.data;
        if (datas != null) {
            //给ListView添加适配器
            lv_electric.setAdapter(new MyElectricAdapter());
            //给ListView设置点击事件
            lv_electric.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    String detailelectric = datas.get(i).detailelectric;
                    String title = datas.get(i).title;

                    Intent intent = new Intent(getApplicationContext(), ElectricDetailActivity.class);
                    intent.putExtra("detailelectric", detailelectric);
                    intent.putExtra("title",title);

                    startActivity(intent);
                }
            });

        }
    }

    class MyElectricAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return datas.size();
        }

        @Override
        public ElectricBean.ElectricData getItem(int i) {
            return datas.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            StationEnergyOfWaterActivity.ViewHolder holder;
            if (view == null) {
                view = View.inflate(getApplicationContext(), R.layout.list_item_water, null);
                holder = new StationEnergyOfWaterActivity.ViewHolder();
                holder.tvTitle = (TextView) view.findViewById(R.id.tv_item_title);
                holder.tvData = (TextView) view.findViewById(R.id.tv_data);
                view.setTag(holder);
            } else {
                holder = (StationEnergyOfWaterActivity.ViewHolder) view.getTag();
            }

            ElectricBean.ElectricData item = getItem(i);
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
