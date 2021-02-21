package com.sheenhill.module_chest.study_plan

import android.view.WindowManager
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.sheenhill.common.base.BaseDialog
import com.sheenhill.module_chest.R
import com.sheenhill.module_chest.databinding.DialogAddRecordBinding

class AddRecordDialog : BaseDialog<DialogAddRecordBinding, AddRecordDialog>() {
    lateinit var viewModel: StudyPlanViewModel

    override fun getLayoutId(): Int {
        return R.layout.dialog_add_record
    }

    override fun setLayout(params: WindowManager.LayoutParams): WindowManager.LayoutParams {
        super.setLayout(params)
        params.width = dp2px(210)
        return params
    }

    override fun initData(binding: DialogAddRecordBinding) {
        super.initData(binding)
        val factory:ViewModelProvider.Factory=ViewModelProvider.NewInstanceFactory()
        val owner:ViewModelStoreOwner=findNavController().getViewModelStoreOwner(R.id.nav_module_chest)
        viewModel=ViewModelProvider(owner,factory)[StudyPlanViewModel::class.java]
        binding.listener= Listener()
        binding.viewModel=viewModel
        binding.nav=NavHostFragment.findNavController(this);
        viewModel.currentRecordH.value="00"
        viewModel.currentRecordM.value="00"
        viewModel.currentRecordNote.value=""
//        viewModel: StudyPlanViewModel by navGraphViewModels(R.id.nav_module_chest) { factory }
//        viewModel= ViewModelProvider(Stud)[StudyPlanViewModel::class.java]
    }

    class Listener(){
        fun addOneRecord(nav:NavController, viewModel: StudyPlanViewModel){
            viewModel.addPlanRecord()
            nav.navigateUp()
        }
    }
}