package mems.jiaochuan.com.mems_2000.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
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
import mems.jiaochuan.com.mems_2000.model.ElectricDetailBean;

/**
 * @author 刘建阳
 * @time 2016/9/26 15:03
 */
public class ElectricDetailActivity extends Activity {

    private ArrayList<ElectricDetailBean.ElectricDetailData> datas;

    @BindView(R.id.lv_water_detail)
    ListView lv_water;
    @BindView(R.id.tv_title)
    TextView tv_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_electric_detail);

        ButterKnife.bind(this);

        getDataFromServer();

    }

    /**
     * 从服务端获取数据
     */
    public void getDataFromServer() {

        String url = getIntent().getStringExtra("detailelectric");
        String title = getIntent().getStringExtra("title");
        tv_title.setText(title +"电能耗");

        HttpUtils utils = new HttpUtils();
        utils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {

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
        ElectricDetailBean electricDetailBean = gson.fromJson(result, ElectricDetailBean.class);

        datas = electricDetailBean.data;
        if (datas != null) {
            //给ListView添加适配器
            lv_water.setAdapter(new MyElectricDetailAdapter());
        }
    }

    class MyElectricDetailAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return datas.size();
        }

        @Override
        public ElectricDetailBean.ElectricDetailData getItem(int i) {
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

            ElectricDetailBean.ElectricDetailData item = getItem(i);
            holder.tvTitle.setText(item.title);
            holder.tvData.setText("用电量为："+ item.energycount);
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
