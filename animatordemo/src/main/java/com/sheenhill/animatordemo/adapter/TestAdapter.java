package com.sheenhill.animatordemo.adapter;


import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.annotation.SuppressLint;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;

import androidx.recyclerview.widget.RecyclerView;

import com.sheenhill.animatordemo.R;
import com.sheenhill.animatordemo.databinding.ItemTestBinding;
import com.sheenhill.common.base.SingleTypeBaseRVAdapter;
import com.sheenhill.common.util.LogUtil;

public class TestAdapter extends SingleTypeBaseRVAdapter<String, ItemTestBinding> {
    @Override
    protected int getLayoutResId(int viewType) {
        return R.layout.item_test;
    }

    @SuppressLint("ResourceType")
    @Override
    protected void onBindItem(ItemTestBinding binding, String item, RecyclerView.ViewHolder holder) {
        binding.setData(item);
        // 给item用下面俩种方式加动效都可
//        holder.itemView.setAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(),R.animator.item_animation));
//        LogUtil.d("onBindItem:[adapterPosition,layoutPosition]>>>>>"+"["+holder.getAdapterPosition()+","+holder.getLayoutPosition()+"]");
//
//        final int distance = 2000;// 这个是应该在1920左右
////        final int distance = 3000;
//        final float scale = holder.itemView.getResources().getDisplayMetrics().density * distance;
//        holder.itemView.setCameraDistance(scale);
//        AnimatorSet set=(AnimatorSet)AnimatorInflater.loadAnimator(holder.itemView.getContext(),R.animator.polyhedron_l2r_step3);
//        set.setTarget(holder.itemView);
//        set.start();
//        LogUtil.i("当前holder.getLayoutPosition()>>>>>"+holder.getLayoutPosition()+"");
//        LogUtil.i("当前holder.getAdapterPosition()>>>>>"+holder.getAdapterPosition()+"");
//        LogUtil.i("当前holder.itemView.getLeft(),getRight()>>>>>"+holder.itemView.getLeft()+","+holder.itemView.getRight());
//        holder.itemView.getRight()
//        binding.getRoot().setAnimation();
//        AnimationUtils.loadAnimation()
    }

}
