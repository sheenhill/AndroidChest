package com.sheenhill.rusuo;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MenuItem;

import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.sheenhill.rusuo.adapter.TabFragmentAdapter;
import com.sheenhill.rusuo.base.BaseActivity;
import com.sheenhill.rusuo.util.ToastUtils;
import com.google.android.material.tabs.TabLayout;
import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;



public class MainActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.guide_view_pager)
    ViewPager guideViewPager;
    @BindView(R.id.toolbar_layout)
    TabLayout toolbarLayout;

    private int count;
    //    private StringBuffer sb;
//    private String reData = null;
    private IntentFilter intentFilter;
    private MyHandler myHandler;

    private NetworkChangeReceiver networkChangeReceiver;
    private TabFragmentAdapter adapter;
    //    private String[] titles = {"视界", "器物"};
    private String[] titles = {"", ""};
//    private static final int GET_PIC = 1;
//    private static final int GET_SSQ = 2;

    private static class MyHandler extends Handler{
        WeakReference<MainActivity> myHandlerWeakReference;
        Context mContext;
        MyHandler(MainActivity activity){
            mContext=activity;
            myHandlerWeakReference=new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(myHandlerWeakReference.get()!=null){
                switch (msg.what){
                    default:break;
                }

            }
        }
    }


//    @SuppressLint("MissingSuperCall")
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        switch (requestCode) {
//            case 5:
//                if (resultCode == RESULT_OK) {
//                    reData = data.getStringExtra("study_time_return");
//                    LogUtil.w(reData);
//                }
//                break;
//            default:
//        }
//    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initEvent() {
//        intentFilter = new IntentFilter();
//        intentFilter.addAction("com.example.broadcasttest.LOCAL_BROADCAST");

    }

    @Override
    protected void initView() {
        setSupportActionBar(toolbar);
//        toolbar.setTitle("如梭  " + sb); // todo
        toolbar.setTitle("如梭");
        initViewPager();
        initTabLayout();
    }

    @Override
    protected int setStatusBarColor() {
        return R.color.colorAccent;
    }


    private void initViewPager() {
        adapter = new TabFragmentAdapter(getSupportFragmentManager(), titles);
//        adapter = new TabFragmentAdapter(getSupportFragmentManager(), "");
        guideViewPager.setAdapter(adapter);
    }

    private void initTabLayout() {
        toolbarLayout.setupWithViewPager(guideViewPager, false);
        final TabLayout.Tab tab1 = toolbarLayout.getTabAt(0);
        final TabLayout.Tab tab2 = toolbarLayout.getTabAt(1);
        tab1.setIcon(getResources().getDrawable(R.mipmap.pic,null));
        tab2.setIcon(getResources().getDrawable(R.mipmap.toolbox,null));
    }

    @Override
    protected void initData() {
        myHandler=new MyHandler(this);
//        sb = new StringBuffer("已学习");
//        count = LitePal.sum(HoursPlan.class, "thisStudyTime", int.class);
//        sb.append(count);
//        sb.append("小时");
//        localBroadcastManager = LocalBroadcastManager.getInstance(this);

        String[] s = new String[]{Manifest.permission.INTERNET, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, s, 1);
        }
//        girlPicUrlList = new ArrayList<>();
//        getBeauty();
        intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        networkChangeReceiver = new NetworkChangeReceiver();
        registerReceiver(networkChangeReceiver, intentFilter);


//        tvMotto01.setText(yuce); TODO
    }



    @Override
    protected void onStart() {
        super.onStart();
//        if (reData != null) {
//            sb = new StringBuffer("已学习");
//            sb.append(reData);
//            sb.append("小时");
//            toolbar.setTitle(sb);
//            LogUtil.w(reData + "wtf");
//        }
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.menu_show:
                return true;
            case R.id.menu_close:
                finish();
                return true;
//            case R.id.action_one:
//                //隐式intent
//                Intent intent = new Intent("com.example.recycleviewtest.STUDY_START");
//                intent.addCategory("com.example.recycleviewtest.STUDY_ACTIVITY");
//                intent.putExtra("study_plan", (Serializable) sb);
//                //按照Alibaba android开发手册所规范：
//                //Activity 间通过隐式 Intent 的跳转，在发出 Intent 之前必须通过 resolveActivity 检查，
//                //避免找不到合适的调用组件，造成 ActivityNotFoundException 的异常。
//                if (getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY) != null)
//                    startActivityForResult(intent, 5);
//                else
//                    ToastUtils.showShort(this, "该功能暂时未开通");
//                return true;
//            case R.id.action_two:
//                Intent intent1 = new Intent(MainActivity.this, CalculatorActivity.class);
////                intent1.putExtra("tool_title", tvTwo.getText());
//                startActivity(intent1);
//                return true;
////            case R.id.action_three:
////                //静态方法，直接用类使用方法
////                BubbleActivity.actionStart(this, BubbleActivity.class);
////                return true;
//            case R.id.action_four:
//                TensorFlowLiteActivity.actionStart(this, TensorFlowLiteActivity.class);
//                return true;
//            case R.id.action_five:
//                Intent intent3 = new Intent("com.example.broadcasttest.LOCAL_BROADCAST");//本地广播
////                localBroadcastManager.sendBroadcast(intent3);
//                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(networkChangeReceiver);
    }

    class NetworkChangeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isAvailable()) {
//                getBeauty();
//                LogUtil.i("有网了！girlPicUrlList:" + girlPicUrlList);
//                getShuangseqiu();
//                ToastUtils.showShort(context, "network available");
            } else
                ToastUtils.showShort(context, "network unavailable");
        }
    }


}
