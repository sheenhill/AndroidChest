package com.sheenhill.demo_lottery.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.sheenhill.demo_lottery.R;
import com.sheenhill.demo_lottery.databinding.FragmentLotteryBinding;

public class J_LotteryFragment extends Fragment {
    FragmentLotteryBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_lottery, container, false);
        return binding.getRoot();
    }
}
