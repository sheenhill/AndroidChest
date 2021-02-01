package com.sheenhill.rusuo.v2.index

import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.sheenhill.common.base.MainActivityViewModel
import com.sheenhill.common.base.SingleTypeBaseRVAdapter
import com.sheenhill.common.util.ToastUtils
import com.sheenhill.rusuo.R
import com.sheenhill.rusuo.databinding.ItemBingPicBinding
import com.sheenhill.rusuo.util.LogUtil

class V2_BingPicAdapter(val fragment: V2_IndexFragment) : SingleTypeBaseRVAdapter<BingPicBean.ImagesBean, ItemBingPicBinding>() {
    val vm = fragment.getActivityViewModel(MainActivityViewModel::class.java)
    val glideRequestManager = Glide.with(fragment.activity)
            .applyDefaultRequestOptions(
                    RequestOptions().placeholder(R.drawable.svg_placeholder)
                            .diskCacheStrategy(DiskCacheStrategy.DATA))

    override fun getLayoutResId(viewType: Int): Int {
        return R.layout.item_bing_pic
    }

    override fun onBindItem(binding: ItemBingPicBinding, item: BingPicBean.ImagesBean, holder: RecyclerView.ViewHolder) {
        binding.data = item
        binding.position=holder.adapterPosition
        binding.mainViewModel = vm
        binding.listener = Listener()
        binding.glideRequestManager = glideRequestManager
    }

    class Listener {
        fun showImage(view: View, url: String,position:Int, vm: MainActivityViewModel) {
            LogUtil.i("RV.item触发showImage")
            ToastUtils.showShort(view.context!!, url)
            vm.imageData.value = url
            vm.clickedViewPosition.value=position
            val extras = FragmentNavigatorExtras(view to url)
            view.findNavController().navigate(R.id.action_global_imageShowFragment, null, null, extras)
        }
    }
}