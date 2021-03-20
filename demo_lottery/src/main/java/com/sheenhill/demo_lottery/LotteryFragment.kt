package com.sheenhill.demo_lottery

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.sheenhill.common.base.SingleTypeBaseRVAdapter
import com.sheenhill.common.fragment.K_BaseJetpackFragment
import com.sheenhill.common.fragment.K_DataBindingConfig
import com.sheenhill.common.util.LogUtil
import com.sheenhill.common.util.dp2px
import com.sheenhill.demo_lottery.databinding.FragmentLotteryBinding
import com.sheenhill.demo_lottery.databinding.ItemLotteryV3Binding

/* 集成模式下的彩票模块入口，直接用了Navigation组件来导航 */
class LotteryFragment : K_BaseJetpackFragment() {
    lateinit var vm: LotteryViewModel
    override fun initViewModel() {
        // 获取父级Fragment的ViewModel（从nav中拿）
        vm = getNavigationViewModel(LotteryViewModel::class.java, R.id.nav_lottery)
    }

    override fun getDataBindingConfig(): K_DataBindingConfig {
        return K_DataBindingConfig(R.layout.fragment_lottery, BR.viewModel, vm)
                .addBindingParam(BR.listener, Listener())
    }

    override fun hasStatusBarHeight(): Boolean = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val lotteryAdapter = LotteryVPAdapter(this)
        (mBinding as FragmentLotteryBinding).vpLottery.adapter = lotteryAdapter
//        (mBinding as FragmentLotteryBinding).vpLottery.setPageTransformer(vpAnimation)
        (mBinding as FragmentLotteryBinding).vpLottery.offscreenPageLimit = 1

        // vp2 页面选择的监听
        (mBinding as FragmentLotteryBinding).vpLottery.registerOnPageChangeCallback(
                object : ViewPager2.OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        super.onPageSelected(position)
                        vm.pageInfoType.value = position
                    }
                }
        )
        // 获取双色球数据
        vm.crawlerSsq(context, "http://datachart.500.com/ssq/history/history.shtml")
        // 获取大乐透数据
        vm.crawlerDLT(context, "http://datachart.500.com/dlt/history/history.shtml")

        vm.pageInfoType.observe(viewLifecycleOwner, { type: Int ->
            val lotteryType = when (type) {
                vm.TYPE_SSQ -> "双色球"
                vm.TYPE_DLT -> "大乐透"
                else -> ""
            }
            LogUtil.d("pageInfoType>>>>>${lotteryType}")
            (mBinding as FragmentLotteryBinding).vpLottery.currentItem = type
        })
        return mBinding.root
    }

    class Listener {
        // 页面转换
        fun convert(vm: LotteryViewModel) {
            LogUtil.i("click convert")
            vm.pageInfoType.value = if(vm.pageInfoType.value==vm.TYPE_SSQ) vm.TYPE_DLT else vm.TYPE_SSQ
        }
    }

    /**
     * 左滑：1.0(新) -> 0.0（新，旧） -> -1.0（旧）
     * 右滑：-1.0(新) -> 0.0（新，旧） -> 1.0（旧）
     * 这个方法会调用N次（测试中滑动一次page会调用47次）
     */
    private val vpAnimation = ViewPager2.PageTransformer { page: View, position: Float ->

        //        LogUtil.d("page.getWidth():" + page.width + ",position:" + position)

        // 很舒服的方块滑屏动效
//        animotionOfPoker(page,position);

        // 使用相机实现卡片翻转动效
//        flipByCamera(page,position);

        // 卡片上下替换动效
//        animationOfCut(page,position);

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
        page.pivotX = (if (position < 0f) page.width as Float else 0f)
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

class LotteryVPAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        LogUtil.d("LotteryVPAdapter.createFragment.position=${position}")
        return when (position) {
            0 -> SSQFragmentV2()
            1 -> DLTFragmentV2()
            else -> DLTFragmentV2()
        }
    }
}

/* 大乐透 fragment */
class DLTFragmentV2 : K_BaseJetpackFragment() {
    lateinit var vm: LotteryViewModel
    override fun initViewModel() {
        // 获取父级Fragment的ViewModel（从nav中拿）
        vm = getNavigationViewModel(LotteryViewModel::class.java, R.id.nav_lottery)
    }

    override fun getDataBindingConfig(): K_DataBindingConfig {
        return K_DataBindingConfig(R.layout.fragment_dlt_v2, BR.viewModel, vm)
                .addBindingParam(BR.adapterDLT, LotteryDLTAdapterV2())
                .addBindingParam(BR.itemDecoration, HeaderItemDecoration(requireContext()))
    }

    class LotteryDLTAdapterV2 : SingleTypeBaseRVAdapter<LotteryBean, ItemLotteryV3Binding>() {
        override fun getLayoutResId(viewType: Int): Int {
            return R.layout.item_lottery_v3
        }

        override fun onBindItem(binding: ItemLotteryV3Binding, item: LotteryBean, holder: RecyclerView.ViewHolder) {
            binding.data = item
        }
    }
}

/* 双色球 fragment */
class SSQFragmentV2 : K_BaseJetpackFragment() {
    lateinit var vm: LotteryViewModel
    override fun initViewModel() {
        // 获取父级Fragment的ViewModel（从nav中拿）
        vm = getNavigationViewModel(LotteryViewModel::class.java, R.id.nav_lottery)
    }

    override fun getDataBindingConfig(): K_DataBindingConfig {
        return K_DataBindingConfig(R.layout.fragment_ssq_v2, BR.viewModel, vm)
                .addBindingParam(BR.adapterSSQ, LotterySSQAdapterV2())
                .addBindingParam(BR.itemDecoration, HeaderItemDecoration(requireContext()))
    }

    class LotterySSQAdapterV2 : SingleTypeBaseRVAdapter<LotteryBean, ItemLotteryV3Binding>() {
        override fun getLayoutResId(viewType: Int): Int {
            return R.layout.item_lottery_v3
        }

        override fun onBindItem(binding: ItemLotteryV3Binding, item: LotteryBean, holder: RecyclerView.ViewHolder) {
            binding.data = item
        }
    }
}

/* 彩票 RV item 装饰 */
class HeaderItemDecoration(context: Context) : RecyclerView.ItemDecoration() {
    val margin = dp2px(context, 8)

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        val position = parent.getChildLayoutPosition(view)
        val count = parent.adapter!!.itemCount //  获取准确的Item总个数
        when (position) {
            0 -> outRect.set(0, margin * 3, 0, 0)
            (count - 1) -> outRect.set(0, 0, 0, margin)
        }
    }
}
