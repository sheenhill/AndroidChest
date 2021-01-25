package com.sheenhill.demo_lottery

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.sheenhill.common.fragment.K_BaseJetpackFragment
import com.sheenhill.common.fragment.K_DataBindingConfig
import com.sheenhill.common.util.LogUtil
import com.sheenhill.demo_lottery.adapter.LotteryVPAdapter
import com.sheenhill.demo_lottery.databinding.FragmentCrawlerBinding

class CrawlerFragment:K_BaseJetpackFragment() {
    private lateinit var viewModel:JCrawlerViewModel
    override fun initViewModel() {
        viewModel= JCrawlerViewModel()
    }

    override fun getDataBindingConfig(): K_DataBindingConfig {
        return K_DataBindingConfig(R.layout.fragment_crawler, BR.viewModel, viewModel)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val binding= mBinding as FragmentCrawlerBinding
        initVpModeView(binding)
        // initShModeView();
        initEvent(viewModel,binding)
        return binding.root
    }

    /* 用viewpager承载俩种彩票页面 */
    private fun initVpModeView(binding: FragmentCrawlerBinding) {
        val lotteryAdapter = LotteryVPAdapter(this)
        binding.vpLottery.adapter = lotteryAdapter
        binding.vpLottery.setPageTransformer(vpAnimation)
        //        binding.vpLottery.setPageTransformer(new MarginPageTransformer(50));
        binding.vpLottery.offscreenPageLimit = 1
    }


    /* 用view显示隐藏的切换俩种彩票页面 */
    private fun initShModeView() {
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

    private fun initEvent(vm: JCrawlerViewModel,binding: FragmentCrawlerBinding) {
        // 获取双色球数据
        vm.crawlerSsq(context, "http://datachart.500.com/ssq/history/history.shtml")
        // 获取大乐透数据
        vm.crawlerDLT(context, "http://datachart.500.com/dlt/history/history.shtml")
        vm.pageInfoType.observe(viewLifecycleOwner, { bool: Boolean ->
//            showOrHideView(bool, binding.rvSsq,binding.rvDlt);
            binding.vpLottery.setCurrentItem(if (bool) 1 else 0)
        })
    }

    // 根据数据进行显示隐藏动效
    private fun showOrHideView(bool: Boolean, rvSsq: View, rvDlt: View) {
        val `in` = AnimatorInflater.loadAnimator(context,
                R.animator.card_flip_in) as AnimatorSet
        val out = AnimatorInflater.loadAnimator(context,
                R.animator.card_flip_out) as AnimatorSet
        if (bool == JCrawlerViewModel.DLT) {
            `in`.setTarget(rvDlt)
            out.setTarget(rvSsq)
            out.start()
            rvSsq.visibility = View.GONE
            rvDlt.visibility = View.VISIBLE
            `in`.start()
        } else {
            `in`.setTarget(rvSsq)
            out.setTarget(rvDlt)
            out.start()
            rvDlt.visibility = View.GONE
            rvSsq.visibility = View.VISIBLE
            `in`.start()
        }
    }

    class Listener {
        // 页面转换
        fun convert(vm: JCrawlerViewModel) {
            LogUtil.i("click convert")
            vm.pageInfoType.value = !vm.pageInfoType.value!!
        }
    }

    /**
     * 左滑：1.0(新) -> 0.0（新，旧） -> -1.0（旧）
     * 右滑：-1.0(新) -> 0.0（新，旧） -> 1.0（旧）
     * 这个方法会调用N次（测试中滑动一次page会调用47次）
     */
    private val vpAnimation = ViewPager2.PageTransformer { page: View, position: Float ->
        // 很舒服的方块滑屏动效
//        animotionOfPoker(page,position);

        // 使用相机实现卡片翻转动效
//        flipByCamera(page,position);

        // 卡片上下替换动效
//        animationOfCut(page,position);
        LogUtil.d("page.getWidth():" + page.width + ",position:" + position)
        /**
         * 使用canvas  通过二维变换实现卡片三维翻转
         * 限制：view只有setScaleX这种缩放整体的。无法变换左右俩边一边大一边小，形成3维翻转的视觉效果
         */
        if (position >= -1 && position <= 1) {
            page.translationX = -position * page.width // 位移
            //            page.setAlpha(1); // 透明度
//            page.setScaleX((float) Math.pow(1.0f - Math.abs(position), 3.0)); // 比例缩放大小  这种方案有瑕疵，用下面方式实现
            if (position > -0.5f && position < 0.5f) page.scaleX = 1.0f - 2 * Math.abs(position) // 一次函数显示效果也可 可用幂函数优化 注：scale为负值会翻转页面
            else page.scaleX = 0f // [-1,-0.5]or[0.5,1]直接不显示，让卡片更多时间处于“垂直于屏幕的状态”
        }
    }

    // 很舒服的方块滑屏动效
    private fun animationOfPoker(page: View, position: Float) {
        page.pivotX=(if (position < 0f) page.width as Float else 0f)
        page.pivotY = page.height * 0.5f
        page.rotationY = position * 45f
    }

    // 通过相机进行翻转动画
    private fun flipByCamera(page: View, position: Float) {
        val distance = 160000
        val scale = resources.displayMetrics.density * distance
        page.cameraDistance = scale //设置镜头距离
        page.rotationY = 180f * position
    }

    // 像切牌一样卡片上下替换动效
    private fun animationOfCut(page: View, position: Float) {
        val MAX_SCALE = 1.0f
        val MIN_SCALE = 0.85f
        val pos = position - 0.15f //设置了内间距  有0.15的偏差
        if (pos <= 1) {
            val scaleFactor = MIN_SCALE + (1 - Math.abs(pos)) * (MAX_SCALE - MIN_SCALE)
            page.scaleX = scaleFactor //缩放效果
            if (pos > 0) {
                page.translationX = -scaleFactor * 2
            } else if (pos < 0 && pos > -1) {
                page.translationX = scaleFactor * 2
            }
            page.scaleY = scaleFactor
        } else {
            page.scaleX = MIN_SCALE
            page.scaleY = MIN_SCALE
        }
    }

}