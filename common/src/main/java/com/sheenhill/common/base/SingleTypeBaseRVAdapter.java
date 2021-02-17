package com.sheenhill.common.base;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.sheenhill.common.IClickListener;
import com.sheenhill.common.IClickListener2;

import java.util.ArrayList;
import java.util.List;


// base RV adapter

/**
 *
 * @param <M>   list bean
 * @param <B>   databinding
 */
public abstract class SingleTypeBaseRVAdapter<M, B extends ViewDataBinding> extends BaseRVAdapter<M> {

    protected List<M> mList = new ArrayList<>();
    protected IClickListener clickListener;
    protected IClickListener2<M> clickListener2;

    public SingleTypeBaseRVAdapter(){}

    public List<M> getList() {
        return mList;
    }

    public void setList(List<M> list) {
//        this.mList.clear();
//        this.mList.addAll(list);
        this.mList = list;
//        notifyDataSetChanged();
    }

    public void updateList(List<M> list){
        this.mList.clear();
        this.mList.addAll(list);
//        notifyDataSetChanged();
    }

    protected abstract @LayoutRes
    int getLayoutResId(int viewType);

    @Override
    @NonNull
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        B binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), this.getLayoutResId(viewType), parent, false);
        return new BaseBindingViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        B binding = DataBindingUtil.getBinding(holder.itemView);
        assert binding != null;
        binding.getRoot().setOnClickListener(v->{
            if(clickListener!=null)
                clickListener.itemClickCallback(v,position);
            if(clickListener2!=null)
                clickListener2.itemClickCallback(v,position,this.mList.get(position));
        });
        this.onBindItem(binding, this.mList.get(position), holder);
        binding.executePendingBindings();
    }

    public void setClickListener(IClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void setClickListener2(IClickListener2<M> clickListener2) {
        this.clickListener2 = clickListener2;
    }

    /**
     * 注意：
     * RecyclerView 中的数据有位置改变（比如删除）时一般不会重新调用 onBindViewHolder() 方法，除非这个元素不可用。
     * 为了实时获取元素的位置，我们通过 ViewHolder.getAdapterPosition() 方法。
     *
     * @param binding .
     * @param item    .
     * @param holder  .
     */
    protected abstract void onBindItem(B binding, M item, RecyclerView.ViewHolder holder);

    @Override
    public int getItemCount() {
        return null == this.mList ? 0 : this.mList.size();
    }

    public static class BaseBindingViewHolder extends RecyclerView.ViewHolder {
        public BaseBindingViewHolder(View itemView) {
            super(itemView);
        }
    }
}