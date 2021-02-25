package com.sheenhill.module_chest.custom_view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.sheenhill.module_chest.R
import java.util.jar.Attributes

class MyWheelView : RecyclerView {
    constructor(context: Context) : super(context) {

    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
// 通过obtainStyledAttributes方法获取到TypedArray对象
        val a = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.CustomCreateView,
                0, 0
        )

        try {
            // 获取相应的属性值
            isShowText = a.getBoolean(R.styleable.CustomCreateView_showText, false)
            textPos = a.getInteger(R.styleable.CustomCreateView_labelPosition, 0)
        } finally {
            // TypedArray是共享资源必须在使用后进行回收
            a.recycle()
        }
    }

    private fun initThis() {
        initThis(arrayListOf("☰", "☵", "☶", "☳", "☴", "☲", "☷", "☱"))
    }

    private fun initThis(labelArr: List<String>) {

    }

}