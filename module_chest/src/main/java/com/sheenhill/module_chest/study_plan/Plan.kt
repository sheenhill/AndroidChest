package com.sheenhill.module_chest.study_plan

import android.graphics.Color
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.widget.TextView
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
data class Plan(
        @PrimaryKey(autoGenerate = true) val uid: Int,
        @ColumnInfo(name = "date") val time: String,
        @ColumnInfo(name = "total_time") val totalTime: Float, // 计划总时长
        @ColumnInfo(name = "study_time") val studyTime: Float, // 本次时长
        @ColumnInfo(name = "spare_time") val spareTime: Float, // 剩余时长
        @ColumnInfo(name = "note") val note: String?, // 记录
//        @ColumnInfo(name = "motto") val motto: String?, // 格言
) {
    // 对数据的处理应该在获取数据源之后和holder展示之前，在onBindViewHolder中处理数据会影响展示效率
    @Ignore var spannableString: SpannableStringBuilder = SpannableStringBuilder("")

    fun setSpannable(fgColorSpan: ForegroundColorSpan,
                     relativeSizeSpan: RelativeSizeSpan
    ): SpannableStringBuilder {
        val str1 = studyTime.toString()
        val str2 = spareTime.toString()
//    val string = "本次学习${str1}h，剩余${str2}h。"
        val str1SpannableString = SpannableString(str1)
        str1SpannableString.setSpan(fgColorSpan, 0, str1.length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
        str1SpannableString.setSpan(relativeSizeSpan, 0, str1.length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
        val str2SpannableString = SpannableString(str2)
        str2SpannableString.setSpan(fgColorSpan, 0, str2.length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
        str2SpannableString.setSpan(relativeSizeSpan, 0, str2.length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)

        val spannableString = SpannableStringBuilder("本次学习")
                .append(str1SpannableString)
                .append("h，剩余")
                .append(str2SpannableString)
                .append("h。")
        return spannableString
    }


}
