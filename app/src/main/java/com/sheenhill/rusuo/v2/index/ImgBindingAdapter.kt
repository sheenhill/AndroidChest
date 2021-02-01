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
}