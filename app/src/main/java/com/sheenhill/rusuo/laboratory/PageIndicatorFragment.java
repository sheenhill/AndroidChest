package com.sheenhill.rusuo.laboratory;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.sheenhill.rusuo.R;
import com.sheenhill.rusuo.databinding.ActivityCustomViewBinding;
import com.sheenhill.rusuo.util.LogUtil;


public class PageIndicatorFragment extends Fragment {

    private ActivityCustomViewBinding binding;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    private com.stanza.pageindicator.PagerIndicatorFragment pFragment1;
    private com.stanza.pageindicator.PagerIndicatorFragment pFragment2;
    private com.stanza.pageindicator.PagerIndicatorFragment pFragment3;
    private com.stanza.pageindicator.PagerIndicatorFragment pFragment4;
    private com.stanza.pageindicator.PagerIndicatorFragment pFragment5;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.activity_custom_view, container, false);
        initView();
        initData();
        initEvent();
        return binding.getRoot();
    }


    private void initView() {
        fragmentManager = getParentFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        pFragment1 = new com.stanza.pageindicator.PagerIndicatorFragment(0);
        pFragment2 = new com.stanza.pageindicator.PagerIndicatorFragment(1);
        pFragment3 = new com.stanza.pageindicator.PagerIndicatorFragment(2);
        pFragment4 = new com.stanza.pageindicator.PagerIndicatorFragment(3);
        pFragment5 = new com.stanza.pageindicator.PagerIndicatorFragment(50);
        fragmentTransaction.add(R.id.f1, pFragment1);
        fragmentTransaction.add(R.id.f2, pFragment2);
        fragmentTransaction.add(R.id.f3, pFragment3);
        fragmentTransaction.add(R.id.f4, pFragment4);
        fragmentTransaction.add(R.id.f5, pFragment5);
        fragmentTransaction.commit();
    }

    private void initData() {
        String s = getContext().getSharedPreferences("景色", 0)
                .getString("地址", "温州");
        binding.infoPreview.setText(s);
        LogUtil.i("CustomViewActivity.sp(景色).地址：" + s);
    }

    private void initEvent() {
        pFragment1.setFragmentCallback(num -> binding.infoPreview.setText(num));
        pFragment2.setFragmentCallback(num -> binding.infoPreview.setText(num));
        pFragment3.setFragmentCallback(num -> binding.infoPreview.setText(num));
        pFragment4.setFragmentCallback(num -> binding.infoPreview.setText(num));
        pFragment5.setFragmentCallback(num -> binding.infoPreview.setText(num));
    }

}
