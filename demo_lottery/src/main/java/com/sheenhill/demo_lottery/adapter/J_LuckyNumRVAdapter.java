package com.sheenhill.demo_lottery.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sheenhill.common.base.BaseRVAdapter;
import com.sheenhill.demo_lottery.JCrawlerViewModel;

import java.util.List;

public class J_LuckyNumRVAdapter extends BaseRVAdapter<List<String>> {

    private JCrawlerViewModel vm;
    private List<List<String>> dataList;

    public J_LuckyNumRVAdapter(JCrawlerViewModel viewModel){
        vm=viewModel;
    }

    @Override
    public List<List<String>> getList() {
        return this.dataList;
    }

    @Override
    public void setList(List<List<String>> list) {
dataList=list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    static class VH1 extends RecyclerView.ViewHolder{

        public VH1(@NonNull View itemView) {
            super(itemView);
        }
    }
}
