package com.sheenhill.module_chest.study_plan

import androidx.recyclerview.widget.RecyclerView
import com.sheenhill.common.base.SingleTypeBaseRVAdapter
import com.sheenhill.module_chest.R
import com.sheenhill.module_chest.databinding.ItemStudyRecordBinding

class StudyRecordsAdapter : SingleTypeBaseRVAdapter<Plan, ItemStudyRecordBinding>() {

    override fun getLayoutResId(viewType: Int): Int {
        return R.layout.item_study_record
    }

    override fun onBindItem(binding: ItemStudyRecordBinding, item: Plan, holder: RecyclerView.ViewHolder?) {
        binding.data = item
    }
}
