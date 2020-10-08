package com.example.roy.recycleviewtest.activity;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.Date;
import java.util.List;

import butterknife.BindView;

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

    private static final int OTHERS_FOUND = 0x0001;

    private static List<HoursPlan> mList;
    private static StudyPlanRViewAdapter mAdapter;
    private static int countOfHoursPlanList; // HoursPlan表中数据条数

    @SuppressLint("HandlerLeak")
    static Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case OTHERS_FOUND:
                    mAdapter.notifyDataSetChanged();
                    break;
                default:
                    break;

            }
        }
    };

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.study_fab) {
            LogUtil.v("onClick", "fab click");
            if (mList.isEmpty())
                addOneTimeData(1);
            else if (new Date().getDay() != mList.get(mList.size() - 1).getTime().getDay())
                addOneTimeData(1);
            else {
                LogUtil.i("今日已打卡");
            }
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.study_activity;
    }

    @Override
    protected void initView() {
        setSupportActionBar(toolbar);
        toolbar.setTitle("时光如梭");
        mList = new ArrayList<>();
        emptyView = findViewById(R.id.layout_empty_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(StudyActivity.this, RecyclerView.VERTICAL, true));
        mAdapter = new StudyPlanRViewAdapter(mList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setEmptyView(emptyView);
    }

    @Override
    protected void initData() {
        countOfHoursPlanList = LitePal.count(HoursPlan.class);
        if (countOfHoursPlanList > 20) {
            findPartData();
        } else {
            mList.addAll(LitePal.findAll(HoursPlan.class));
            mAdapter.notifyDataSetChanged();
        }
        LogUtil.i("LitePal.findAll:"+Arrays.toString(mList.toArray()));
    }

    @Override
    protected void initEvent() {
        studyFab.setOnClickListener(this);
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
                deleteAll();
                return true;
            case R.id.menu_close:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
//        return true;
    }


    // 分段读取数据库数据
    private void findPartData() {
        mList.addAll(LitePal.limit(20).find(HoursPlan.class));
        mAdapter.notifyDataSetChanged();
        findOthersDataAsync();
    }

    /* 加载剩余代码 */
    private static void findOthersDataAsync() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mList.clear();
                mList.addAll(LitePal.findAll(HoursPlan.class));
                mHandler.sendEmptyMessage(OTHERS_FOUND);
            }
        }).start();
    }

    @Override
    protected int setStatusBarColor() {
        return R.color.colorAccent;
    }

    /*  写入测试，100条数据 */
    public void test100Times(){
    for (int i=0;i<=100;i++){
    addOneTimeData(2);
    }
    }

        /*  删除所有数据 */
    private void deleteAll() {
        LitePal.deleteAll(HoursPlan.class);
        mList.clear();
        Toast.makeText(this,"clean all",Toast.LENGTH_SHORT).show();
        mAdapter.notifyDataSetChanged();
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
        int pastTime = mList.size();
        hoursPlan.setResidualTime(2000 - n - pastTime);
        hoursPlan.setTime(new Date());
        hoursPlan.save();
        LogUtil.i("addOneTimeData");
        if (hoursPlan.isSaved()) {
            LogUtil.i("isSaved!");
            mList.add(hoursPlan);
            mAdapter.notifyItemInserted(mList.size());  // 局部刷新
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        }
    }

    @Override
    public void setTheme(int resId) {
        super.setTheme(resId);
    }
}
