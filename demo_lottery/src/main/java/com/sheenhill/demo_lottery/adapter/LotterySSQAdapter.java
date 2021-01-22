package com.sheenhill.demo_lottery.adapter;


import androidx.recyclerview.widget.RecyclerView;

import com.sheenhill.common.base.SingleTypeBaseRVAdapter;
import com.sheenhill.demo_lottery.R;
import com.sheenhill.demo_lottery.databinding.ItemLotteryBinding;

import java.util.List;

public class LotterySSQAdapter extends SingleTypeBaseRVAdapter<List<String>, ItemLotteryBinding> {
    @Override
    protected int getLayoutResId(int viewType) {
        return R.layout.item_lottery;
    }

    @Override
    protected void onBindItem(ItemLotteryBinding binding, List<String> item, RecyclerView.ViewHolder holder) {
        binding.setData(item);
    }
}
