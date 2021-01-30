package com.sheenhill.rusuo.v2.index

import android.app.Activity
import android.view.View
import android.widget.ImageView
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.RecyclerView
import com.sheenhill.common.base.SingleTypeBaseRVAdapter
import com.sheenhill.common.fragment.K_BaseJetpackFragment
import com.sheenhill.common.util.ToastUtils
import com.sheenhill.rusuo.R
import com.sheenhill.rusuo.databinding.ItemBingPicBinding
import com.sheenhill.rusuo.util.LogUtil

class V2_BingPicAdapter: SingleTypeBaseRVAdapter<BingPicBean.ImagesBean, ItemBingPicBinding>() {

    override fun getLayoutResId(viewType: Int): Int {
        return R.layout.item_bing_pic
    }

    override fun onBindItem(binding: ItemBingPicBinding, item: BingPicBean.ImagesBean, holder: RecyclerView.ViewHolder) {
        binding.data = item
        binding.listener = Listener()
    }

    class Listener {
        fun showImage(view: View, url: String) {
            LogUtil.i("RV.item触发showImage")
            ToastUtils.showShort(view.context!!, url)
            val extras = FragmentNavigatorExtras(view to url)
            view.findNavController().navigate(R.id.action_global_imageShowFragment, null, null, extras)
        }
    }
}