package mems.jiaochuan.com.mems_2000.base.impl;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.xw.repo.refresh.PullListView;
import com.xw.repo.refresh.PullToRefreshLayout;

import org.feezu.liuli.timeselector.TimeSelector;

import java.util.ArrayList;
import java.util.List;

import mems.jiaochuan.com.mems_2000.R;
import mems.jiaochuan.com.mems_2000.api.GlobalConstant;
import mems.jiaochuan.com.mems_2000.base.BasePager;
import mems.jiaochuan.com.mems_2000.config.Config;
import mems.jiaochuan.com.mems_2000.model.WarningMenu;
import mems.jiaochuan.com.mems_2000.ui.activity.WarningDetailActivity;
import mems.jiaochuan.com.mems_2000.ui.view.MyPopupWindow;

/**
 * 告警页面
 *
 * @author 刘建阳
 * @time 2016/8/10 14:02
 */
public class WarningPager extends BasePager implements View.OnClickListener, PullToRefreshLayout.OnRefreshListener {

    private PullListView pullListView;

    private ArrayList<WarningMenu.WarningData> datas;
    private View mView;
    private TextView tv_time_select;
    private TextView tv_station_select;
    private TextView tv_level_select;
    private TextView tv_device_select;
    private TextView tv_system_select;
    private TextView tv_sure_select;
    private TextView tv_key_word_select;
    private ImageView iv_calendar;
    private ImageView iv_station_select;
    private ImageView iv_level_select;
    private ImageView iv_device_select;
    private ImageView iv_system_select;
    private ImageView iv_sure_select;
    private ImageView iv_key_word_select;
    private Button btn_sure;
    private AlertDialog mDialog;
    private RelativeLayout rl_content;
    private WarningAdapter mAdapter;
    private PullToRefreshLayout mRefreshLayout;
    private List<WarningMenu.WarningData> warningDataList;
    private String mMore;

    public WarningPager(Activity activity) {
        super(activity);

        initData();

    }

    @Override
    public View initView() {
        mView = View.inflate(mActivity, R.layout.tab_warning_menu_detail, null);
        pullListView = (PullListView) mView.findViewById(R.id.pullListView);
        mRefreshLayout = (PullToRefreshLayout) mView.findViewById(R.id.pullToRefreshLayout);
        mRefreshLayout.setOnRefreshListener(this);


        return super.initView();
    }

    @Override
    public void initData() {
        btn_search.setVisibility(View.VISIBLE);
        btn_search.setOnClickListener(this);
        warningDataList = new ArrayList<WarningMenu.WarningData>();

        // 要给帧布局填充布局对象
        flContent.addView(mView);
        tv_title.setText("报警信息");


        new Thread(){
            @Override
            public void run() {
                getDataFromServer();
            }
        }.start();

    }

