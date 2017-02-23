package mems.jiaochuan.com.mems_2000.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import butterknife.BindView;
import butterknife.ButterKnife;
import mems.jiaochuan.com.mems_2000.R;
import mems.jiaochuan.com.mems_2000.model.WarningDetail;

/**
 * @author 刘建阳
 * @time 2016/8/12 9:40
 */
public class WarningDetailActivity extends Activity {

    @BindView(R.id.tv_station)
    TextView tv_station;

    @BindView(R.id.tv_level)
    TextView tv_level;

    @BindView(R.id.tv_date)
    TextView tv_date;

    @BindView(R.id.tv_device)
    TextView tv_device;

    @BindView(R.id.tv_cause)
    TextView tv_cause;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warning_detail);

        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        String url = getIntent().getStringExtra("detailUrl");

        HttpUtils utils = new HttpUtils();
        utils.send(HttpMethod.GET, url, new RequestCallBack<Object>() {
            @Override
            public void onSuccess(ResponseInfo<Object> responseInfo) {
                //请求成功
                String result = (String) responseInfo.result;
                processData(result);

            }

            @Override
            public void onFailure(HttpException e, String s) {
                //请求失败
            }

        });
    }

    private void processData(String result) {

        Gson gson = new Gson();
        WarningDetail detail = gson.fromJson(result, WarningDetail.class);
        tv_station.setText(detail.station);
        tv_level.setText(detail.level);
        tv_date.setText(detail.time);
        tv_device.setText(detail.device);
        tv_cause.setText(detail.cause);
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
