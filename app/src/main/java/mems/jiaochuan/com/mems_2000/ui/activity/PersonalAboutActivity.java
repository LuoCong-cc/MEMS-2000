package mems.jiaochuan.com.mems_2000.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import mems.jiaochuan.com.mems_2000.R;

/**
 * 项目名称：MEMS-2000
 * 类描述：
 * 创建人：12078
 * 创建时间：2016/10/19 16:32
 * 修改人：12078
 * 修改时间：2016/10/19 16:32
 * 修改备注：
 */

public class PersonalAboutActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView mTvTitle;
    private ImageButton mBtnBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_about);

        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mBtnBack = (ImageButton) findViewById(R.id.btn_back);

        mBtnBack.setOnClickListener(this);
        initView();
    }

    /**
     *@data 创建时间：2016/10/20
     *@Description 加载页面
     *@param
     *@retun
     *
     */
    private void initView() {
        mTvTitle.setText("关于MEMS");
        mBtnBack.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                onBackPressed();
                //结束当前页面
                finish();
                break;
        }
    }
}
