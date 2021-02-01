package com.sheenhill.rusuo.v2.index

import android.content.Context
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.SharedElementCallback
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.sheenhill.common.base.MainActivityViewModel
import com.sheenhill.common.fragment.K_BaseJetpackFragment
import com.sheenhill.common.fragment.K_DataBindingConfig
import com.sheenhill.rusuo.BR
import com.sheenhill.rusuo.R
import com.sheenhill.rusuo.util.LogUtil

class ImageShowFragment:K_BaseJetpackFragment() {
    lateinit var viewModel:MainActivityViewModel
    lateinit var glideRequestManager:RequestManager
    override fun initViewModel() {
//        viewModel= getActivityViewModel(MainActivityViewModel::class.java)
    }

    override fun getDataBindingConfig(): K_DataBindingConfig {
        return K_DataBindingConfig(R.layout.fragment_image, BR.viewModel, getActivityViewModel(MainActivityViewModel::class.java))
                .addBindingParam(BR.glideRequestManager, glideRequestManager)
                .addBindingParam(BR.listener,Listener())
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        glideRequestManager = Glide.with(context)
                .applyDefaultRequestOptions(
                        RequestOptions().placeholder(R.drawable.svg_placeholder)
                                .diskCacheStrategy(DiskCacheStrategy.DATA))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        prepareSharedElementTransition()
       return mBinding.root
    }

    class Listener{
        fun back(view: View){
            LogUtil.i("ImageShowFragment.listener.back()")
            view.findNavController().navigateUp()
        }
    }

    /**
     * Prepares the shared element transition from and back to the grid fragment.kj\
     */
    private fun prepareSharedElementTransition() {
        val transition = TransitionInflater.from(context)
                .inflateTransition(R.transition.image_shared_element_transition)
        sharedElementEnterTransition = transition

        // A similar mapping is set at the GridFragment with a setExitSharedElementCallback.
        setEnterSharedElementCallback(
                object : SharedElementCallback() {
                    override fun onMapSharedElements(names: List<String>, sharedElements: MutableMap<String, View>) {
                        // Locate the image view at the primary fragment (the ImageFragment that is currently
                        // visible). To locate the fragment, call instantiateItem with the selection position.
                        // At this stage, the method will simply return the fragment at the position and will
                        // not create a new one.
                        val view = mBinding.root

                        // Map the first shared element name to the child ImageView.
                        sharedElements[names[0]] = view.findViewById(R.id.imageView)
                    }
                })
    }
}