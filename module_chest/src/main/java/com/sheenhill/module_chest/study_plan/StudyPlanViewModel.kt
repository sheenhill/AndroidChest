package com.sheenhill.module_chest.study_plan

import android.annotation.SuppressLint
import android.graphics.Color
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.CharacterStyle
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sheenhill.common.util.LogUtil
import kotlinx.coroutines.launch

class StudyPlanViewModel : ViewModel() {
    val planRecords = MutableLiveData<List<Plan>>()

    private val textStyleArray = arrayOf<CharacterStyle>(
            ForegroundColorSpan(Color.parseColor("#333333")),
            RelativeSizeSpan(1.5f),
            ForegroundColorSpan(Color.parseColor("#333333")),
            RelativeSizeSpan(1.5f))


    init {
        getPlanList()
    }

    @SuppressLint("SimpleDateFormat")
    fun addPlanRecord(studyTime: Float) {
        viewModelScope.launch {
            val db = PlanDatabase.instance
            val planDao = db.planDao()
            planDao.addOneTime(Plan(0, "yy年MM月dd日 HH时mm分ss秒", 1000f, studyTime, 1000f - studyTime, "xxxxxx"))
            getPlanList()
        }
    }

    fun getPlanList() {
        viewModelScope.launch {
            val list = PlanDatabase.instance.planDao().getAll()
            list.forEach { it.spannableString = setSpannable(it.studyTime, it.spareTime, textStyleArray) }
            planRecords.postValue(list)
            LogUtil.d("getPlanList>>>>>>${planRecords.value}")
        }
    }

    // 设置字体样式
    private fun setSpannable(studyTime: Float,
                             spareTime: Float,
                             textStyleArray: Array<CharacterStyle>
    ): SpannableStringBuilder {
        val str1 = studyTime.toString()
        val str2 = spareTime.toString()
//    val string = "本次学习${str1}h，剩余${str2}h。"
        val str1SpannableString = SpannableString(str1)
        str1SpannableString.setSpan(textStyleArray[0], 0, str1.length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
        str1SpannableString.setSpan(textStyleArray[1], 0, str1.length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
        val str2SpannableString = SpannableString(str2)
        str2SpannableString.setSpan(textStyleArray[2], 0, str2.length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)// CharacterStyle无法复用
        str2SpannableString.setSpan(textStyleArray[3], 0, str2.length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)

        val spannableString = SpannableStringBuilder("本次学习 ")
                .append(str1SpannableString)
                .append(" h，剩余 ")
                .append(str2SpannableString)
                .append(" h。")
        return spannableString
    }
}
