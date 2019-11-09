package com.example.roy.recycleviewtest.activity;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.roy.recycleviewtest.R;
import com.example.roy.recycleviewtest.adapter.GuidePagerAdapter;
import com.example.roy.recycleviewtest.fragment.GuideFragment;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    protected ViewPager mGuideViewPager;

    // Fragment集合
    private List<GuideFragment> mFragmentList;
    // 小圆点集合
    private List<ImageView> mPointList;

    // 3个图片id
    private int[] imgIds = new int[]
            {R.mipmap.splash_pic_01, R.mipmap.splash_pic_02, R.mipmap.splash_pic_03};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guide_activity);
        initView();
    }

    private void initView() {
        mGuideViewPager = (ViewPager) findViewById(R.id.guide_view_pager);
        LinearLayout pointContainer = (LinearLayout) findViewById(R.id.point_container);

        mFragmentList = new ArrayList<>();
        mPointList = new ArrayList<>();

        // 布局参数，可以指定宽高、权重等信息
        // 视图所在容器是什么，就选择对应的LayoutParams
        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
        for (int imgId : imgIds) {// 3
            // 实例化Fragment
            GuideFragment guideFragment = GuideFragment.newInstance(imgId);
            mFragmentList.add(guideFragment);

            // 实例化小圆点
            ImageView pointImageView = new ImageView(this);
            pointImageView.setImageResource(R.drawable.selector_point);
            //TODO:selector_point UI绘制有问题
            // 给子控件设置布局参数
            pointImageView.setLayoutParams(params);
            // 向容器中添加子视图：小圆点
            pointContainer.addView(pointImageView);
            mPointList.add(pointImageView);
        }
        // 初始化第一个小圆点为选中状态，即白色
        mPointList.get(0).setSelected(true);
        // 最后一个碎片设置按钮可见
        mFragmentList.get(mFragmentList.size() - 1).setShowBtn(true);

        GuidePagerAdapter adapter =
                new GuidePagerAdapter(getSupportFragmentManager(), mFragmentList);

        mGuideViewPager.setAdapter(adapter);
        mGuideViewPager.addOnPageChangeListener(this);
        }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
// 遍历全部小圆点，当前展示页对应的点设置为白色，其他圆点一律设置为灰色
        for (int i = 0; i < mPointList.size(); i++) {
            mPointList.get(i).setSelected(i == position);
        }
        }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
