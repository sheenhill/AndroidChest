package com.sheenhill.rusuo.activity;

import com.sheenhill.rusuo.R;
import com.sheenhill.rusuo.base.BaseActivity;
import com.sheenhill.rusuo.view.EmptyRecyclerView;

import butterknife.BindView;

public class BingPicActivity extends BaseActivity {
    private static final String BINGPIC_URL = "https://cn.bing.com/HPImageArchive.aspx?format=js&idx=0&n=12&mkt=zh-CN";
    @BindView(R.id.rv_bing_pic)
    EmptyRecyclerView rvBingPic;


    @Override
    protected int getLayoutId() {
        return R.layout.bing_pic_activity;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {

    }

}