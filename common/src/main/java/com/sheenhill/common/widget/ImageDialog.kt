package com.sheenhill.common.widget

import android.content.DialogInterface
import android.view.ViewGroup
import android.view.WindowManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sheenhill.common.R
import com.sheenhill.common.base.BaseDialog
import com.sheenhill.common.databinding.DialogImageBinding
import java.io.File

class ImageDialog(val imgUrl:String) :BaseDialog<DialogImageBinding, ImageDialog>(){
     override fun getLayoutId(): Int = R.layout.dialog_image

     override fun initView(binding: DialogImageBinding) {
         super.initView(binding)
         val options = RequestOptions()
                 .placeholder(R.drawable.svg_placeholder)
//         Glide.with(binding.imageView).applyDefaultRequestOptions(options).load(imgUrl).into(binding.imageView)
//         Glide.with(binding.imageView).applyDefaultRequestOptions(options).load(imageFile).into(binding.imageView)
     }

     override fun setLayout(params: WindowManager.LayoutParams): WindowManager.LayoutParams {
         super.setLayout(params)
         params.height= ViewGroup.LayoutParams.MATCH_PARENT
         params.width= ViewGroup.LayoutParams.MATCH_PARENT
         return params
     }

     override fun initListener(binding: DialogImageBinding) {
         super.initListener(binding)
         binding.root.setOnClickListener { dismiss() }
     }

     override fun onDismiss(dialog: DialogInterface) {
         super.onDismiss(dialog)
         Glide.with(context).clear(getBinding().imageView);
     }
 }