package com.sheenhill.module_chest

import android.os.Bundle
import com.sheenhill.common.activity.K_BaseActivity


//class MainActivity : BaseTempActivity(StudyPlanFragment())
class MainActivity : K_BaseActivity(R.layout.activity_main){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setLightStatusBarStyle()
    }
}
