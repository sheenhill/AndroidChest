package com.sheenhill.module_chest.custom_view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sheenhill.module_chest.R
import com.sheenhill.module_chest.databinding.FragmentCustomPagerIndicatorBinding
import com.stanza.pageindicator.PagerIndicatorFragment

class PagerIndicatorFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        val binding= FragmentCustomPagerIndicatorBinding.inflate(inflater, container, false)
        val fragmentTransaction = parentFragmentManager.beginTransaction()
        val pFragment1 = PagerIndicatorFragment(0)
        val pFragment2 = PagerIndicatorFragment(1)
        val pFragment3 = PagerIndicatorFragment(2)
        val pFragment4 = PagerIndicatorFragment(3)
        val pFragment5 = PagerIndicatorFragment(50)
        fragmentTransaction.add(R.id.fl1, pFragment1)
        fragmentTransaction.add(R.id.f2, pFragment2)
        fragmentTransaction.add(R.id.f3, pFragment3)
        fragmentTransaction.add(R.id.f4, pFragment4)
        fragmentTransaction.add(R.id.f5, pFragment5)
        fragmentTransaction.commit()

        pFragment1.setFragmentCallback { num: String? -> binding.infoPreview.text=num }
        pFragment2.setFragmentCallback { num: String? -> binding.infoPreview.text=num  }
        pFragment3.setFragmentCallback { num: String? -> binding.infoPreview.text=num  }
        pFragment4.setFragmentCallback { num: String? -> binding.infoPreview.text=num  }
        pFragment5.setFragmentCallback { num: String? -> binding.infoPreview.text=num  }

        return binding.root
    }
}