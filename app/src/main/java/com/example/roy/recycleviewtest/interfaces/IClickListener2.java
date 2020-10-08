package com.example.roy.recycleviewtest.interfaces;

import android.view.View;

public interface IClickListener2<T> {
    void itemClickCallback(View view, int pos, T o);
}
