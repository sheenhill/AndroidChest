package com.example.roy.recycleviewtest.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.roy.recycleviewtest.BR;
import com.example.roy.recycleviewtest.R;
import com.example.roy.recycleviewtest.databinding.FragmentLaboratoryBinding;

public class LaboratoryFragment extends Fragment {
    FragmentLaboratoryBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_laboratory, container, false);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_laboratory, container, false);
        binding.setVariable(BR.listener,new Listener());
        return binding.getRoot();
    }

    public class Listener{
        // 类型转换器
        public void toTypeSwitcher(){
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_laboratoryFragment_to_typeSwitcherFragment);
        }
        // 页面指示器
        public void toPagerIndication(){
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_laboratoryFragment_to_pageIndicatorFragment);
        }
        // 倒计时
        public void toCountdown(){
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_laboratoryFragment_to_countdownFragment);
        }
        // 爬虫
        public void toCrawler(){
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_laboratoryFragment_to_crawlerFragment);
        }
        // lottie动画
        public void toLottieMotion(){
//            Navigation.findNavController(binding.getRoot()).navigate(R.id.);
        }
    }


}
