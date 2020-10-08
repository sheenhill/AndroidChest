package com.example.roy.recycleviewtest.base;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

// 规定该项目RV的功能  set|get List
public abstract class BaseRVAdapter<M> extends RecyclerView.Adapter {

    public abstract List<M> getList();

    public abstract void setList(List<M> list) ;
}
