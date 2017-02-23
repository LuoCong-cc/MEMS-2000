package mems.jiaochuan.com.mems_2000.base;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import mems.jiaochuan.com.mems_2000.R;

/**
 * 三个标签页的基类
 * @author 刘建阳
 * @time 2016/8/10 13:52
 */
public class BasePager {

    public Activity mActivity;

    public FrameLayout flContent;// 空的帧布局对象, 要动态添加布局
    public TextView tv_title; // 标题栏
    public ImageButton btn_search; // 告警页面条件查询按钮*/

    public View mRootView;// 当前页面的布局对象

    public BasePager(Activity activity) {
        mActivity = activity;
        mRootView = initView();
    }

    // 初始化布局
    public View initView() {
        View view = View.inflate(mActivity, R.layout.base_pager, null);
//        ButterKnife.bind(this,view);
        flContent = (FrameLayout) view.findViewById(R.id.fl_content);
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        btn_search = (ImageButton) view.findViewById(R.id.btn_search);
        return view;
    }

    // 初始化数据
    public void initData() {

    }
}
