package com.sheenhill.common.binding_adapter

import android.app.Dialog
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.sheenhill.common.util.ToastUtils

@BindingAdapter(value = ["url"])
fun showImgByGlide(imgView: ImageView, url: String) {
    Glide.with(imgView).load(url).into(imgView)
    imgView.setOnClickListener { view ->
        ToastUtils.showShort(view.context!!,url)
    }
}
//
//@BindingAdapter(value = ["url"])
//fun showImgFullWindow(imgView: ImageView, url: String) {
//    Glide.with(imgView).load(url).into(imgView)
//}