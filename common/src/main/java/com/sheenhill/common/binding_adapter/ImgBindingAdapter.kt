package com.sheenhill.common.binding_adapter

import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter(value = ["url"])
fun showImgByGlide(imgView: ImageView, url: String) {
    Glide.with(imgView).load(url).into(imgView)
}