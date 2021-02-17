package com.sheenhill.common.base

import androidx.recyclerview.widget.DiffUtil

abstract class KT_BaseDiffCallback<T> : DiffUtil.Callback() {
    abstract fun setOldList(list: List<T>?)
    abstract fun setNewList(list: List<T>?)
}