package com.sheenhill.module_chest.study_plan.db

import android.text.SpannableStringBuilder
import android.text.format.DateFormat
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
data class Plan(
        @PrimaryKey(autoGenerate = true) val uid: Int,
        @ColumnInfo(name = "date") val date: Long,
//        @ColumnInfo(name = "total_time") val totalTime: Float, // 计划总时长
        @ColumnInfo(name = "study_time") val studyTime: Float, // 本次时长
        @ColumnInfo(name = "spare_time") val spareTime: Float, // 剩余时长
        @ColumnInfo(name = "note") val note: String?, // 记录
        @ColumnInfo(name = "motto_id") val mottoId: Int, // 格言
) {
    // 对数据的处理应该在获取数据源之后和holder展示之前，在onBindViewHolder中处理数据会影响展示效率
    @Ignore var spannableString: SpannableStringBuilder = SpannableStringBuilder("")
    val time: String
        get() {
            return DateFormat.format("yy-MM-dd HH:mm", date).toString()
        }


}
