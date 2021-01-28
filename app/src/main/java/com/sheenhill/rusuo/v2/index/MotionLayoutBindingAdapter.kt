package com.sheenhill.common.binding_adapter

import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sheenhill.common.R
import com.sheenhill.common.fragment.K_BaseJetpackFragment
import com.sheenhill.common.util.LogUtil
import com.sheenhill.rusuo.v2.index.IndexFragmentViewModel


/**
 * 此方法给MotionLayout的动画事件增加监听
 * 主要是：MotionScene会导致targetId的View失去本身的监听事件
 */
@BindingAdapter(value = ["view_model"])
fun setMLayoutListener(ml:MotionLayout,vm: IndexFragmentViewModel){
    ml.setTransitionListener(object : MotionLayout.TransitionListener {
        override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {}

        override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {}

        override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {
            //  iv_lottie.progress = p3
            //  操控lottie进程
        }

        override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
            if(p1== R.id.end) {
                LogUtil.i("触发了>>>>>>setMLayoutListener.setTransitionListener.onTransitionCompleted")
                vm.message.value="end"
//                iv_lottie.setMaxProgress(0.5f)
//                iv_lottie.playAnimation()
            }
        }

    })

}