package com.sheenhill.rusuo.laboratory;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.sheenhill.rusuo.R;
import com.sheenhill.rusuo.databinding.FragmentCountdownBinding;
import com.sheenhill.rusuo.util.LogUtil;

import java.util.Calendar;
import java.util.Date;


public class CountdownFragment extends Fragment {
    private static final long TIME = 60 * 60 * 1000;
    private static final short SIXTY = 60;
    private static final short SECOND = 1000;
    private FragmentCountdownBinding binding;
    private CountDownTimer countDownTimer;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_countdown, container, false);
        // TODO: 2020/7/1  使用线程池开个独立线程，获取当前时间和下班时间的时差进行显示，每隔1s显示一次，用handler通知
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        Calendar calendar = Calendar.getInstance();
        final long nowMills = calendar.getTimeInMillis();
        final int hour = calendar.get(Calendar.HOUR_OF_DAY);
        final int minutes = calendar.get(Calendar.MINUTE);
        final int second = calendar.get(Calendar.SECOND);
        LogUtil.e(hour + ":" + minutes + ":" + second);
        LogUtil.e("now:" + new Date(nowMills));
        calendar.set(Calendar.HOUR_OF_DAY, 17);
        calendar.set(Calendar.MINUTE, 30);
        calendar.set(Calendar.SECOND, 00);

        final long targetMills = calendar.getTimeInMillis();
        LogUtil.e("target:" + new Date(targetMills));
        if (hour < 17 && hour > 8 || minutes < 30) {
            countDownTimer = new CountDownTimer(targetMills - nowMills, SECOND) {
                @SuppressLint({"SetTextI18n", "SimpleDateFormat"})
                @Override
                public void onTick(long millisUntilFinished) {
                    final int h = (int) millisUntilFinished / (SIXTY * SIXTY * SECOND);
                    millisUntilFinished %= (SIXTY * SIXTY * SECOND);
                    final int m = (int) millisUntilFinished / (SIXTY * SECOND);
                    millisUntilFinished %= (SIXTY * SECOND);
                    final int s = (int) millisUntilFinished / SECOND;
                    binding.tv.setText("还有" + h + "h" + m + 'm' + s + "s");
                    LogUtil.e("还有" + h + "h" + m + 'm' + s + "s");
                }

                @Override
                public void onFinish() {

                }
            };
            countDownTimer.start();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        countDownTimer.cancel();
        countDownTimer = null;
    }
}
