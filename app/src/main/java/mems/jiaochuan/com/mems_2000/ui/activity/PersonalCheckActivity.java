package mems.jiaochuan.com.mems_2000.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import mems.jiaochuan.com.mems_2000.R;
import mems.jiaochuan.com.mems_2000.utils.StreamUtil;
import mems.jiaochuan.com.mems_2000.utils.ToastUtil;


/**
 * 项目名称：MEMS-2000
 * 类描述：
 * 创建人：12078
 * 创建时间：2016/10/19 16:32
 * 修改人：12078
 * 修改时间：2016/10/19 16:32
 * 修改备注：
 */

public class PersonalCheckActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView mTvTitle;
    private ImageButton mBtnBack;
    private LinearLayout mLlCheck;

    //-------------------------------------检查更新------------------------------------

    protected static final String TAG = "PersonalCheckActivity";
    /**
     * 更新新版本的状态码
     */
    protected static final int UPDATE_VERSION = 100;

    /**
     * 进入应用程序主界面的状态码
     */
    protected static final int NEW_VERSIONCODE = 101;

    /**
     * url地址出错的状态码
     */
    protected static final int URL_ERROR = 102;

    /**
     * io异常的状态码
     */
    protected static final int IO_ERROR = 103;

    /**
     * json解析异常的状态码
     */
    protected static final int JSON_ERROR = 104;

    private String mVersionDes;

    private String mDownloadUrl;

    private int mLocalVersionCode;

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case UPDATE_VERSION:
                    // 弹出对话框，提示用户更新
                    showUpdateDialog();
                    break;
                case NEW_VERSIONCODE:
                    // 提示用户已经是最新版本
                    showNewsDialog();
                    break;
                case URL_ERROR:
                    ToastUtil.show(PersonalCheckActivity.this, "url异常");

                    break;
                case IO_ERROR:
                    ToastUtil.show(PersonalCheckActivity.this, "读取异常");

                    break;
                case JSON_ERROR:
                    ToastUtil.show(PersonalCheckActivity.this, "json解析异常");

                    break;

            }

        }
    };



    //-------------------------------------检查更新完毕--------------------------------------
    public PersonalCheckActivity() {
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_check);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mBtnBack = (ImageButton) findViewById(R.id.btn_back);
        mLlCheck = (LinearLayout) findViewById(R.id.ll_check);
        mLocalVersionCode = getVersionCode();


        mLlCheck.setOnClickListener(this);
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
        mTvTitle.setText("检查更新");
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
            case R.id.ll_check:
                //设置内容
                checkedVersion();

                break;
        }
    }

    //--------------------------------------检查更新的方法--------------------------------
    /**
     * 检测版本号
     */
    private void checkedVersion() {

        new Thread() {

            public void run() {
                // 发送请求获取数据，参数为请求json的链接地址
                // http://192.168.1.102:8080/update.json 测试阶段不是最优
                // 仅限于模拟器访问电脑tomcat
                Message msg = Message.obtain();
                long startTime = System.currentTimeMillis();
                try {
                    // 1.封装url地址
                    URL url = new URL("http://192.168.1.119:8080/version.json");
                    // 2.开启一个链接
                    HttpURLConnection connection = (HttpURLConnection) url
                            .openConnection();
                    // 3.设置常见的请求参数(请求头)
                    // 请求超时
                    connection.setConnectTimeout(2000);
                    // 读取超时
                    connection.setReadTimeout(2000);
                    // 默认就是get请求方式
                    // connection.setRequestMethod("");
                    // 4.获取响应码
                    int responseCode = connection.getResponseCode();
                    if (responseCode == 200) {
                        // 5.以流的型式，把数据读取下来
                        InputStream is = connection.getInputStream();
                        // 6.将流转换成字符串
                        String json = StreamUtil.stream2String(is);
                        Log.i(TAG, json);
                        // 7.json的解析
                        JSONObject jsonObject = new JSONObject(json);
                        String versionName = jsonObject.getString("versionName");
                        mVersionDes = jsonObject.getString("versionDes");
                        String versionCode = jsonObject.getString("versionCode");
                        mDownloadUrl = jsonObject.getString("downloadUrl");

                        Log.i(TAG, versionName);
                        Log.i(TAG, mVersionDes);
                        Log.i(TAG, versionCode);
                        Log.i(TAG, mDownloadUrl);

                        // 8.比对版本号(服务器版本号》本地版本号，提示更新)
                        if (mLocalVersionCode < Integer.parseInt(versionCode)) {
                            // 提示用户更新
                            msg.what = UPDATE_VERSION;
                        } else if (mLocalVersionCode == Integer.parseInt(versionCode)){
                            // 已经是最新版本
                            msg.what = NEW_VERSIONCODE;

                        }
                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    msg.what = URL_ERROR;
                } catch (IOException e) {
                    e.printStackTrace();
                    msg.what = IO_ERROR;
                } catch (JSONException e) {
                    e.printStackTrace();
                    msg.what = JSON_ERROR;
                } finally {
                    // 指定睡眠，请求网络的时长超过4秒则不做处理
                    // 请求网络的时长小于4秒，强制让其睡眠满4秒钟
                    long endTime = System.currentTimeMillis();
                    long time = endTime - startTime;
                    if (time < 4000) {
                        try {
                            Thread.sleep(4000 - time);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    mHandler.sendMessage(msg);
                }
            }
        }.start();
    }

    /**
     * 弹出对话框，提示用户更新
     */
    protected void showUpdateDialog() {
        // 对话框是依赖于activity存在的，需要绑定界面，getApplicationContext()所对应的的activity是未知的，所以必须要用this
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // 设置左上角的图标
        builder.setIcon(R.mipmap.ic_launcher);
        // 设置标题
        builder.setTitle("版本更新");
        // 设置描述内容
        builder.setMessage(mVersionDes);

        // 积极按钮，立即更新
        builder.setPositiveButton("立即更新", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 下载apk
                downloadApk();
                dialog.dismiss();
            }
        });

        // 消极按钮，稍后再说
        builder.setNegativeButton("稍后再说", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        // 点击取消事件监听
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {

            @Override
            public void onCancel(DialogInterface dialog) {
                dialog.dismiss();
            }
        });

        builder.create().show();
    }


    /**
     *@data 创建时间：2016/10/25
     *@Description 提示用户已经是最新版本
     *@param
     *@retun
     *
     */
    private void showNewsDialog() {
        AlertDialog.Builder newBuilder = new AlertDialog.Builder(this);
        newBuilder.setTitle("检查更新");
        newBuilder.setMessage("已是最新版本~当前版本："+getVersionName()+"/"+getVersionCode());
        //为AlertDialog.Builder添加确定按钮
        newBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                return;
            }
        });
    }

    /**
     * 下载apk的方法
     */
    protected void downloadApk() {
        // apk下载链接地址，放置apk的所在路径
        // 1.判断sdcard是否可用，是否挂载上
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            // 2.获取sd卡的路径
            String path = Environment.getExternalStorageDirectory()
                    .getAbsolutePath() + File.separator + "MEMS_2000.apk";
            // 3.发送请求，获取apk，并放置到指定路径
            HttpUtils httpUtils = new HttpUtils();
            // 4.发送请求，传递参数(下载地址，下载应用放置的位置)
            httpUtils.download(mDownloadUrl, path, new RequestCallBack<File>() {

                @Override
                public void onSuccess(ResponseInfo<File> responseInfo) {
                    // 下载成功
                    Log.i(TAG, "下载成功");
                    File file = responseInfo.result;
                    // 提示用户安装
                    installApk(file);
                }

                @Override
                public void onFailure(HttpException arg0, String arg1) {
                    // 下载失败
                    Log.i(TAG, "下载失败");
                }

                // 刚刚开始下载的方法
                @Override
                public void onStart() {
                    Log.i(TAG, "刚刚开始下载");
                    super.onStart();
                }

                // 下载过程中的方法(下载apk的总大小，当前下载的位置，是否正在下载)
                @Override
                public void onLoading(long total, long current,
                                      boolean isUploading) {
                    Log.i(TAG, "下载中...");
                    Log.i(TAG, "total:" + total);
                    Log.i(TAG, "current:" + current);
                    super.onLoading(total, current, isUploading);
                }
            });
        }

    }

    /**
     * 安装对应apk的方法
     *
     * @param file
     *            安装文件
     */
    protected void installApk(File file) {
        // 系统应用界面，系统的源码，安装入口
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
		/*
		 * //文件作为数据源 intent.setData(Uri.fromFile(file)); //设置安装的类型
		 * intent.setType("application/vnd.android.package-archive");
		 */
        intent.setDataAndType(Uri.fromFile(file),
                "application/vnd.android.package-archive");
        startActivityForResult(intent, 0);
		/*
		 * 升级 注意事项： 1.要将原有应用覆盖，包名要一致 2.签名要一致
		 * 从Eclipse运行到手机上的应用，使用的是bin目录下的应用，使用debug.keystore签名应用
		 * 手机卫士版本1，右键运行至手机，所以使用的签名文件是debug.keystore
		 * 手机卫士版本2，是单独打包，生成签名文件，所以签名文件是heima74keystore
		 * 生成一个heima74keystore为签名文件的apk
		 */
    }

    /**
     * 获取应用版本号
     *
     * @return 非0代表获取成功
     */
    private int getVersionCode() {
        // 1.包管理者对象PackageManager
        PackageManager pm = getPackageManager();
        // 2.从包管理者对象中获取指定包名的基本信息(版本名称，版本号)，传0代表获取基本信息
        try {
            PackageInfo packageInfo = pm.getPackageInfo(getPackageName(), 0);
            // 3.获取版本名称
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
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


    //--------------------------------------检查更新的方法结束--------------------------------
}
