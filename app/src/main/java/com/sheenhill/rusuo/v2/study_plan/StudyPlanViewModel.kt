package com.sheenhill.rusuo.v2.study_plan

import android.annotation.SuppressLint
import android.text.format.DateFormat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sheenhill.rusuo.util.LogUtil
import com.sheenhill.rusuo.v2.db.AppDatabase
import com.sheenhill.rusuo.v2.db.Plan
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class StudyPlanViewModel:ViewModel() {
    val planRecords=MutableLiveData<List<Plan>>()

    init {
        getPlanList()
    }

    @SuppressLint("SimpleDateFormat")
    fun addPlanRecord(studyTime:Float) {
        viewModelScope.launch {
            val db = AppDatabase.instance
            val planDao = db.planDao()
            planDao.addOneTime(Plan(0, "yy年MM月dd日 HH时mm分ss秒", 1000f, studyTime, 1000f-studyTime, "xxxxxx"))
            getPlanList()
        }
    }

    fun getPlanList() {
        viewModelScope.launch {
            val db = AppDatabase.instance
            planRecords.postValue(db.planDao().getAll())
            LogUtil.d("getPlanList>>>>>>${planRecords.value}")
        }
    }
}
