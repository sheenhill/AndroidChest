package com.sheenhill.demo_lottery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.sheenhill.common.util.LogUtil;
import com.sheenhill.demo_lottery.databinding.ActivityMainBinding;
import com.sheenhill.demo_lottery.databinding.FragmentLotteryBinding;

/* 单独为app时，入口是这个activity */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataBindingUtil.setContentView(this,R.layout.fragment_lottery);
        LogUtil.i(BuildConfig.LOG_TEST);
    }
}
