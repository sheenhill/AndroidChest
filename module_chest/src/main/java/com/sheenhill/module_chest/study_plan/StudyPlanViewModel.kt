package com.sheenhill.module_chest.study_plan

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sheenhill.common.util.LogUtil
import com.sheenhill.module_chest.study_plan.db.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StudyPlanViewModel : ViewModel() {
    val planRecords = MutableLiveData<List<PlanRecord>>()
    val currentRecord = MutableLiveData<Record>(Record(0, 0, ""))
//    val currentRecordH = MutableLiveData<String>()
    val currentRecordH = ObservableField<String>()
//    val currentRecordM = MutableLiveData<String>()
    val currentRecordM = ObservableField<String>()
    val currentRecordNote=MutableLiveData<String>()
    private var lastSpareTime: Float = 2000f


    data class Record(val hours: Int, val mins: Int, val note: String)

    init {
        getPlanRecords()
//        getMottoList()
//        updateNumTest()
//        testGetAllRecords()
    }

    /* 添加一条记录，并刷新页面 */
    fun addPlanRecord() {
        val studyTime = (currentRecordH.get()?:"0").toFloat() + ((currentRecordM.get()?:"0").toInt()/ 60f * 10).toInt().toFloat() / 10
        LogUtil.d("addPlanRecord>>>>>>studyTime=$studyTime,currentRecordNote=${currentRecordNote.value?:""}")
        viewModelScope.launch {
            val db = RusuoDatabase.instance
            val studyPlanDao = db.studyPlanDao()
            // 1.获取格言   2.添加一条记录  3.更新格言频率  4.刷新页面
            val mottoRes = studyPlanDao.selectOneLowCitedMotto()  // 1
            val mottoId = if (mottoRes.isNotEmpty()) mottoRes[0].id else 0
            studyPlanDao.addOneRecord(  // 2
                    Plan(0,
                            System.currentTimeMillis(),
                            studyTime,
                            lastSpareTime - studyTime,
                            currentRecordNote.value?:"",
                            mottoId))
            studyPlanDao.updateCitedNum(mottoId) //  3
            getPlanRecords()
        }
    }

    /* 获取plan 显示列表 */
    fun getPlanRecords() {
        viewModelScope.launch(Dispatchers.IO) {
            val list = RusuoDatabase.instance.studyPlanDao().getAllPlanRecords()
            if (list.isNotEmpty())
                lastSpareTime = list[list.size - 1].plan.spareTime
            planRecords.postValue(list.reversed())
//            planRecords.postValue(list)
            LogUtil.d("getPlanList  size=${list.size},>>>>>>${list}")
        }
    }


    // 测试
    private fun getMottoList() {
        viewModelScope.launch(Dispatchers.IO) {
            val list = RusuoDatabase.instance.mottoDao().getAllMotto()
            LogUtil.i("motto list>>>>>>${list}")
        }
    }

    // 测试
    private fun updateNumTest() {
        viewModelScope.launch(Dispatchers.IO) {
            var motto: List<Motto>
            var updateResult: Int
            for (i in 0..1000) {
                motto = RusuoDatabase.instance.mottoDao().selectOneLowCitedMotto()
                updateResult = RusuoDatabase.instance.mottoDao().updateCitedNum(motto[0].id)
                LogUtil.d("updateNumTest($i).rowId：${motto[0].id},频率：${motto[0].citedNum},更新数据库结果：${updateResult > 0}")
            }
        }
    }


    // 测试
    private fun testGetAllRecords() {
        viewModelScope.launch(Dispatchers.IO) {
            var list = RusuoDatabase.instance.studyPlanDao().getAllPlanRecords()
            LogUtil.d("getAllPlanRecords>>>>>>${list}")
        }
    }


}
