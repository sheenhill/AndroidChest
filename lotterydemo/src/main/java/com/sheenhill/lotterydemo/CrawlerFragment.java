package com.sheenhill.lotterydemo;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.graphics.Point;
import android.os.Bundle;
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
import androidx.viewpager2.widget.ViewPager2;


import com.sheenhill.common.util.LogUtil;
import com.sheenhill.lotterydemo.databinding.FragmentCrawlerBinding;

public class CrawlerFragment extends Fragment {
    private JCrawlerViewModel vm;
    private FragmentCrawlerBinding binding;
    private LotterySSQAdapter adapterSSQ;
    private LotteryDLTAdapter adapterDLT;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_crawler, container, false);
        vm = new ViewModelProvider(this).get(JCrawlerViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setActivity(getActivity());
        final LotteryVPAdapter lotteryAdapter = new LotteryVPAdapter(this);
        binding.vpLottery.setAdapter(lotteryAdapter);
        binding.vpLottery.setPageTransformer(vpAnimotion);


//        adapterSSQ = new LotterySSQAdapter();
//        adapterDLT = new LotteryDLTAdapter();
//        binding.setAdapterLuckyNum(new LuckyNumAdapter());
//        binding.setAdapterDLT(adapterDLT);
//        binding.setAdapterSSQ(adapterSSQ);
//        binding.setViewModel(vm);
//        binding.setListener(new Listener());
//        initEvent(vm);
        // 使用相机翻转卡片动效
//        final int distance = 160000*100;
//        final float scale = getResources().getDisplayMetrics().density * distance;
//        binding.rvDlt.setCameraDistance(scale); //设置镜头距离
//        binding.rvSsq.setCameraDistance(scale); //设置镜头距离
//
//
//        //获取屏幕宽高
//        Point size = new Point();
////        DisplayMetrics metric = new DisplayMetrics();
//        Display display = getActivity().getWindowManager().getDefaultDisplay();
//        display.getSize(size);
//        LogUtil.i("当前fragment宽高："+binding.getRoot().getWidth()+","+binding.getRoot().getHeight());
//        LogUtil.i("当前fragment   size宽高："+size.x+","+size.y);
////        binding.rvDlt.setTranslationX(-(size.x/2));
////        binding.rvSsq.setTranslationX(-(size.x/2));
////        binding.rvDlt.res
////        binding.vpLottery.setAdapter(new Fr);
        return binding.getRoot();
    }

    private void initEvent(JCrawlerViewModel vm) {
        // 获取双色球数据
        vm.crawlerSsq(getContext(), "http://datachart.500.com/ssq/history/history.shtml");
        // 获取大乐透数据
        vm.crawlerDLT(getContext(), "http://datachart.500.com/dlt/history/history.shtml");

        vm.getPageInfoType().observe(getViewLifecycleOwner(), bool -> {
            AnimatorSet in = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(),
                    R.animator.card_flip_in);
            AnimatorSet out = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(),
                    R.animator.card_flip_out);
            if (bool == JCrawlerViewModel.DLT) {
                in.setTarget(binding.rvDlt);
                out.setTarget(binding.rvSsq);
                out.start();
                binding.rvSsq.setVisibility(View.GONE);
                binding.rvDlt.setVisibility(View.VISIBLE);
                in.start();
            } else {
                in.setTarget(binding.rvSsq);
                out.setTarget(binding.rvDlt);
                out.start();
                binding.rvDlt.setVisibility(View.GONE);
                binding.rvSsq.setVisibility(View.VISIBLE);
                in.start();
            }
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

    /**
     * 右滑：1.0(新) -> 0.0（新，旧） -> -1.0（旧）
     * 左滑：-1.0(新) -> 0.0（新，旧） -> 1.0（旧）
     */
    private ViewPager2.PageTransformer vpAnimotion = (page, position) -> {
        // 很舒服的方块滑屏动效
//        page.setPivotX(position < 0f ? page.getWidth() : 0f);
//        page.setPivotY(page.getHeight() * 0.5f);
//        page.setRotationY(position*45f);


        // 使用相机实现卡片翻转动效
//        final int distance = 160000 ;
//        final float scale = getResources().getDisplayMetrics().density * distance;
//        page.setCameraDistance(scale); //设置镜头距离
//        page.setRotationY(180f * position);

//        page.setTranslationY(page.getHeight() * 0.5f);// 位移
//        page.setTranslationX(page.getWidth() * 0.5f);
        page.setAlpha((position > -0.5f && position < 0.5f) ? 1 : 0);
//        page.setTranslationX(page.getWidth() * -position);
        page.setPivotX((1.0f - Math.abs(position))*page.getWidth());// 水平偏转量  貌似有初始偏转量
//        page.setPivotX(page.getWidth() * 0.5f);// 水平偏转量

        page.setScaleX(1.0f - Math.abs(position));  // 参数是比例
//        page.setScaleX((1-Math.abs(position))*page.getWidth());


        // 卡片上下替换动效
//        final float MAX_SCALE = 1.0f;
//        final float MIN_SCALE = 0.85f;
//        //设置了内间距  有0.15的偏差
//        float pos = position - 0.15f;
//        if (pos <= 1) {
//            float scaleFactor = MIN_SCALE + (1 - Math.abs(pos)) * (MAX_SCALE - MIN_SCALE);
//            page.setScaleX(scaleFactor); //缩放效果
//            if (pos > 0) {
//                page.setTranslationX(-scaleFactor * 2);
//            } else if (pos < 0 && pos > -1) {
//                page.setTranslationX(scaleFactor * 2);
//            }
//            page.setScaleY(scaleFactor);
//        } else {
//            page.setScaleX(MIN_SCALE);
//            page.setScaleY(MIN_SCALE);
//        }

    };

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