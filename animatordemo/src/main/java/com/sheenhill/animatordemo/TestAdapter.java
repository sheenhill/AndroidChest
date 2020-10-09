package com.sheenhill.animatordemo;


import androidx.recyclerview.widget.RecyclerView;

import com.sheenhill.animatordemo.databinding.ItemTestBinding;
import com.sheenhill.common.base.SingleTypeBaseRVAdapter;

public class TestAdapter extends SingleTypeBaseRVAdapter<String, ItemTestBinding> {
    @Override
    protected int getLayoutResId(int viewType) {
        return R.layout.item_test;
    }

    @Override
    protected void onBindItem(ItemTestBinding binding, String item, RecyclerView.ViewHolder holder) {
        binding.setData(item);
    }
}
