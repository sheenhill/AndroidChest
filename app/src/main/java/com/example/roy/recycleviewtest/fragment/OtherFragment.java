package com.example.roy.recycleviewtest.fragment;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.roy.recycleviewtest.R;
import com.example.roy.recycleviewtest.activity.CalculatorActivity;
import com.example.roy.recycleviewtest.activity.StudyActivity;
import com.example.roy.recycleviewtest.activity.TensorFlowLiteActivity;
import com.example.roy.recycleviewtest.util.ToastUtils;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OtherFragment extends Fragment implements View.OnClickListener {
    @BindView(R.id.btn_study_plan)
    Button btnStudyPlan;
    @BindView(R.id.btn_calculator)
    Button btnCalculator;
    @BindView(R.id.btn_tf_lite_test)
    Button btnTfLiteTest;
    @BindView(R.id.btn_hold)
    Button btnHold;
//    @BindView(R.id.tv_study_plan)
//    TextView tvStudyPlan;
//    @BindView(R.id.tv_calculator)
//    TextView tvCalculator;
//    @BindView(R.id.tv_tf_lite_test)
//    TextView tvTfLiteTest;
//    @BindView(R.id.tv_hold)
//    TextView tvHold;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_other, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        btnCalculator.setOnClickListener(this);
        btnStudyPlan.setOnClickListener(this);
        btnTfLiteTest.setOnClickListener(this);
        btnHold.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_study_plan:
                StudyActivity.actionStart(getActivity(), StudyActivity.class);
                //隐式intent
//                Intent intent = new Intent("com.example.roy.recycleviewtest.STUDY_START");
//                intent.addCategory("com.example.roy.recycleviewtest.STUDY_ACTIVITY");
////                intent.putExtra("study_plan", (Serializable) sb);//todo
////                intent.putExtra("study_plan", (Serializable) "123");
//                //按照Alibaba android开发手册所规范：
//                //Activity 间通过隐式 Intent 的跳转，在发出 Intent 之前必须通过 resolveActivity 检查，
//                //避免找不到合适的调用组件，造成 ActivityNotFoundException 的异常。
//                if (getActivity().getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY) != null)
//                    startActivityForResult(intent, 5);
//                else
//                    ToastUtils.showShort(getActivity(), "该功能暂时未开通");
                break;
            case R.id.btn_calculator:
                Intent intent1 = new Intent(getActivity(), CalculatorActivity.class);
//                intent1.intent1putExtra("tool_title", tvTwo.getText());
                startActivity(intent1);
                break;
            case R.id.btn_tf_lite_test:
                //静态方法，直接用类使用方法
                TensorFlowLiteActivity.actionStart(getActivity(), TensorFlowLiteActivity.class);
                break;
            default:
                break;

        }

    }
}