    /**
     * 从服务端获取数据
     */
    public void getDataFromServer() {

        HttpUtils utils = new HttpUtils();
        utils.send(HttpMethod.GET, GlobalConstant.WARNING_DATAS_URL, new RequestCallBack<String>() {

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                //请求成功
                String result = responseInfo.result;
                processData(result,false);

            }

            @Override
            public void onFailure(HttpException error, String msg) {
                // 请求失败
                error.printStackTrace();
                Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT).show();
            }
        });

    }

    /**
     * 请求服务器数据成功后处理json数据
     *
     * @param result 服务端返回的json数据
     */
    protected void processData(String result, boolean isMore) {
        Gson gson = new Gson();
        WarningMenu warningMenu = gson.fromJson(result, WarningMenu.class);
        datas = warningMenu.data;
        warningDataList.addAll(datas);
        mMore = warningMenu.more;
        if (!isMore) {
            if (warningDataList != null) {
                //给ListView添加适配器
                mAdapter = new WarningAdapter();
                pullListView.setAdapter(mAdapter);
                //给ListView设置点击事件
                pullListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                        String detailUrl = datas.get(i).detailurl;
                        Intent intent = new Intent(mActivity, WarningDetailActivity.class);
                        intent.putExtra("detailUrl", detailUrl);
                        mActivity.startActivity(intent);
                    }
                });

            }
        } else {
            // 加载更多数据
            List<WarningMenu.WarningData> moreWarningData = warningMenu.data;
            warningDataList.addAll(moreWarningData);// 将数据追加到原来的集合中


            // 刷新listview
            mAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_search:
                showDialog();
                break;
            case R.id.iv_calendar:
                TimeSelector timeSelector = new TimeSelector(mActivity, new TimeSelector.ResultHandler() {
                    @Override
                    public void handle(String time) {
                        tv_time_select.setText(time);
                    }
                }, "2015-11-22 17:34", "2200-01-01 00:00");
                timeSelector.show();
                break;
            case R.id.iv_station_select:
                new MyPopupWindow(mActivity, Config.stations, tv_station_select, rl_content);
                break;
            case R.id.iv_level_select:
                new MyPopupWindow(mActivity, Config.levels, tv_level_select, rl_content);
                break;
            case R.id.iv_device_select:
                new MyPopupWindow(mActivity, Config.devices, tv_device_select, rl_content);

                break;
            case R.id.iv_system_select:
                new MyPopupWindow(mActivity, Config.systems, tv_system_select, rl_content);

                break;
            case R.id.iv_sure_select:
                new MyPopupWindow(mActivity, Config.personSure, tv_sure_select, rl_content);


                break;
            case R.id.iv_key_word_select:
                new MyPopupWindow(mActivity, Config.keyWord, tv_key_word_select, rl_content);

                break;
            case R.id.btn_sure:
                submit();
                mDialog.dismiss();
                break;
        }
    }

    /**
     * 条件查询提交
     */
    private void submit() {
        // 写SQL从数据库中进行查询


    }

    /**
     * 弹出对话框
     */
    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        mDialog = builder.create();
        View alertView = View.inflate(mActivity, R.layout.alert_dialog, null);

        mDialog.setView(alertView, 0, 0, 0, 0);
        mDialog.show();

        initDialogUI(alertView);

    }

    private void initDialogUI(View alertView) {
        tv_time_select = (TextView) alertView.findViewById(R.id.tv_time_select);
        tv_station_select = (TextView) alertView.findViewById(R.id.tv_station_select);
        tv_level_select = (TextView) alertView.findViewById(R.id.tv_level_select);
        tv_device_select = (TextView) alertView.findViewById(R.id.tv_device_select);
        tv_system_select = (TextView) alertView.findViewById(R.id.tv_system_select);
        tv_sure_select = (TextView) alertView.findViewById(R.id.tv_sure_select);
        tv_key_word_select = (TextView) alertView.findViewById(R.id.tv_key_word_select);
        iv_calendar = (ImageView) alertView.findViewById(R.id.iv_calendar);
        iv_station_select = (ImageView) alertView.findViewById(R.id.iv_station_select);
        iv_level_select = (ImageView) alertView.findViewById(R.id.iv_level_select);
        iv_device_select = (ImageView) alertView.findViewById(R.id.iv_device_select);
        iv_system_select = (ImageView) alertView.findViewById(R.id.iv_system_select);
        iv_sure_select = (ImageView) alertView.findViewById(R.id.iv_sure_select);
        iv_key_word_select = (ImageView) alertView.findViewById(R.id.iv_key_word_select);
        rl_content = (RelativeLayout) alertView.findViewById(R.id.rl_content);
        btn_sure = (Button) alertView.findViewById(R.id.btn_sure);

        iv_calendar.setOnClickListener(this);
        iv_station_select.setOnClickListener(this);
        iv_level_select.setOnClickListener(this);
        iv_system_select.setOnClickListener(this);
        iv_device_select.setOnClickListener(this);
        iv_sure_select.setOnClickListener(this);
        iv_key_word_select.setOnClickListener(this);
        btn_sure.setOnClickListener(this);
    }

    // 下拉刷新
    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
        mRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {

                mRefreshLayout.refreshFinish(true);
                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        getDataFromServer();
                    }
                }.start();
            }
        }, 2000); // 2秒后刷新
    }

    // 上拉加载更多
    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
        mRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.loadMoreFinish(true);
                // 判断是否有下一页数据
                if (mMore != null) {
                    // 有下一页
                    new Thread(){
                        @Override
                        public void run() {
                            super.run();
                            getMoreDataFromServer();
                        }
                    }.start();

                } else {
                    // 没有下一页
                    Toast.makeText(mActivity, "没有更多数据了", Toast.LENGTH_SHORT)
                            .show();
                }

            }
        }, 2000); // 2秒后刷新
    }

    private void getMoreDataFromServer() {
        HttpUtils utils = new HttpUtils();
        utils.send(HttpMethod.GET, mMore, new RequestCallBack<String>() {

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                //请求成功
                String result = responseInfo.result;
                processData(result, true);

            }

            @Override
            public void onFailure(HttpException error, String msg) {
                // 请求失败
                error.printStackTrace();
                Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }


    //ListView报警信息适配器
    class WarningAdapter extends BaseAdapter {

        BitmapUtils mBitmapUtils;

        public WarningAdapter() {
            mBitmapUtils = new BitmapUtils(mActivity);
        }

        @Override
        public int getCount() {
            return warningDataList.size();
        }

        @Override
        public WarningMenu.WarningData getItem(int i) {
            return warningDataList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder holder;
            if (view == null) {
                view = View.inflate(mActivity, R.layout.list_item_warning, null);
                holder = new ViewHolder();
                holder.ivIcon = (ImageView) view.findViewById(R.id.iv_icon);
                holder.tvTitle = (TextView) view.findViewById(R.id.tv_item_title);
                holder.tvDes = (TextView) view.findViewById(R.id.tv_des);
                holder.tvDate = (TextView) view.findViewById(R.id.tv_date);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }

            WarningMenu.WarningData item = getItem(i);
            holder.tvTitle.setText(item.title);
            holder.tvDes.setText(item.des);
            holder.tvDate.setText(item.time);
            mBitmapUtils.display(holder.ivIcon, item.icon);
            return view;
        }
    }

    static class ViewHolder {
        public ImageView ivIcon;
        public TextView tvTitle;
        public TextView tvDes;
        public TextView tvDate;
    }


}
