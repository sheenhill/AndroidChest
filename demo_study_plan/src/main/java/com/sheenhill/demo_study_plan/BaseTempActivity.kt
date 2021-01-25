package com.sheenhill.demo_study_plan

import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView

open class BaseTempActivity(private val fragment: Fragment) : AppCompatActivity() {

    /**
     * 无需加载xml，activity以fragment为首页
     * 方便快速构造一个demo
     */
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