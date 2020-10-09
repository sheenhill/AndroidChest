package com.sheenhill.animatordemo;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.sheenhill.animatordemo.databinding.FragmentDemoAnimatorBinding;

import java.util.Arrays;

public class AnimatorDemoFragment extends Fragment {
    FragmentDemoAnimatorBinding binding;
    String[] colors = new String[]{"#FF0000", "#FF7F00", "#FFFF00", "#00FF00", "#00FFFF", "#0000FF", "#8B00FF", "#FF0000", "#FF7F00", "#FFFF00", "#00FF00", "#00FFFF"};
    //        Color.parseColor("#009688")
    final boolean isL2R = true;
    AnimatorSet s1, s2, s3, s4, s5, s6, s7;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_demo_animator, container, false);
        initCameraDistance();
        AnimatorSet mapAS = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(),
                R.animator.map_rotation_x);
        mapAS.setTarget(binding.ivMap);
        AnimatorSet pointAS = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(),
                R.animator.point_scale);
        pointAS.setTarget(binding.ivPoint);
        if (isL2R) {
            s1 = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(),
                    R.animator.polyhedron_l2r_step1);
            s2 = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(),
                    R.animator.polyhedron_l2r_step2);
            s3 = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(),
                    R.animator.polyhedron_l2r_step3);
            s4 = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(),
                    R.animator.polyhedron_l2r_step4);
            s5 = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(),
                    R.animator.polyhedron_l2r_step5);
            s6 = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(),
                    R.animator.polyhedron_l2r_step6);
            s7 = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(),
                    R.animator.polyhedron_l2r_step7);
            setAnimatorTargets(s1, new View[]{binding.tv1, binding.tv2, binding.tv3, binding.tv4, binding.tv5, binding.tv6});
            setAnimatorTargets(s2, new View[]{binding.tv1, binding.tv2, binding.tv3, binding.tv4, binding.tv5});
//            setAnimatorTargets(s3,new View[]{binding.tv1,binding.tv2,binding.tv3,binding.tv4});
//            setAnimatorTargets(s4,new View[]{binding.tv1,binding.tv2,binding.tv3});
//            setAnimatorTargets(s5,new View[]{binding.tv1,binding.tv2});
//            setAnimatorTargets(s6,new View[]{binding.tv1});
        } else {
            s1 = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(),
                    R.animator.polyhedron_r2l_step1);
            s2 = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(),
                    R.animator.polyhedron_r2l_step2);
            s3 = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(),
                    R.animator.polyhedron_r2l_step3);
            s4 = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(),
                    R.animator.polyhedron_r2l_step4);
            s5 = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(),
                    R.animator.polyhedron_r2l_step5);
            s6 = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(),
                    R.animator.polyhedron_r2l_step6);
            s1.setTarget(binding.tv1);
            s2.setTarget(binding.tv2);
            s3.setTarget(binding.tv3);
            s4.setTarget(binding.tv4);
            s5.setTarget(binding.tv5);
            s6.setTarget(binding.tv6);
        }
        binding.button.setOnClickListener(v -> {
            if (isL2R) {
//                setEndAction(s6,s7);
//                setEndAction(s5,s6);
//                setEndAction(s4,s5);
//                setEndAction(s3,s4);
//                setEndAction(s2,s3);
                setEndAction(s1, s2);
                s1.start();
            } else {
                s1.start();
                s2.start();
                s3.start();
                s4.start();
                s5.start();
                s6.start();
            }
        });
        binding.btnLeft.setOnClickListener(v -> {
            s1.setTarget(binding.tv2);
            s2.setTarget(binding.tv3);
            s3.setTarget(binding.tv4);
            s4.setTarget(binding.tv5);
            s5.setTarget(binding.tv6);
            s6.setTarget(binding.tv1);
            s1.start();
            s2.start();
            s3.start();
            s4.start();
            s5.start();
            s6.start();
        });
        binding.btnRight.setOnClickListener(v -> {
            mapAS.start();
            pointAS.start();
        });
//        binding.rv.setLayoutManager(new CenterLinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        TestAdapter adapter=new TestAdapter();
        adapter.setList(Arrays.asList("AAAAAAAAAA".split("")));
        binding.rv.setAdapter(adapter);
        return binding.getRoot();
    }

    /* 调节相机高度 */
    private void initCameraDistance(){
        final int distance = 2000;// 这个是应该在1920左右
//        final int distance = 3000;
        final float scale = getResources().getDisplayMetrics().density * distance;
        binding.tv1.setCameraDistance(scale);
        binding.tv2.setCameraDistance(scale);
        binding.tv3.setCameraDistance(scale);
        binding.tv4.setCameraDistance(scale);
        binding.tv5.setCameraDistance(scale);
        binding.tv6.setCameraDistance(scale);
        binding.ivMap.setCameraDistance(scale);
        binding.ivPoint.setCameraDistance(scale);
    }

    private void setEndAction(AnimatorSet A1, AnimatorSet A2) {
        A1.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                A2.start();
            }
        });
    }

    private void setAnimatorTargets(AnimatorSet animationSet, View[] views) {
        for (View view : views) {
            animationSet.setTarget(view);
        }
    }
}
