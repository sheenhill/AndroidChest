package com.sheenhill.common.util

import android.content.Context

fun dp2px(context: Context,value: Int): Int {
    val v: Float = context.resources.displayMetrics.density
    return (v * value + 0.5f).toInt()
}