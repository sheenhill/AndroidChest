package com.sheenhill.rusuo.v2.study_plan

import androidx.recyclerview.widget.RecyclerView
import com.sheenhill.common.base.SingleTypeBaseRVAdapter
import com.sheenhill.rusuo.R
import com.sheenhill.rusuo.databinding.ItemStudyRecordBinding
import com.sheenhill.rusuo.v2.db.Plan

class StudyRecordsAdapter: SingleTypeBaseRVAdapter<Plan, ItemStudyRecordBinding>() {
    override fun getLayoutResId(viewType: Int): Int {
       return R.layout.item_study_record
    }

    override fun onBindItem(binding: ItemStudyRecordBinding, item: Plan?, holder: RecyclerView.ViewHolder?) {
        binding.data=item
    }
}