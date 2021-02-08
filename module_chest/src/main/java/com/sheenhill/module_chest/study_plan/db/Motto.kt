package com.sheenhill.module_chest.study_plan.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Fts4
import androidx.room.PrimaryKey
import java.sql.RowId


@Entity(tableName = "motto")
data class Motto(
        @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int,
        @ColumnInfo(name = "motto") val motto: String,
        @ColumnInfo(name = "cited_number",defaultValue = "0") val citedNum: Int, // 调用次数
)