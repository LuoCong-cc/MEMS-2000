package mems.jiaochuan.com.mems_2000.base.impl;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import mems.jiaochuan.com.mems_2000.R;
import mems.jiaochuan.com.mems_2000.base.BasePager;
import mems.jiaochuan.com.mems_2000.ui.activity.LoginActivity;
import mems.jiaochuan.com.mems_2000.ui.activity.MainActivity;
import mems.jiaochuan.com.mems_2000.ui.activity.PersonalAboutActivity;
import mems.jiaochuan.com.mems_2000.ui.activity.PersonalCheckActivity;
import mems.jiaochuan.com.mems_2000.ui.activity.PersonalMsgActivity;
import mems.jiaochuan.com.mems_2000.utils.PrefUtils;
import mems.jiaochuan.com.mems_2000.utils.Utils;

/**
 * @author 刘建阳
 * @time 2016/8/10 14:23
 */
public class PersonalPager extends BasePager implements View.OnClickListener {

    private static final int PHOTO_REQUEST_CAMERA = 1;// 拍照

    private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择

    private static final int PHOTO_REQUEST_CUT = 3;// 结果

    private static final String PHOTO_FILE_NAME = "temp_photo.jpg";

    private ImageView mCivUserIcon;
    private TextView mTv_personal_login;
    private RelativeLayout mRlPersonalAbout,mRlPersonalCheck,mRlPersonalMsg;
    private boolean login;
    private RelativeLayout mRlPersonalExit;


    public PersonalPager(Activity activity) {
        super(activity);
        initData();
    }

    @Override
    public void initData() {
        // 要给帧布局填充布局对象

        View view = View.inflate(mActivity, R.layout.tab_personal_detail, null);

        mCivUserIcon = (ImageView) view.findViewById(R.id.civ_user_icon);
        mTv_personal_login = (TextView) view.findViewById(R.id.tv_personal_login);
        mRlPersonalMsg = (RelativeLayout) view.findViewById(R.id.rl_personal_msg);
        mRlPersonalCheck = (RelativeLayout) view.findViewById(R.id.rl_personal_check);
        mRlPersonalAbout = (RelativeLayout) view.findViewById(R.id.rl_personal_about);
        mRlPersonalExit = (RelativeLayout) view.findViewById(R.id.rl_personal_exit);
        mCivUserIcon.setOnClickListener(this);
        mTv_personal_login.setOnClickListener(this);
        mRlPersonalMsg.setOnClickListener(this);
        mRlPersonalCheck.setOnClickListener(this);
        mRlPersonalAbout.setOnClickListener(this);
        mRlPersonalExit.setOnClickListener(this);

        login = PrefUtils.getBoolean(mActivity, "login", false);
        showLogin();
        flContent.addView(view);

        tv_title.setText("个人中心");

    }
    /**
     *@data 创建时间：2016/10/19
     *@Description 判断用户是否登陆
     *@param
     *@retun
     *
     */
    private void showLogin() {
        login = PrefUtils.getBoolean(mActivity, "login", false);
        if (login){
       /* if (MainActivity.username !=null){*/
            //登陆成功
            mTv_personal_login.setText("用户名："+ MainActivity.username);
            Toast.makeText(mActivity, "登陆成功", Toast.LENGTH_SHORT).show();
            return;
        }else {
            mTv_personal_login.setText("点击登陆");
            return;
        }

    }

    @Override
    public void onClick(View view) {
        login = PrefUtils.getBoolean(mActivity, "login", false);
        switch (view.getId()) {
            case R.id.civ_user_icon:
//                createDialog();
                break;
            case R.id.tv_personal_login:
               /* if (MainActivity.username !=null){*/
                if (login){
                    Toast.makeText(mActivity, "用户已登录", Toast.LENGTH_SHORT).show();
                }else {
                    Intent loginIntent = new Intent();
                    loginIntent.setClass(mActivity, LoginActivity.class);
                    mActivity.startActivity(loginIntent);
                }

                break;
            case R.id.rl_personal_msg:
                Intent msgIntent = new Intent();
                msgIntent.setClass(mActivity, PersonalMsgActivity.class);
                mActivity.startActivity(msgIntent);
                break;
            case R.id.rl_personal_check:
                Intent checkIntent = new Intent();
                checkIntent.setClass(mActivity, PersonalCheckActivity.class);
                mActivity.startActivity(checkIntent);
                break;
            case R.id.rl_personal_about:
                Intent aboutIntent = new Intent();
                aboutIntent.setClass(mActivity, PersonalAboutActivity.class);
                mActivity.startActivity(aboutIntent);

                break;
            case R.id.rl_personal_exit:
                if (login){
                    showAlertDialog();
                }else {
                    Toast.makeText(mActivity, "用户未登录", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }
    /**
     *@data 创建时间：2016/10/25
     *@Description 用户头像弹出层
     *@param
     *@retun
     *
     */
//    private void createDialog() {
//        Holder holder = new ViewHolder(R.layout.user_icon_setting_dialog);
//        OnClickListener onClickListener = new OnClickListener() {
//            @Override
//            public void onClick(DialogPlus dialog, View view) {
//                switch (view.getId()) {
//                    case R.id.user_setting_cancel:
//                        dialog.dismiss();
//
//                        break;
//                    case R.id.user_setting_album:
//                        fromAlubm();
//                        dialog.dismiss();
//
//                        break;
//                    case R.id.user_setting_photo:
//                        fromCamera();
//                        dialog.dismiss();
//
//                        break;
//
//                }
//            }
//        };

//        DialogPlus dialogPlus = DialogPlus.newDialog(mActivity)
//                .setContentHolder(holder)
//                .setHeader(R.layout.user_setting_header)
//                .setGravity(Gravity.CENTER)
//                .setCancelable(true)
//                .setOnClickListener(onClickListener)
//                .create();
//        dialogPlus.show();
//    }
    /**
     *@data 创建时间：2016/10/25
     *@Description 判断存储卡是否可以用，可用进行存储
     *@param
     *@retun
     *
     */
    private void fromCamera() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        // 判断存储卡是否可以用，可用进行存储
        if (Utils.hasSdcard()) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT,
                    Uri.fromFile(new File(Environment
                            .getExternalStorageDirectory(), PHOTO_FILE_NAME)));
        }
        mActivity.startActivityForResult(intent, PHOTO_REQUEST_CAMERA);
    }

    /**
     *@data 创建时间：2016/10/25
     *@Description 激活系统图库选择一张图片
     *@param
     *@retun
     *
     */
    private void fromAlubm() {
        // 激活系统图库，选择一张图片
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        mActivity.startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
    }

    /**
     *@data 创建时间：2016/10/24
     *@Description 退出登录
     *@param
     *@retun
     *
     */
    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setTitle("退出登陆");
        builder.setMessage("确定退出登陆？");
        //为AlertDialog.Builder添加确定按钮
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                PrefUtils.putBoolean(mActivity,"login",false);
                MainActivity.username = "";
                mTv_personal_login.setText("点击登陆");
                Toast.makeText(mActivity, "退出登录成功", Toast.LENGTH_SHORT).show();
            }
        });
        //取消按钮
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        });
        builder.create().show();
    }


}
