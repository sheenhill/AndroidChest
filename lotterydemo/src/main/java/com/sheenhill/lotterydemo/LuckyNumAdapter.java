package com.sheenhill.lotterydemo;

import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.sheenhill.common.base.SingleTypeBaseRVAdapter;
import com.sheenhill.lotterydemo.databinding.ItemLuckyNumberBinding;

import java.util.List;

public class LuckyNumAdapter extends SingleTypeBaseRVAdapter<List<String>, ItemLuckyNumberBinding> {
    @Override
    protected int getLayoutResId(int viewType) {
        return R.layout.item_lucky_number;
    }

    @Override
    protected void onBindItem(ItemLuckyNumberBinding binding, List<String> item, RecyclerView.ViewHolder holder) {
        binding.setData(item);
    }
}
