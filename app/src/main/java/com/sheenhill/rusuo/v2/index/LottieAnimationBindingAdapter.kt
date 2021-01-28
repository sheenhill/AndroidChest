package com.sheenhill.rusuo.v2.index

import android.animation.Animator
import android.text.TextUtils
import androidx.databinding.BindingAdapter
import com.airbnb.lottie.LottieAnimationView
import com.sheenhill.rusuo.util.LogUtil

@BindingAdapter(value = ["msg","view_model"],requireAll = true)
fun setLottieAnimationListener(la:LottieAnimationView,oldMsg:String?,vmOld:IndexFragmentViewModel?,newMsg: String,vmNew:IndexFragmentViewModel){
    LogUtil.i("BindingAdapter.setLottieAnimationListener")
    if(TextUtils.isEmpty(oldMsg)){
        la.removeAllAnimatorListeners()  //la自带有监听，但是没有调用方法
        la.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {
                LogUtil.d("BindingAdapter.addAnimatorListener.onAnimationStart")
            }

            override fun onAnimationEnd(animation: Animator?) {
                LogUtil.d("BindingAdapter.addAnimatorListener.onAnimationEnd")
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
