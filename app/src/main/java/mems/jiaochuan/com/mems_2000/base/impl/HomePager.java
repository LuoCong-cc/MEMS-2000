package mems.jiaochuan.com.mems_2000.base.impl;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import mems.jiaochuan.com.mems_2000.R;
import mems.jiaochuan.com.mems_2000.adapter.HorizontalScrollViewAdapter;
import mems.jiaochuan.com.mems_2000.base.BasePager;
import mems.jiaochuan.com.mems_2000.ui.activity.StationEnergyActivity;
import mems.jiaochuan.com.mems_2000.ui.view.MyHorizontalScrollView;

/**
 *
 * 首页
 * @author 刘建阳
 * @time 2016/8/10 13:58
 */
public class HomePager extends BasePager {


    private static final String TAG = "HomePager";
    private HorizontalScrollViewAdapter mAdapter;
    private MyHorizontalScrollView id_horizontalScrollView;

    public HomePager(Activity activity) {
        super(activity);

        initData();

    }

    @Override
    public void initData() {

        Log.i(TAG,"首页初始化啦。。。");
        View view = View.inflate(mActivity, R.layout.tab_home_menu_detail, null);
        id_horizontalScrollView = (MyHorizontalScrollView) view.findViewById(R.id.id_horizontalScrollView);
        flContent.addView(view);
        tv_title.setText("地铁能源监管系统");


        mAdapter = new HorizontalScrollViewAdapter(mActivity);
        // 添加点击回调
        id_horizontalScrollView.setOnItemClickListener(new MyHorizontalScrollView.OnItemClickListener() {

            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(mActivity, StationEnergyActivity.class);
                intent.putExtra("station", (String) mAdapter.getItem(position));
                mActivity.startActivity(intent);
            }
        });
        // 设置适配器
        id_horizontalScrollView.initDatas(mAdapter);

    }
}
