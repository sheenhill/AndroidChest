package com.sheenhill.demo_animator.main;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.sheenhill.demo_animator.R;
import com.sheenhill.demo_animator.adapter.TestAdapter;
import com.sheenhill.demo_animator.databinding.FragmentDemoAnimatorBinding;
import com.sheenhill.common.util.LogUtil;

import java.util.ArrayList;

public class AnimatorDemoFragment extends Fragment{
    FragmentDemoAnimatorBinding binding;
    String[] colors = new String[]{"#FF0000", "#FF7F00", "#FFFF00", "#00FF00", "#00FFFF", "#0000FF", "#8B00FF", "#FF0000", "#FF7F00", "#FFFF00", "#00FF00", "#00FFFF"};
    //        Color.parseColor("#009688")
    final boolean isL2R = true;
    private AnimatorSet s1, s2, s3, s4, s5, s6, s7,mapAS,pointAS;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_demo_animator, container, false);
        binding.setListener(new Listener());
        initCameraDistance();
        bindAnimator();



        new LinearSnapHelper().attachToRecyclerView(binding.rv);
//        binding.rv.setLayoutManager(new CenterLinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        TestAdapter adapter = new TestAdapter();
        ArrayList<String> arr = new ArrayList<>(20);
        for (int i = 0; i < 20; i++) arr.add(i + "");
        adapter.setList(arr);
//        binding.rv.setItemAnimator(new DefaultItemAnimator());
//        binding.rv.setItemAnimator(circleAnimator);
        binding.rv.setAdapter(adapter);
//        binding.rv.addItemDecoration(circleDecoration);
//        binding.rv.setItemAnimator(new CircleItemAnimator());
//        binding.rv.setOnScrollChangeListener();

        return binding.getRoot();
    }

    public class Listener{

        public void clickBtn(){
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
        }

        public void clickLeftBtn(){
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
        }

        public void clickRightBtn(){
            mapAS.start();
            pointAS.start();
        }
    }
    /* 调节相机高度 */
    private void initCameraDistance() {
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

    // 组件动画绑定代码
    private void bindAnimator(){
        mapAS = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(),
                R.animator.map_rotation_x);
        mapAS.setTarget(binding.ivMap);
        pointAS = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(),
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

    private Camera camera = new Camera();
    private Canvas canvas = new Canvas();

    private RecyclerView.ItemAnimator circleAnimator = new SimpleItemAnimator() {
        @Override
        public boolean animateRemove(RecyclerView.ViewHolder holder) {
            return false;
        }

        @Override
        public boolean animateAdd(RecyclerView.ViewHolder holder) {
            return false;
        }

        @Override
        public boolean animateMove(RecyclerView.ViewHolder holder, int fromX, int fromY, int toX, int toY) {
            LogUtil.d("animateMove>>>>>fromX,fromY,toX,toY:(" + fromX + "+" + fromY + "+" + toX + "+" + toY + ")");
            return true;
        }

        @Override
        public boolean animateChange(RecyclerView.ViewHolder oldHolder, RecyclerView.ViewHolder newHolder, int fromLeft, int fromTop, int toLeft, int toTop) {
            return false;
        }

        @Override
        public void runPendingAnimations() {

        }

        @Override
        public void endAnimation(@NonNull RecyclerView.ViewHolder item) {

        }

        @Override
        public void endAnimations() {

        }

        @Override
        public boolean isRunning() {
            return false;
        }
    };

    /* itemDecoration可以实现RV的3d效果，不过仅限简单效果。（绘制在画布上） */
    private RecyclerView.ItemDecoration circleDecoration = new RecyclerView.ItemDecoration() {

        @Override
        public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
//            super.onDraw(c, parent, state);
            LogUtil.d("DRAW.adapter.itemCount>>>>>" + parent.getAdapter().getItemCount());// 总
            LogUtil.d("DRAW.layoutManager.childCount>>>>>" + parent.getLayoutManager().getChildCount()); // 未被回收的
            LogUtil.d("DRAW.layoutManager.itemCount>>>>>" + parent.getLayoutManager().getItemCount());// 总
            Rect parentRect = new Rect(parent.getLeft(), parent.getTop(), parent.getRight(), parent.getBottom());
            int middleLineY = (int) ((parent.getRight() - parent.getLeft()) / 2);
            c.save();
            c.scale(4.7f, 1.8f);
            c.rotate(45, 0, 0);
//            camera.save();
//            camera.rotateY(-60);
//            camera.applyToCanvas(c);
//            camera.restore();
            c.restore();

//            LinearLayoutManager llm = (LinearLayoutManager) parent.getLayoutManager();
//            boolean isVertical = llm.getOrientation() == LinearLayoutManager.VERTICAL;//垂直与水平布局方式
//            Rect parentRect = new Rect(parent.getLeft(), parent.getTop(), parent.getRight(), parent.getBottom());
//            int startPosition = llm.findFirstVisibleItemPosition();
//            final int itemCount=parent.getLayoutManager().getItemCount();
//            if (startPosition < 0) return;
//            int endPosition = llm.findLastVisibleItemPosition();
////            hasCenterItem = false;
//            for (int itemPosition = startPosition; itemPosition <= endPosition; itemPosition++) {
//                if (itemPosition < itemCount) continue;//itemCount为空白项,不考虑
//                if (itemPosition >= llm.getItemCount() - itemCount) break;//超过列表的也是空白项
//                //Log.i("you", "onDraw currentItem... " + itemPosition);
//                View itemView = llm.findViewByPosition(itemPosition);
//                Rect itemRect = new Rect(itemView.getLeft(), itemView.getTop(), itemView.getRight(), itemView.getBottom());
//                if (isVertical) {//垂直布局, 还需要靠对齐方式
//                } else {//水平布局
//                    drawHorizontalItem(c, itemRect, itemPosition, parentRect.exactCenterX(), parentRect.exactCenterY(),itemCount);
//                }
//            }
        }

        /**
         * 画水平布局时的item
         * @param c
         * @param rect
         * @param position
         * @param parentCenterX RecyclerView的中心X点
         * @param parentCenterY RecyclerView的中心Y点
         */
        void drawHorizontalItem(Canvas c, Rect rect, int position, float parentCenterX, float parentCenterY, int itemCount) {

        }

        /**
         * 根据item的大小(弧的长度),和item对应的旋转角度,计算出滑轮轴的半径
         * @param radian
         * @param degree
         * @return
         */
        double radianToRadio(int radian, float degree) {
            return radian * 180d / (degree * Math.PI);
        }

        /**
         * 旋转大于90度时,完全透明
         * @param degree
         * @return
         */
        int degreeAlpha(float degree) {
            degree = Math.abs(degree);
            if (degree >= 90) return 0;
            float al = (90 - degree) / 90;
            return (int) (255 * al);
        }
    };

}
