package com.sheenhill.lotterydemo.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.sheenhill.common.base.BaseRVAdapter;
import com.sheenhill.common.base.SingleTypeBaseRVAdapter;
import com.sheenhill.lotterydemo.R;
import com.sheenhill.lotterydemo.databinding.ItemLuckyNumberBinding;

import java.util.ArrayList;
import java.util.List;

public class LuckyNumAdapter extends BaseRVAdapter<String> {
    private List<String> list=new ArrayList<>();

    @NonNull
    @Override
    public vHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public List<String> getList() {
        return list;
    }

    @Override
    public void setList(List<String> list) {
this.list=list;
    }

    static class vHolder extends RecyclerView.ViewHolder{

        public vHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
