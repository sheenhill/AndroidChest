package com.example.roy.recycleviewtest.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.roy.recycleviewtest.MainActivity;
import com.example.roy.recycleviewtest.R;
import com.example.roy.recycleviewtest.base.BaseActivity;
import com.example.roy.recycleviewtest.util.LogUtil;

import butterknife.BindView;

import static android.view.View.VISIBLE;


public class SplashActivity extends BaseActivity {
    public static final int MSG_UPDATE_TIME = 1;

    // 时间间隔1s
    public static final int INTERNAL_TIME = 1000;
    // 配置文件名
    public static final String CONGIF_NAME = "app_config";
    // 配置文件中的一个配置项：是否首次启动
    public static final String IS_FIRST_LOGIN = "is_first_login";

    @BindView(R.id.splash_image_view)
    ImageView splashImageView;
    @BindView(R.id.count_down_tv)
    TextView countDownTv;

    // 倒计时，初始2s
    private int mCountDownTime = 2;
    private boolean  clickedTv=false;//监听用户是否点击了中止倒计时的textview


    private Handler mHandler = new Handler(new Handler.Callback() {

        // 接收发送的消息
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_UPDATE_TIME:// 更新时间
                    countDownTv.setVisibility(VISIBLE);
                    countDownTv.setText(mCountDownTime+" s >");
                    if (mCountDownTime > 0) {
                        --mCountDownTime;
                        // 每次更新完UI界面，都会在1s后发送消息，通知继续下一次的更新
                        mHandler.sendEmptyMessageDelayed(MSG_UPDATE_TIME, INTERNAL_TIME);//handler隔一段时间发送一个简单事件
                    } else {
                        if(!clickedTv)
                        startOtherActivity();//  不在点击事件监听器中修改clickTv，会加载2个mainActivity(闪频)
                    }
                    break;
            }
            return true;
        }
    });

    @Override
    protected int getLayoutId() {
        return R.layout.splash_activity;
    }

    @Override
    protected void initView() {
        // 使用handler每隔1s发送一次消息，通知textView刷新UI界面
        mHandler.sendEmptyMessageDelayed(MSG_UPDATE_TIME, INTERNAL_TIME);
        Animation animation= AnimationUtils.loadAnimation(this,R.anim.splash_become_large);
        //动画停留在最后一帧
        animation.setFillAfter(true);
        //执行动画
        splashImageView.startAnimation(animation);
    }

    @Override
    protected void initData() {
    // TODO：权限在此activity之前
    }

    @Override
    protected void initEvent() {
        countDownTv.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                    LogUtil.i("点击了跳转");
                    clickedTv=true;
                    SplashActivity.actionStart(SplashActivity.this,MainActivity.class);
                    finish();
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {

            }
        });
    }

    // 跳转界面  暂时不用导航页了
    private void startOtherActivity() {
//        SharedPreferences sharedPreferences =
//                this.getSharedPreferences(CONGIF_NAME, Context.MODE_PRIVATE);
//        // 每次启动app都要检查是否首次启动
//        boolean isFirstLogin = sharedPreferences.getBoolean(IS_FIRST_LOGIN, true);//不存在“IS_FIRST_LOGIN”，就生产一个“IS_FIRST_LOGIN”，并返回true
        // 跳转
        Intent intent = new Intent();
//        if (isFirstLogin) {// 跳转引导页
//            intent.setClass(this, GuideActivity.class);
//            // 从此不再是首次启动
//            SharedPreferences.Editor editor = sharedPreferences.edit();
//            editor.putBoolean(IS_FIRST_LOGIN, false);
//            editor.commit();
//        } else {// 跳转主界面
            intent.setClass(this, MainActivity.class);
//        }
        startActivity(intent);
        // 关闭当前页面
        finish();
    }


}
