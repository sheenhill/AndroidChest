package com.sheenhill.lotterydemo;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.graphics.Point;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;


import com.sheenhill.common.util.LogUtil;
import com.sheenhill.lotterydemo.databinding.FragmentCrawlerBinding;

public class CrawlerFragment extends Fragment {
    private JCrawlerViewModel vm;
    private FragmentCrawlerBinding binding;
    private LotteryAdapter adapter;
    private LotteryDLTAdapter adapterDLT;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_crawler, container, false);
        vm = new ViewModelProvider(this).get(JCrawlerViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setActivity(getActivity());
        binding.rvSsq.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.rvDlt.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new LotteryAdapter();
        adapterDLT = new LotteryDLTAdapter();
        binding.setAdapter(adapter);
        binding.setAdapterDLT(adapterDLT);
        binding.setViewModel(vm);
        binding.setListener(new Listener());
        initEvent(vm);
        final int distance = 160000*100;
        final float scale = getResources().getDisplayMetrics().density * distance;
        binding.rvDlt.setCameraDistance(scale); //设置镜头距离
        binding.rvSsq.setCameraDistance(scale); //设置镜头距离


        //获取屏幕宽高
        Point size = new Point();
//        DisplayMetrics metric = new DisplayMetrics();
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        display.getSize(size);
        LogUtil.i("当前fragment宽高："+binding.getRoot().getWidth()+","+binding.getRoot().getHeight());
        LogUtil.i("当前fragment   size宽高："+size.x+","+size.y);
//        binding.rvDlt.setTranslationX(-(size.x/2));
//        binding.rvSsq.setTranslationX(-(size.x/2));
//        binding.rvDlt.res
        return binding.getRoot();
    }

    private void initEvent(JCrawlerViewModel vm) {
        // 获取双色球数据
        vm.crawlerSsq(getContext(), "http://datachart.500.com/ssq/history/history.shtml");
        // 获取大乐透数据
        vm.crawlerDLT(getContext(), "http://datachart.500.com/dlt/history/history.shtml");

        vm.getPageInfoType().observe(this,bool->{
            AnimatorSet in = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(),
                    R.animator.card_flip_in);
            AnimatorSet out = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(),
                    R.animator.card_flip_out);
            if (bool == JCrawlerViewModel.DLT) {
                in.setTarget(binding.rvDlt);
                out.setTarget(binding.rvSsq);
            } else {
                in.setTarget(binding.rvSsq);
                out.setTarget(binding.rvDlt);
            }
            out.start();
            in.start();
        });
    }

    public class Listener {
        public void convert() {
            LogUtil.i("click convert");
            vm.getPageInfoType().setValue(!(vm.getPageInfoType().getValue()));
        }

        public void editLuckNum() {
            new LuckNumDialog().show(getFragmentManager(), "luck_number");
        }
    }

}

//public class CrawlerFragment extends BaseJPFragment {
//    private JCrawlerViewModel vm;
//
//    @Override
//    protected void initViewModel() {
//        vm=getFragmentViewModel(JCrawlerViewModel.class);
//    }
//
//    @Override
//    protected DataBindingConfig getDataBindingConfig() {
//        return new DataBindingConfig(R.layout.fragment_crawler, vm)
//                .addBindingParam(BR.adapter,new LotteryAdapter())
//                .addBindingParam(BR.listener,new Listener())
//                .addBindingParam(BR.activity, getActivity());
//    }
//
//    public class Listener{
//        public void crawlerSSQ(Context context, JCrawlerViewModel vm){
//            vm.crawler(context,"http://datachart.500.com/ssq/history/history.shtml");
//        }
//    }
//}