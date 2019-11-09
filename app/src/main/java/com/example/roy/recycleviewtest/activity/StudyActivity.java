package com.example.roy.recycleviewtest.activity;

import android.content.Intent;
import android.os.Bundle;
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
import com.example.roy.recycleviewtest.adapter.MyRecyclerViewAdapter;
import com.example.roy.recycleviewtest.base.BaseActivity;
import com.example.roy.recycleviewtest.entity.HoursPlan;
import com.example.roy.recycleviewtest.util.LogUtil;
import com.example.roy.recycleviewtest.view.EmptyRecyclerView;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.litepal.LitePal;

import java.util.ArrayList;
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


    private List<HoursPlan> mList;
    MyRecyclerViewAdapter mAdapter;
    private String data;
    private String aPastTime;

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.study_fab) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
//                LogUtil.v("onClick","fab click");
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
            case R.id.menu_show:
                return true;
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
        mList = LitePal.findAll(HoursPlan.class);
        LogUtil.i("LitePal.findAll");
        Intent i = getIntent();
        data = i.getStringExtra("study_plan");
    }

    @Override
    protected void initView() {
        initSystemBarTint();
        setSupportActionBar(toolbar);
        toolbar.setTitle(data);
        emptyView = findViewById(R.id.layout_empty_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(StudyActivity.this, RecyclerView.VERTICAL, true));
        mAdapter = new MyRecyclerViewAdapter(mList);
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

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("study_time_return", aPastTime);
        setResult(RESULT_OK, intent);
        LogUtil.w(aPastTime);
        finish();
    }

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
            int count = LitePal.count(HoursPlan.class);
            if (mList == null)
                LogUtil.i("mList为空");
            mList.add(hoursPlan);
            mAdapter.notifyItemInserted(count);
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
