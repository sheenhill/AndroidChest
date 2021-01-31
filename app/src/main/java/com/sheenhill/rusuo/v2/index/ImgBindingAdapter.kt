package com.sheenhill.common.binding_adapter


import android.widget.ImageView
import androidx.core.view.ViewCompat
import androidx.databinding.BindingAdapter
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sheenhill.common.fragment.K_BaseJetpackFragment
import com.sheenhill.common.util.ToastUtils
import com.sheenhill.rusuo.R

@BindingAdapter(value = ["url"], requireAll = true)
fun showImgByGlide(imgView: ImageView, url: String) {
    val options = RequestOptions()
            .placeholder(R.drawable.svg_placeholder)
    ViewCompat.setTransitionName(imgView, url)
    Glide.with(imgView).applyDefaultRequestOptions(options).load(url).into(imgView)
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