package com.sheenhill.module_chest.study_plan.db

import android.graphics.Color
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextUtils
import android.text.format.DateFormat
import android.text.style.CharacterStyle
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import androidx.room.Embedded
import androidx.room.Relation

data class PlanRecord(
        @Embedded val plan: Plan,
        @Relation(
                parentColumn = "motto_id",
                entityColumn = "id"
        )
        val motto: Motto?
) {

    val time: String // 打卡时间
        get() = DateFormat.format("yy-MM-dd HH:mm", plan.date).toString()

    val spannableString: SpannableStringBuilder  // 字体样式
        get() = setSpannable(plan.studyTime, plan.spareTime)

    val hasNote: Boolean   // 是否有备注
        get() = !TextUtils.isEmpty(plan.note)

    val mottoContent: String   // 学习格言
        get() = motto?.motto?:""

    override fun toString(): String {
//        return "{uid=${plan.uid}}"
        return "PlanRecord(time=$time,spannableString=$spannableString,note=${plan.note},mottoContent=$mottoContent)\n"
    }
}


/**
 * 设置字体样式
 * 在RV.adapter.onBindViewHolder前就处理好数据
 */
fun setSpannable(studyTime: Float,
                 spareTime: Float
): SpannableStringBuilder {
    val str1 = if (studyTime.compareTo(studyTime.toInt()) == 0) studyTime.toInt().toString() else studyTime.toString() // float格式化
    val str2 = if (spareTime.compareTo(spareTime.toInt()) == 0) spareTime.toInt().toString() else spareTime.toString()
        // CharacterStyle无法复用，只能使用不同实例
    val textStyleArray = arrayOf<CharacterStyle>(
            ForegroundColorSpan(Color.parseColor("#333333")),
            RelativeSizeSpan(1.5f),
            ForegroundColorSpan(Color.parseColor("#333333")),
            RelativeSizeSpan(1.5f))
    val str1SpannableString = SpannableString(str1)
    str1SpannableString.setSpan(textStyleArray[0], 0, str1.length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
    str1SpannableString.setSpan(textStyleArray[1], 0, str1.length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
    val str2SpannableString = SpannableString(str2)
    str2SpannableString.setSpan(textStyleArray[2], 0, str2.length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
    str2SpannableString.setSpan(textStyleArray[3], 0, str2.length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)

    return SpannableStringBuilder("本次学习 ")
            .append(str1SpannableString)
            .append(" h，剩余 ")
            .append(str2SpannableString)
            .append(" h。")
}