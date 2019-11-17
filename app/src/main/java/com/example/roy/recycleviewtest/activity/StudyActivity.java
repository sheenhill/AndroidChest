package com.example.roy.recycleviewtest.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roy.recycleviewtest.R;
import com.example.roy.recycleviewtest.adapter.StudyPlanRViewAdapter;
import com.example.roy.recycleviewtest.base.BaseActivity;
import com.example.roy.recycleviewtest.entity.HoursPlan;
import com.example.roy.recycleviewtest.util.LogUtil;
import com.example.roy.recycleviewtest.view.EmptyRecyclerView;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StudyActivity extends BaseActivity implements View.OnClickListener {

    View emptyView;
    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.recyclerView)
    EmptyRecyclerView mRecyclerView;
    @BindView(R.id.iv_empty)
    ImageView ivEmpty;
    @BindView(R.id.tv_empty)
    TextView tvEmpty;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;
    @BindView(R.id.study_fab)
    FloatingActionButton studyFab;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;

    private static final int STUDY_PLAN_PART_FOUND=1;
    private static final int STUDY_PLAN_LEFTOVER_FOUND=2;

    private static List<HoursPlan> mList;
    private static StudyPlanRViewAdapter mAdapter;
    private static int countOfHoursPlanList; // HoursPlan表中数据条数
    private String data;
    private String aPastTime;
    private int counts;

    @SuppressLint("HandlerLeak")
    static Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case STUDY_PLAN_PART_FOUND:
                    mAdapter.notifyDataSetChanged();
                    AsyncAddStudyPlanFromLocalDB();
                    break;
                case STUDY_PLAN_LEFTOVER_FOUND:
//                    mAdapter.notifyItemRangeInserted(20,countOfHoursPlanList-20);
                    mAdapter.notifyItemInserted(mList.size());
//                    mAdapter.notifyDataSetChanged();
                    break;
                default:
                    break;

            }
        }
    };

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.study_fab) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                LogUtil.v("onClick","fab click");
            addOneTimeData(1);
        }
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.study_activity);
    }

    /**
     * 以下俩个是toolbar中的选项
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
//            case R.id.menu_show:
//                return true;
            case R.id.menu_close:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
//        return true;
    }

    @Override
    protected void initData() {
        mList = new ArrayList<>();
        countOfHoursPlanList=LitePal.count(HoursPlan.class);

        if(countOfHoursPlanList>20){
            AsyncfindStudyListFromLocalDB();
        }else{
            mList.addAll(LitePal.findAll(HoursPlan.class));
            Collections.reverse(mList);
        }

        LogUtil.i("LitePal.findAll");
//        Intent i = getIntent();
//        data = i.getStringExtra("study_plan");
    }

    // 分段读取数据库数据
    private void AsyncfindStudyListFromLocalDB() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mList.clear();
                    mList.addAll(LitePal.limit(20).offset(countOfHoursPlanList-20).find(HoursPlan.class));
                    Collections.reverse(mList);
//                    HoursPlan hp=new HoursPlan();
//                    for(int i=0;i<countOfHoursPlanList-20;i++){
//                        mList.add(hp);
//                    }
                    mHandler.sendEmptyMessage(STUDY_PLAN_PART_FOUND);
            }
        }).start();
    }
    private static void AsyncAddStudyPlanFromLocalDB() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<HoursPlan> list=new ArrayList<>();
                list.addAll(LitePal.limit(countOfHoursPlanList-20).offset(0).find(HoursPlan.class));
                Collections.reverse(list);
                int i;
                for(i=0;i<list.size();i++){
                    mList.add(list.get(i));
//                            mList.addAll(list);
//                            LogUtil.i("mList.size():"+mList.size());
                    mHandler.sendEmptyMessage(STUDY_PLAN_LEFTOVER_FOUND);
                }
            }
        }).start();
    }

    @Override
    protected int setStatusBarColor() {
        return R.color.colorAccent;
    }

    @Override
    protected void initView() {
//        initSystemBarTint();
        setSupportActionBar(toolbar);
//        toolbar.setTitle(data);
        toolbar.setTitle("时光如梭");

        emptyView = findViewById(R.id.layout_empty_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(StudyActivity.this, RecyclerView.VERTICAL, false));
        mAdapter = new StudyPlanRViewAdapter(mList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setEmptyView(emptyView);
//        //不将数据copy，再进行数据刷新，会导致recyclerView无法显示本次登录的数据
//        mHourPlanList.clear();
//        mHourPlanList.addAll(mList);
//        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void initEvent() {
        studyFab.setOnClickListener(this);
    }

//    @Override
//    public void onBackPressed() {
//        Intent intent = new Intent();
//        intent.putExtra("study_time_return", aPastTime);
//        setResult(RESULT_OK, intent);
//        LogUtil.w(aPastTime);
//        finish();
//    }

    /**
     * 刷新整个页面
     */
    private void refresh() {
//        finish();
        Intent intent = new Intent(this, StudyActivity.class);
        startActivity(intent);
    }

    /**
     * 刷新recyclerView局部数据
     */
    private void refreshData() {
        mAdapter.notifyDataSetChanged();
        LogUtil.i("refreshData");

    }

/*
/**
* 写入测试，100条数据
* /
public void test100Times(){
for (int i=0;i<=100;i++){
addOneTimeData();
}
refreshData();
}
*/

    /**
     * 删除所有数据
     */
    private void deleteAll() {
        LitePal.deleteAll(HoursPlan.class);
        refresh();
        LogUtil.i("deleteAll");
    }

    /**
     * 向数据库写入一次数据，并在recyclerView中显示出来
     *
     * @param n 本次学习时间
     */
    private void addOneTimeData(int n) {
        HoursPlan hoursPlan = new HoursPlan();
        hoursPlan.setThisStudyTime(n);
        int pastTime = LitePal.sum(HoursPlan.class, "thisStudyTime", int.class);
        hoursPlan.setResidualTime(2000 - n - pastTime);
        hoursPlan.setTime(new Date());
        hoursPlan.save();
        LogUtil.i("addOneTimeData");
        if (hoursPlan.isSaved()) {
            LogUtil.i("isSaved!");
//            int count = LitePal.count(HoursPlan.class)
            List<HoursPlan> list=new ArrayList<>();
            list.addAll(mList);
            mList.clear();
            mList.add(hoursPlan);
            mList.addAll(list);
            mAdapter.notifyItemInserted(0);
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
            aPastTime = String.valueOf(pastTime);
//        toolbar.setTitle("已学习"+count+"小时");  TODO:实时更新title数据
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
