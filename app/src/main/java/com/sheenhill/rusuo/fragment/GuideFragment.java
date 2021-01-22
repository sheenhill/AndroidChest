package com.sheenhill.rusuo.fragment;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.sheenhill.rusuo.MainActivity;
import com.sheenhill.rusuo.R;


public class GuideFragment extends Fragment implements View.OnClickListener {

    private int mResId;
    // 是否展示立即体验按钮
    private boolean mIsShowBtn;

    // 用于在外部创建Fragment的实例，而且可以传递参数
    public static GuideFragment newInstance(int resId) {
        Bundle args = new Bundle();
        args.putInt("resId", resId);
        GuideFragment fragment = new GuideFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mResId = getArguments()
                .getInt("resId");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.guide_fragment, container, false);
        ImageView guideImageView = (ImageView) rootView.findViewById(R.id.guide_image_view);
        guideImageView.setImageResource(mResId);
        if (mIsShowBtn) {
            Button startMainBtn = (Button) rootView.findViewById(R.id.start_main_btn);
            startMainBtn.setOnClickListener(this);
            // 设置可见
            startMainBtn.setVisibility(View.VISIBLE);
        }
        return rootView;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getContext(), MainActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    public void setShowBtn(boolean showBtn) {
        mIsShowBtn = showBtn;
    }
}
