package com.sheenhill.common.fragment

import android.util.SparseArray
import androidx.lifecycle.ViewModel


/**
 *  tip:
 *  将 DataBinding 实例限制于 base 页面中，默认不向子类暴露，
 *  通过这样的方式，来彻底解决 视图调用的一致性问题，
 *  而 DataBindingConfig 就是在这样的背景下，用于为 base 页面中的 DataBinding 提供绑定项。
 */
class K_DataBindingConfig (val layout: Int, val vmVariableId: Int, val stateViewModel: ViewModel) {
    val bindingParams=SparseArray<Any>()

    open fun addBindingParam(variableId: Int, any: Any): K_DataBindingConfig? {
        if (bindingParams[variableId] == null) {
            bindingParams.put(variableId,any)
        }
        return this
    }
}