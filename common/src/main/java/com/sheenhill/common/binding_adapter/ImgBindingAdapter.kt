package com.sheenhill.common.binding_adapter


import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.findFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sheenhill.common.R
import com.sheenhill.common.fragment.ImageDialog
import com.sheenhill.common.util.ToastUtils

@BindingAdapter(value = ["url"])
fun showImgByGlide(imgView: ImageView, url: String) {
    val options = RequestOptions()
            .placeholder(R.drawable.svg_placeholder)
    Glide.with(imgView).applyDefaultRequestOptions(options).load(url).into(imgView)
    imgView.setOnClickListener { view ->
        ToastUtils.showShort(view.context!!, url)
        ImageDialog(url).startShow((imgView.context!! as FragmentActivity).supportFragmentManager,"img")
    }
}
//
//@BindingAdapter(value = ["url"])
//fun showImgFullWindow(imgView: ImageView, url: String) {
//    Glide.with(imgView).load(url).into(imgView)
//}