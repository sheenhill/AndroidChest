package com.sheenhill.common.binding_adapter

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.sheenhill.common.base.BaseRVAdapter
import com.sheenhill.common.base.KT_BaseDiffCallback
import com.sheenhill.common.util.LogUtil


// RecyclerView更新数据
@BindingAdapter(value = ["resource", "diff_callback"], requireAll = false)
fun <T> updateListCommon(rv: RecyclerView,
                         oldList: List<T>?,
                         oldDiffCallback: KT_BaseDiffCallback<*>?,
                         newList: List<T>?,
                         newDiffCallback: KT_BaseDiffCallback<*>?) {
    if (newList != null&&newDiffCallback!=null) {
        newDiffCallback.setOldList(oldList as List<Nothing>?)
        newDiffCallback.setNewList(newList as List<Nothing>?)
        LogUtil.d("do dispatchUpdatesToAdapter")
        DiffUtil.calculateDiff(newDiffCallback).dispatchUpdatesTo(rv.adapter!!)
        (rv.adapter as BaseRVAdapter<*>?)!!.list = newList
        if(newList.size>oldList?.size?:0){  // 添加item时，rv滑至顶部
            rv.layoutManager!!.scrollToPosition(0)
        }
    }
}

@BindingAdapter(value = ["adapter", "itemDecoration"], requireAll = false)
fun setRVCommon(rv: RecyclerView,
                newAdapter: RecyclerView.Adapter<*>?,
                newItemDecoration: ItemDecoration?) {
//    if (rv.layoutManager == null)
//        rv.layoutManager = LinearLayoutManager(rv.context!!)
    if (null == rv.adapter) rv.adapter = newAdapter
    LogUtil.d("new adapter?????")
    if (newItemDecoration != null) rv.addItemDecoration(newItemDecoration)
    rv.itemAnimator=DefaultItemAnimator()
}
