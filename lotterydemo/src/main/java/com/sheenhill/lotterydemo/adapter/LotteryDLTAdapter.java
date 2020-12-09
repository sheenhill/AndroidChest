package com.sheenhill.lotterydemo.adapter;


import androidx.recyclerview.widget.RecyclerView;

import com.sheenhill.common.base.SingleTypeBaseRVAdapter;
import com.sheenhill.lotterydemo.R;
import com.sheenhill.lotterydemo.databinding.ItemLotteryDltBinding;

import java.util.List;

public class LotteryDLTAdapter extends SingleTypeBaseRVAdapter<List<String>, ItemLotteryDltBinding> {
    @Override
    protected int getLayoutResId(int viewType) {
        return R.layout.item_lottery_dlt;
    }

    @Override
    protected void onBindItem(ItemLotteryDltBinding binding, List<String> item, RecyclerView.ViewHolder holder) {
        binding.setData(item);
    }
}
