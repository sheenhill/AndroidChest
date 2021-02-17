package com.sheenhill.common.activity

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView

/**
 * 无需activity_main.xml，直接加载fragment为临时首页（测试中使用）
 * 方便快速构造一个测试demo
 */
open class BaseTempActivity(private val fragment: Fragment) : K_BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val fragmentContainerView = FragmentContainerView(this)

        val params = ViewGroup.LayoutParams(window.decorView.width, window.decorView.width)
        setContentView(fragmentContainerView, params)
        val flag = (View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
//                or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR) // 6.0 以上，状态栏支持字体变灰色
        window?.decorView?.systemUiVisibility = flag
        window?.statusBarColor = Color.TRANSPARENT
        window?.navigationBarColor = Color.TRANSPARENT
        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .setReorderingAllowed(true)
                    .add(android.R.id.content, fragment::class.java, null)
                    .commit()
        }
    }
}