package com.sheenhill.demo_lottery;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;


import com.sheenhill.common.util.LogUtil;
import com.sheenhill.demo_lottery.adapter.LotteryDLTAdapter;
import com.sheenhill.demo_lottery.adapter.LotterySSQAdapter;
import com.sheenhill.demo_lottery.adapter.LotteryVPAdapter;
import com.sheenhill.demo_lottery.adapter.LuckyNumRVAdapter;
import com.sheenhill.demo_lottery.databinding.FragmentCrawlerBinding;
import com.sheenhill.demo_lottery.fragment.PrivacyDialog;

/* 彩票主展示页 -- 爬虫页 */
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
        binding.setAdapterLuckyNum(new LuckyNumRVAdapter(vm));
//        binding.setAdapterLuckyNum(null);
        binding.setViewModel(vm);
        binding.setListener(new Listener());
        initVpModeView();
//        initShModeView();
        initEvent(vm);
        return binding.getRoot();
    }

    /* 用viewpager承载俩种彩票页面 */
    private void initVpModeView() {
        final LotteryVPAdapter lotteryAdapter = new LotteryVPAdapter(this);
        binding.vpLottery.setAdapter(lotteryAdapter);
        binding.vpLottery.setPageTransformer(vpAnimation);
//        binding.vpLottery.setPageTransformer(new MarginPageTransformer(50));
        binding.vpLottery.setOffscreenPageLimit(1);
    }

    /* 用view显示隐藏的切换俩种彩票页面 */
    private void initShModeView() {
//        adapterSSQ = new LotterySSQAdapter();
//        adapterDLT = new LotteryDLTAdapter();
//        binding.setAdapterDLT(adapterDLT);
//        binding.setAdapterSSQ(adapterSSQ);
//         // 使用相机翻转卡片动效
//        final int distance = 160000*100;
//        final float scale = getResources().getDisplayMetrics().density * distance;
//        binding.rvDlt.setCameraDistance(scale); //设置镜头距离
//        binding.rvSsq.setCameraDistance(scale); //设置镜头距离
    }

    private void initEvent(JCrawlerViewModel vm) {
        // 获取双色球数据
        vm.crawlerSsq(getContext(), "http://datachart.500.com/ssq/history/history.shtml");
        // 获取大乐透数据
        vm.crawlerDLT(getContext(), "http://datachart.500.com/dlt/history/history.shtml");

        vm.getPageInfoType().observe(getViewLifecycleOwner(), bool -> {
//            showOrHideView(bool, binding.rvSsq,binding.rvDlt);
            binding.vpLottery.setCurrentItem(bool?1:0);
        });
    }

    // 根据数据进行显示隐藏动效
    private void showOrHideView(boolean bool,View rvSsq,View rvDlt){
        AnimatorSet in = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(),
                R.animator.card_flip_in);
        AnimatorSet out = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(),
                R.animator.card_flip_out);
        if (bool == JCrawlerViewModel.DLT) {
            in.setTarget(rvDlt);
            out.setTarget(rvSsq);
            out.start();
            rvSsq.setVisibility(View.GONE);
            rvDlt.setVisibility(View.VISIBLE);
            in.start();
        } else {
            in.setTarget(rvSsq);
            out.setTarget(rvDlt);
            out.start();
            rvDlt.setVisibility(View.GONE);
            rvSsq.setVisibility(View.VISIBLE);
            in.start();
        }
    }

    public class Listener {
        // 页面转换
        public void convert() {
            LogUtil.i("click convert");
            vm.getPageInfoType().setValue(!(vm.getPageInfoType().getValue()));
        }
        // 调起弹框（临时）
        public void showDialog(){
            PrivacyDialog comfirmDialog = new PrivacyDialog(getActivity(), R.style.dialog, "您可以在相应功能场景/系统设置中开启权限,请您务必审慎阅读,充分理解《电子银行个人客户服务协议》和《兴福村镇银行APP隐私政策》的各项条款")
                    .setTitle("服务协议和隐私政策");
            comfirmDialog.setPositiveButton("同意").setNegativeButton("暂不使用").show();
        }
    }

    /**
     * 左滑：1.0(新) -> 0.0（新，旧） -> -1.0（旧）
     * 右滑：-1.0(新) -> 0.0（新，旧） -> 1.0（旧）
     * 这个方法会调用N次（测试中滑动一次page会调用47次）
     */
    private ViewPager2.PageTransformer vpAnimation = (page, position) -> {
        // 很舒服的方块滑屏动效
//        animotionOfPoker(page,position);

        // 使用相机实现卡片翻转动效
//        flipByCamera(page,position);

        // 卡片上下替换动效
//        animationOfCut(page,position);

        LogUtil.d("page.getWidth():" + page.getWidth() + ",position:" + position);
        /**
         * 使用canvas  通过二维变换实现卡片三维翻转
         * 限制：view只有setScaleX这种缩放整体的。无法变换左右俩边一边大一边小，形成3维翻转的视觉效果
         */
        if (position >= -1 && position <= 1) {
            page.setTranslationX(-position * page.getWidth()); // 位移
//            page.setAlpha(1); // 透明度
//            page.setScaleX((float) Math.pow(1.0f - Math.abs(position), 3.0)); // 比例缩放大小  这种方案有瑕疵，用下面方式实现
            if (position > -0.5f && position < 0.5f)
                page.setScaleX(1.0f - 2 * Math.abs(position));  // 一次函数显示效果也可 可用幂函数优化 注：scale为负值会翻转页面
            else
                page.setScaleX(0);// [-1,-0.5]or[0.5,1]直接不显示，让卡片更多时间处于“垂直于屏幕的状态”
        }
    };

    // 很舒服的方块滑屏动效
    private void animationOfPoker(@NonNull View page, float position) {
        page.setPivotX(position < 0f ? page.getWidth() : 0f);
        page.setPivotY(page.getHeight() * 0.5f);
        page.setRotationY(position * 45f);
    }

    // 通过相机进行翻转动画
    private void flipByCamera(@NonNull View page, float position) {
        final int distance = 160000;
        final float scale = getResources().getDisplayMetrics().density * distance;
        page.setCameraDistance(scale); //设置镜头距离
        page.setRotationY(180f * position);
    }

    // 像切牌一样卡片上下替换动效
    private void animationOfCut(@NonNull View page, float position) {
        final float MAX_SCALE = 1.0f;
        final float MIN_SCALE = 0.85f;
        float pos = position - 0.15f; //设置了内间距  有0.15的偏差
        if (pos <= 1) {
            float scaleFactor = MIN_SCALE + (1 - Math.abs(pos)) * (MAX_SCALE - MIN_SCALE);
            page.setScaleX(scaleFactor); //缩放效果
            if (pos > 0) {
                page.setTranslationX(-scaleFactor * 2);
            } else if (pos < 0 && pos > -1) {
                page.setTranslationX(scaleFactor * 2);
            }
            page.setScaleY(scaleFactor);
        } else {
            page.setScaleX(MIN_SCALE);
            page.setScaleY(MIN_SCALE);
        }
    }

}