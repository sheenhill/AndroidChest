package com.sheenhill.module_chest.study_plan

import com.sheenhill.common.base.KT_BaseDiffCallback
import com.sheenhill.module_chest.study_plan.db.PlanRecord

class StudyPlanDiff : KT_BaseDiffCallback<PlanRecord>() {
    private lateinit var oldList: List<PlanRecord>
    private lateinit var newList: List<PlanRecord>
    override fun setOldList(list: List<PlanRecord>?) {
        if (list != null)
            oldList=list
        else
            oldList= emptyList()
    }

    override fun setNewList(list: List<PlanRecord>?) {
        if (list != null)
            newList=list
        else
            newList= emptyList()
    }

    override fun getOldListSize(): Int {
        return oldList.size
    }
    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
//        return oldList[oldItemPosition] == newList[newItemPosition]
        return oldList[oldItemPosition].plan.uid == newList[newItemPosition].plan.uid
//        return true
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
//        return oldList[oldItemPosition].plan.uid == newList[oldItemPosition].plan.uid
//        return oldList[oldItemPosition].mottoContent == newList[oldItemPosition].mottoContent
        return true
    }

}