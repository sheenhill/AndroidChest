package com.sheenhill.common.activity

import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import com.sheenhill.common.R

/**
 * 无需activity_main.xml，直接加载fragment为临时首页（测试中使用）
 * 方便快速构造一个测试demo
 */
open class BaseTempActivity(private val fragment: Fragment) : K_BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val fragmentContainerView = FragmentContainerView(this)
        val params = ViewGroup.LayoutParams(resources.displayMetrics.widthPixels, resources.displayMetrics.heightPixels)
        setContentView(fragmentContainerView, params)
        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .setReorderingAllowed(true)
                    .add(android.R.id.content, fragment::class.java, null)
                    .commit()
        }
    }
}