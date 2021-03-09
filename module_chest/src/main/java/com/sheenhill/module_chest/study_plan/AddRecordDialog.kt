package com.sheenhill.module_chest.study_plan

import android.view.WindowManager
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.sheenhill.common.base.BaseDialog
import com.sheenhill.common.util.LogUtil
import com.sheenhill.module_chest.R
import com.sheenhill.module_chest.custom_view.MyWheelView
import com.sheenhill.module_chest.databinding.DialogAddRecordBinding
import kotlinx.android.synthetic.main.dialog_add_record.view.*
import kotlinx.android.synthetic.main.fragment_wheel_view.view.*

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
//        viewModel.currentRecordH.value="00"
//        viewModel.currentRecordM.value="00"
        viewModel.currentRecordNote.value=""
        binding.root.rv_h.setWheelListener(object : MyWheelView.WheelListener {
            override fun popValue(str: String) {

                LogUtil.d(" MyWheelView.WheelListener   h$str")
                viewModel.currentRecordH.set(str)
            }
        })
        binding.root.rv_m.setWheelListener(object : MyWheelView.WheelListener {
            override fun popValue(str: String) {
                LogUtil.d(" MyWheelView.WheelListener   m$str")
                viewModel.currentRecordM.set(str)
            }
        })
//        viewModel: StudyPlanViewModel by navGraphViewModels(R.id.nav_module_net) { factory }
//        viewModel= ViewModelProvider(Stud)[StudyPlanViewModel::class.java]
    }

    class Listener(){
        fun addOneRecord(nav:NavController, viewModel: StudyPlanViewModel){
            viewModel.addPlanRecord()
            nav.navigateUp()
        }
    }
}