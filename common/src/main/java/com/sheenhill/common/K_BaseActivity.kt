package com.sheenhill.common

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity

abstract class K_BaseActivity(activity: Int) : AppCompatActivity(activity) {
    init {
        INSTANCE = this
    }

    companion object {
        private lateinit var INSTANCE: Activity

        fun getInstance(): Activity {
            return this.INSTANCE;
        }
    }
}