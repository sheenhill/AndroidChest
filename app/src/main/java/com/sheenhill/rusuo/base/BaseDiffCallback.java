package com.sheenhill.rusuo.base;

import androidx.recyclerview.widget.DiffUtil;

import java.util.List;

public abstract class BaseDiffCallback<T> extends DiffUtil.Callback {

    public abstract void setOldList(List<T> oldList) ;

    public abstract void setNewList(List<T> newList) ;
}
