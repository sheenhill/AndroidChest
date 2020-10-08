package com.sheenhill.common;

import android.view.View;

public interface IClickListener2<T> {
    void itemClickCallback(View view, int pos, T o);
}
