package com.sheenhill.lotterydemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.sheenhill.common.util.LogUtil;
import com.sheenhill.lotterydemo.databinding.ActivityMainBinding;
import com.sheenhill.lotterydemo.databinding.FragmentLotteryBinding;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentLotteryBinding binding=DataBindingUtil.setContentView(this,R.layout.fragment_lottery);
        LogUtil.i(BuildConfig.LOG_TEST);
    }
}
