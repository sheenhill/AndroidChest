package com.sheenhill.common.binding_adapter

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.sheenhill.common.base.BaseDiffCallback
import com.sheenhill.common.base.BaseRVAdapter

//    @BindingAdapter(value ={"adapter"})
//    public static void setAdapter(RecyclerView rv,RecyclerView.Adapter adapter){
//        rv.setAdapter(adapter);
//    }
// RecyclerView更新数据
@BindingAdapter(value = ["resource", "adapter", "diff_callback", "itemDecoration"], requireAll = false)
fun updateListCommon(rv: RecyclerView,
                     oldList: List<*>?,
                     oldAdapter: RecyclerView.Adapter<*>?,
                     callback: BaseDiffCallback<*>?,
                     itemDecoration: ItemDecoration?,
                     newList: List<*>?,
                     newAdapter: RecyclerView.Adapter<*>?,
                     newCallback: BaseDiffCallback<*>?,
                     newItemDecoration: ItemDecoration?) {
    if (rv.layoutManager == null) {
        rv.layoutManager = LinearLayoutManager(rv.context!!)
    }
    if (newItemDecoration != null && itemDecoration == null) rv.addItemDecoration(newItemDecoration)
    if (newList != null) {
        if (null == rv.adapter) rv.adapter = newAdapter
        (rv.adapter as BaseRVAdapter<*>?)!!.setList(newList as List<Nothing>?) // 不显示数据应该是adapter被换了
        if (newCallback != null) {
            newCallback.setOldList(oldList as List<Nothing>?)
            newCallback.setNewList(newList as List<Nothing>?)
            DiffUtil.calculateDiff(newCallback).dispatchUpdatesTo(newAdapter!!)
        }
    }
}