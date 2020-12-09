package com.sheenhill.common.binding_adapter;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;


import com.sheenhill.common.base.BaseDiffCallback;
import com.sheenhill.common.base.BaseRVAdapter;
import com.sheenhill.common.util.LogUtil;

import java.util.List;


// rv的自定义绑定适配器
// 一个view最好不要有同名同输入的绑定适配器
// 修改频率不同的属性应该分开设置BindingAdapter
public class RVBindingAdapter {

//    @BindingAdapter(value ={"adapter"})
//    public static void setAdapter(RecyclerView rv,RecyclerView.Adapter adapter){
//        rv.setAdapter(adapter);
//    }

    // RecyclerView更新数据
    @BindingAdapter(value = {"resource", "adapter", "diff_callback", "itemDecoration"}, requireAll = false)
    public static void updateListCommon(RecyclerView rv,
                                        List oldList,
                                        RecyclerView.Adapter oldAdapter,
                                        BaseDiffCallback callback,
                                        RecyclerView.ItemDecoration itemDecoration,
                                        List newList,
                                        RecyclerView.Adapter newAdapter,
                                        BaseDiffCallback newCallback,
                                        RecyclerView.ItemDecoration newItemDecoration) {
        if (newItemDecoration != null && itemDecoration == null)
            rv.addItemDecoration(newItemDecoration);
        if (newList != null) {
            if(null==rv.getAdapter())
            rv.setAdapter(newAdapter);
            ((BaseRVAdapter) rv.getAdapter()).setList(newList); // 不显示数据应该是adapter被换了
            if(newCallback!=null){
                newCallback.setOldList(oldList);
                newCallback.setNewList(newList);
                DiffUtil.calculateDiff(newCallback).dispatchUpdatesTo(newAdapter);
            }
        }
    }
}
