package com.sheenhill.rusuo.v2.index

import androidx.recyclerview.widget.RecyclerView
import com.sheenhill.common.base.SingleTypeBaseRVAdapter
import com.sheenhill.rusuo.R
import com.sheenhill.rusuo.databinding.ItemBingPicBinding

class V2_BingPicAdapter: SingleTypeBaseRVAdapter<BingPicBean.ImagesBean, ItemBingPicBinding>() {

    override fun getLayoutResId(viewType: Int): Int {
        return R.layout.item_bing_pic
    }

    override fun onBindItem(binding: ItemBingPicBinding, item: BingPicBean.ImagesBean, holder: RecyclerView.ViewHolder) {
            binding.data=item
    }
}