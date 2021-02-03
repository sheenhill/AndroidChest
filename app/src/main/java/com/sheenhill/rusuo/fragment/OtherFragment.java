package com.sheenhill.rusuo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sheenhill.rusuo.R;
import com.sheenhill.rusuo.activity.CalculatorActivity;
import com.sheenhill.rusuo.activity.NewCustomViewActivity;
import com.sheenhill.rusuo.activity.TensorFlowLiteActivity;
import com.sheenhill.rusuo.adapter.OtherFragmentGVAdapter;
import com.sheenhill.rusuo.interfaces.ItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OtherFragment extends Fragment {

    @BindView(R.id.gv)
    GridView gv;
    private OtherFragmentGVAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_other, container, false);
        ButterKnife.bind(this, view);
        List<String> cateList = new ArrayList<>();
        cateList.add(getResources().getString(R.string.card_study_plan));
        cateList.add(getResources().getString(R.string.card_calculator));
        cateList.add(getResources().getString(R.string.card_TF_lite));
        cateList.add("组件实验室");
        cateList.add("下班倒计时");
        cateList.add("爬虫");
//        cateList.add(getResources().getString(R.string.card_TF_lite));
        adapter = new OtherFragmentGVAdapter(getActivity(), cateList);
        gv.setAdapter(adapter);
        initListener();
        return view;
    }

    private void initListener() {
        adapter.setOnItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                switch (position) {
                    case 0: // 学习计划页面
                        //隐式intent
//                Intent intent = new Intent("com.example.recycleviewtest.STUDY_START");
//                intent.addCategory("com.example.recycleviewtest.STUDY_ACTIVITY");
////                intent.putExtra("study_plan", (Serializable) sb);
////                intent.putExtra("study_plan", (Serializable) "123");
//                //按照Alibaba android开发手册所规范：
//                //Activity 间通过隐式 Intent 的跳转，在发出 Intent 之前必须通过 resolveActivity 检查，
//                //避免找不到合适的调用组件，造成 ActivityNotFoundException 的异常。
//                if (getActivity().getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY) != null)
//                    startActivityForResult(intent, 5);
//                else
//                    ToastUtils.showShort(getActivity(), "该功能暂时未开通");
                        break;
                    case 1: // 复利计算器页面
                        Intent intent1 = new Intent(getActivity(), CalculatorActivity.class);
//                intent1.intent1putExtra("tool_title", tvTwo.getText());
                        startActivity(intent1);
                        break;
                    case 2: // 人工智障识图页面
                        //静态方法，直接用类使用方法
                        TensorFlowLiteActivity.actionStart(getActivity(), TensorFlowLiteActivity.class);
                        break;
                    case 3: // 自定义view页面
//                        CustomViewActivity.actionStart(getActivity(), CustomViewActivity.class)
                        Intent intent2 = new Intent(getActivity(), NewCustomViewActivity.class);
//                intent1.intent1putExtra("tool_title", tvTwo.getText());
                        startActivity(intent2);
                        break;
                    case 4: // 下班倒计时
//                        startActivity(new Intent(getActivity(), CountdownFragment.class));
                        break;
                    case 5: // 爬虫
//                        Intent intent = new Intent(getActivity(), CrawlerActivity.class);
//                        startActivity(intent);
                        break;
                }
            }
        });
    }

}
