package mems.jiaochuan.com.mems_2000.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import mems.jiaochuan.com.mems_2000.R;

/**
 * @author 刘建阳
 * @time 2016/10/19 16:30
 */

public class SplashActivity extends Activity {

    @BindView(R.id.tv_version_name)
    TextView tv_version_name;
    @BindView(R.id.rl_root)
    RelativeLayout rl_root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ButterKnife.bind(this);

        initAnimation();
        initData();
    }

    /**
     * 初始化动画
     */
    private void initAnimation() {
        AlphaAnimation alpha = new AlphaAnimation(0, 1); // 创建淡入动画
        alpha.setDuration(3000); // 设置动画执行时间
        rl_root.startAnimation(alpha); // 开启动画
        //设置动画监听
        alpha.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //动画结束，进入主界面
                enterHome();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    /**
     * 初始化数据
     */
    private void initData() {
        // 1.获取应用版本名称
        tv_version_name.setText("版本名称：" + getVersionName());
    }

    /**
     * 获取应用版本名称：从清单文件中获取
     *
     * @return 应用版本名称 返回null代表异常
     */
    private String getVersionName() {
        // 1.包管理者对象PackageManager
        PackageManager pm = getPackageManager();
        // 2.从包管理者对象中获取指定包名的基本信息(版本名称，版本号)，传0代表获取基本信息
        try {
            PackageInfo packageInfo = pm.getPackageInfo(getPackageName(), 0);
            // 3.获取版本名称
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 进入应用程序主界面，Activity跳转过程的方法
     */
    protected void enterHome() {

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

        // 在开启一个新的界面后，将导航界面关闭(导航界面只可见一次)
        finish();
    }


}
