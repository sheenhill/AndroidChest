package com.sheenhill.common.binding_adapter


import android.widget.ImageView
import androidx.core.view.ViewCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.sheenhill.rusuo.R

@BindingAdapter(value = ["url","glide_manager"], requireAll = true)
fun showImgByGlide(imgView: ImageView, url: String,glideRequestManager:RequestManager) {
    ViewCompat.setTransitionName(imgView, url)
    glideRequestManager.load(url).into(imgView)
//    Glide.getPhotoCacheDir()
//    imgView.setOnClickListener { view ->
//        ToastUtils.showShort(view.context!!, url)
////        fragment
//        val extras = FragmentNavigatorExtras(imgView to "image")
//        imgView.findNavController().navigate(R.id.action_v2_MainFragment_to_imageDialog, null, null, extras)
//        ImageDialog(url).startShow((imgView.context!! as FragmentActivity).supportFragmentManager, "img")
//    }
}
//
//@BindingAdapter(value = ["url"])
//fun showImgFullWindow(imgView: ImageView, url: String) {
//    Glide.with(imgView).load(url).into(imgView)
//}