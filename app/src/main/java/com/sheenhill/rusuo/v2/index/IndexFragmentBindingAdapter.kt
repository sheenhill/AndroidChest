package com.sheenhill.rusuo.v2.index

import android.animation.Animator
import android.text.TextUtils
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.databinding.BindingAdapter
import com.airbnb.lottie.LottieAnimationView
import com.sheenhill.common.R
import com.sheenhill.common.util.LogUtil


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
            }
        }

    })

}

@BindingAdapter(value = ["msg","view_model"],requireAll = true)
fun setLottieAnimationListener(la:LottieAnimationView,oldMsg:String?,vmOld:IndexFragmentViewModel?,newMsg: String,vmNew:IndexFragmentViewModel){
    com.sheenhill.rusuo.util.LogUtil.i("BindingAdapter.setLottieAnimationListener")
    if(TextUtils.isEmpty(oldMsg)){
        la.removeAllAnimatorListeners()  //la自带有监听，但是没有调用方法,所以先清空在添加监听
        la.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {}

            override fun onAnimationEnd(animation: Animator?) {
                com.sheenhill.rusuo.util.LogUtil.d("BindingAdapter.addAnimatorListener.onAnimationEnd")
                vmNew.message.value="jump"
            }

            override fun onAnimationCancel(animation: Animator?) {}

            override fun onAnimationRepeat(animation: Animator?) {}

        })
    }

    if(newMsg=="end"){
        la.setMaxProgress(0.5f)
        la.playAnimation()
    }

}