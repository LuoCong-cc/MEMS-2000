package mems.jiaochuan.com.mems_2000.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import mems.jiaochuan.com.mems_2000.R;
import mems.jiaochuan.com.mems_2000.ui.fragment.ContentFragment;
import mems.jiaochuan.com.mems_2000.utils.PrefUtils;

/**
 * 主页面
 * @author 刘建阳
 * @time 2016/10/19 17:10
 */

public class MainActivity extends FragmentActivity {

    private static final String TAG_CONTENT = "TAG_CONTENT";
    public static String username = "";
    public static int page = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = getIntent().getStringExtra("username");
        page = getIntent().getIntExtra("page", 0);

        if (username == null && page == 0) {
            PrefUtils.putBoolean(this, "login", false);
        }

        /*if (username!=null && page ==2){
            PrefUtils.putBoolean(this,"login",true);
        }else {
            PrefUtils.putBoolean(this,"login",false);
        }*/



        initFragment();
    }

    /**
     * 初始化Fragment
     */
    private void initFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();// 开始事务
        transaction.replace(R.id.fl_main, new ContentFragment(), TAG_CONTENT);
        transaction.commit();

    }



}
