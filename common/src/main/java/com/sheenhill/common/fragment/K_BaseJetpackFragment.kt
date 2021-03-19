package com.sheenhill.common.fragment

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController

/**
 * JetPack库下的准MVVM规范的BaseFragment
 */
abstract class K_BaseJetpackFragment : Fragment() {
    protected lateinit var mActivity: FragmentActivity

    /**
     * TODO tip: 警惕使用。非必要情况下，尽可能不在子类中拿到 binding 实例乃至获取 view 实例。使用即埋下隐患。
     * 目前的方案是在 debug 模式下，对获取实例的情况给予提示。
     */
    protected lateinit var mBinding: ViewDataBinding
    protected val mFragmentProvider: ViewModelProvider by lazy { ViewModelProvider(this) } // fragment级VMP
    protected val mActivityProvider: ViewModelProvider by lazy { ViewModelProvider(mActivity) } // Activity级VMP

    protected var hasStatusBarHeight=false

    // 此方法之后执行onCreate
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as FragmentActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
        hasStatusBarHeight=hasStatusBarHeight()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val dataBindingConfig: K_DataBindingConfig = getDataBindingConfig()
        /**
         * tip: DataBinding 严格模式：
         * 将 DataBinding 实例限制于 base 页面中，默认不向子类暴露，
         * 通过这样的方式，来彻底解决 视图调用的一致性问题，
         */
        mBinding = DataBindingUtil.inflate(inflater, dataBindingConfig.layout, container, false)
        mBinding.lifecycleOwner = this //fixme: lifecycleOwner持有表示livedata当前被持有的范围,这个范围可以不是自己，可以是有关的fragment、activity(这个在单A模式中应该不存在)等
        mBinding.setVariable(dataBindingConfig.vmVariableId, dataBindingConfig.stateViewModel) // fixme:继承这个baseFragment的fragment，管理其数据的viewModel，在data binding中  viewModel的声明都必须是  name=viewModel
        val bindingParams = dataBindingConfig.bindingParams
        for (i in 0 until bindingParams.size()) {
            mBinding.setVariable(bindingParams.keyAt(i), bindingParams.valueAt(i))
        }
        if(hasStatusBarHeight){
            val resourceId: Int = mActivity.resources.getIdentifier("status_bar_height", "dimen", "android")
            val statusBarHeight: Int = mActivity.resources.getDimensionPixelSize(resourceId)
            mBinding.root.setPadding(0,statusBarHeight,0,0)
        }
        return mBinding.root
    }

    /* 为顶部状态栏留高度 */
    protected open fun hasStatusBarHeight():Boolean=false


    /**
     * 加载view model
     */
    protected abstract fun initViewModel()

    /**
     * 给data binding中的变量赋值
     */
    protected abstract fun getDataBindingConfig(): K_DataBindingConfig


    /**
     * 获取Fragment级ViewModel,用作单fragment数据来源
     */
    protected open fun <T : ViewModel> getFragmentViewModel(modelClass: Class<T>): T {
        return mFragmentProvider[modelClass]
    }

    /**
     * 获取navigation级ViewModel,用作模块级数据来源和多fragment间通信
     */
    protected open fun <T : ViewModel> getNavigationViewModel(modelClass: Class<T>,@IdRes navId:Int):T{
        val factory: ViewModelProvider.Factory= ViewModelProvider.NewInstanceFactory()
        val owner: ViewModelStoreOwner =findNavController().getViewModelStoreOwner(navId)
        return ViewModelProvider(owner,factory)[modelClass]
    }

    /**
     * 获取Activity级ViewModel，用作APP级数据来源和多fragment间通信
     */
    fun <T : ViewModel> getActivityViewModel(modelClass: Class<T>): T {
        return mActivityProvider[modelClass]
    }

    protected fun nav(): NavController {
        return NavHostFragment.findNavController(this);
    }

}