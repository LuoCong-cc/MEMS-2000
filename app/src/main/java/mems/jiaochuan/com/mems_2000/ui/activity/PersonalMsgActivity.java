package mems.jiaochuan.com.mems_2000.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import mems.jiaochuan.com.mems_2000.R;
import mems.jiaochuan.com.mems_2000.utils.PrefUtils;

/**
 * 项目名称：MEMS-2000
 * 类描述：消息通知页面
 * 创建人：12078
 * 创建时间：2016/10/19 16:32
 * 修改人：12078
 * 修改时间：2016/10/19 16:32
 * 修改备注：
 */

public class PersonalMsgActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView mTvTitle;
    private ImageButton mBtnBack;
    private SwitchCompat mSettingSwitch,mSettingSwitchVoice,mSettingSwitchShake;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_msg);

        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mBtnBack = (ImageButton) findViewById(R.id.btn_back);
        mSettingSwitch = (SwitchCompat) findViewById(R.id.setting_switch);
        mSettingSwitchVoice = (SwitchCompat) findViewById(R.id.setting_switch_voice);
        mSettingSwitchShake = (SwitchCompat) findViewById(R.id.setting_switch_shake);

        mBtnBack.setOnClickListener(this);
        mSettingSwitch.setOnClickListener(this);
        mSettingSwitchVoice.setOnClickListener(this);
        mSettingSwitchShake.setOnClickListener(this);



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
        mTvTitle.setText("消息通知");
        mBtnBack.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_back:
                onBackPressed();
                //结束当前页面
                finish();
                break;
            case  R.id.setting_switch:
                if (!mSettingSwitch.isChecked()){
                    Toast.makeText(this, "关闭消息推送", Toast.LENGTH_SHORT).show();
                    PrefUtils.putBoolean(this,"shouldPush", false);

                }else {
                    Toast.makeText(this, "打开消息推送", Toast.LENGTH_SHORT).show();
                    PrefUtils.putBoolean(this,"shouldPush", true);

                }
                break;
            case  R.id.setting_switch_voice:
                if (!mSettingSwitchVoice.isChecked()){
                    Toast.makeText(this, "关闭声音", Toast.LENGTH_SHORT).show();
                    PrefUtils.putBoolean(this,"shouldPushvoice", false);
                }else {
                    Toast.makeText(this, "打开声音", Toast.LENGTH_SHORT).show();
                    PrefUtils.putBoolean(this,"shouldPushvoice", true);
                }
                break;
            case  R.id.setting_switch_shake:
                if (!mSettingSwitchVoice.isChecked()){
                    Toast.makeText(this, "关闭震动", Toast.LENGTH_SHORT).show();
                    PrefUtils.putBoolean(this,"shouldPushshake", false);
                }else {
                    Toast.makeText(this, "打开震动", Toast.LENGTH_SHORT).show();
                    PrefUtils.putBoolean(this,"shouldPushshake", true);
                }
                break;
        }
    }
}
