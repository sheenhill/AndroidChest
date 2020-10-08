package com.stanza.pageindicator;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class PagerIndicatorFragment extends Fragment implements View.OnClickListener {

    private RecyclerView rv;
    private AppCompatImageButton btnPre;
    private AppCompatImageButton btnNext;

    private FragmentCallback fragmentCallback;

    private PageIndicatorAdapter adapter;
    private List<PageBean> mList;

    private int pageFlag = 0;// 上一个选择的页数下标标志

    public PagerIndicatorFragment(int pageNum) {
        if (pageNum <= 0) return;
        mList = new ArrayList<>();
        PageBean a;
        for (int i = 1; i < pageNum + 1; i++) {
            a = new PageBean();
            a.setPageNum(String.valueOf(i));
            a.setChecked(false);
            mList.add(a);
        }
        mList.get(0).setChecked(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page_indicator, container, false);
        rv=view.findViewById(R.id.rv);
        btnPre=view.findViewById(R.id.btn_pre);
        btnPre.setOnClickListener(this);
        btnNext=view.findViewById(R.id.btn_next);
        btnNext.setOnClickListener(this);
        rv.setLayoutManager(new CenterLinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        rv.addItemDecoration(new PageItemDecoration());
        new LinearSnapHelper().attachToRecyclerView(rv);
        adapter = new PageIndicatorAdapter(getActivity(), mList);
        rv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        btnNext.setImageDrawable(getActivity().getResources().getDrawable(hasNext() ?R.drawable.ic_next_666:R.drawable.ic_next_ccc));
        btnPre.setImageDrawable(getActivity().getResources().getDrawable(hasPre() ?R.drawable.ic_pre_666:R.drawable.ic_pre_ccc));
        initListener();
        return view;
    }

    private void initListener() {
        adapter.setOnItemClickListener((view, position) -> {
            mList.get(pageFlag).setChecked(false);
            adapter.notifyItemChanged(pageFlag);
            mList.get(position).setChecked(true);
            adapter.notifyItemChanged(position);
            pageFlag = position;// 通过pageFlag记录上一次点击item的位置，避免了遍历mList
            smoothScrollToItem(position);
            fragmentCallback.whichPage(mList.get(position).getPageNum().toString());
            btnNext.setImageDrawable(getActivity().getResources().getDrawable(hasNext() ?R.drawable.ic_next_666:R.drawable.ic_next_ccc));
            btnPre.setImageDrawable(getActivity().getResources().getDrawable(hasPre() ?R.drawable.ic_pre_666:R.drawable.ic_pre_ccc));
        });

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_pre) {
            toPre();
        } else if (id == R.id.btn_next) {
            toNext();
        }
    }

    private boolean hasPre() {
        return pageFlag != 0;
    }

    private void toPre() {
//            btnPre.setTextColor(getActivity().getResources().getColor(R.color.text_666));
        if (hasPre()) {
            mList.get(pageFlag).setChecked(false);
            adapter.notifyItemChanged(pageFlag);
            pageFlag--;
            mList.get(pageFlag).setChecked(true);
            adapter.notifyItemChanged(pageFlag);
            smoothScrollToItem(pageFlag);
            fragmentCallback.whichPage(mList.get(pageFlag).getPageNum().toString());
        }
        btnNext.setImageDrawable(getActivity().getResources().getDrawable(hasNext() ?R.drawable.ic_next_666:R.drawable.ic_next_ccc));
        btnPre.setImageDrawable(getActivity().getResources().getDrawable(hasPre() ?R.drawable.ic_pre_666:R.drawable.ic_pre_ccc));
//        if (!hasPre()){
//            btnPre.setTextColor(getActivity().getResources().getColor(R.color.ccc));
//        }
    }

    private boolean hasNext() {
        if (mList == null) return false;
        return pageFlag != mList.size() - 1;
    }

    private void toNext() {
//            btnNext.setTextColor(getActivity().getResources().getColor(R.color.text_666));
        if (hasNext()) {
            mList.get(pageFlag).setChecked(false);
            adapter.notifyItemChanged(pageFlag);
            pageFlag++;
            mList.get(pageFlag).setChecked(true);
            adapter.notifyItemChanged(pageFlag);
            smoothScrollToItem(pageFlag);
            fragmentCallback.whichPage(mList.get(pageFlag).getPageNum().toString());
        }
        btnNext.setImageDrawable(getActivity().getResources().getDrawable(hasNext() ?R.drawable.ic_next_666:R.drawable.ic_next_ccc));
        btnPre.setImageDrawable(getActivity().getResources().getDrawable(hasPre() ?R.drawable.ic_pre_666:R.drawable.ic_pre_ccc));

//        if (!hasNext()){
//            btnNext.setTextColor(getActivity().getResources().getColor(R.color.ccc));
//        }
    }

    /* 滚动到position*/
    private void smoothScrollToItem(int position) {
        rv.getLayoutManager().smoothScrollToPosition(rv, new RecyclerView.State(), position);
    }



    /**
     * 回调page值
     */
    public interface FragmentCallback {
        void whichPage(String num);
    }

    public void setFragmentCallback(FragmentCallback fragmentCallback) {
        this.fragmentCallback = fragmentCallback;
    }
}

/**
 * rv  itemDecoration
 * 给第一个item左边和最后一个item右边加一个padding(等于一个view长度的padding)
 */
class PageItemDecoration extends RecyclerView.ItemDecoration {
    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position = parent.getChildAdapterPosition(view);
        final int count = parent.getLayoutManager().getItemCount();
        if (position == 0) {
            outRect.set(parent.getLayoutManager().getWidth() / 3, 0, 0, 0);
        } else if (position == count - 1) {
            outRect.set(0, 0, parent.getLayoutManager().getWidth() / 3, 0);
        }
        if (position != 0 && position != count - 1) return;
    }
}

/**
 * rv layoutManager   重写LinearLayoutManager
 * smoothScrollToPosition滚动的目标都在最中间
 */
class CenterLinearLayoutManager extends LinearLayoutManager {
    public CenterLinearLayoutManager(Context context) {
        super(context);
    }

    public CenterLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public CenterLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
        RecyclerView.SmoothScroller smoothScroller = new CenterSmoothScroller(recyclerView.getContext());
        smoothScroller.setTargetPosition(position);
        startSmoothScroll(smoothScroller);
    }

    @Override
    public void scrollToPosition(int position) {
        super.scrollToPosition(position);
    }

    private static class CenterSmoothScroller extends LinearSmoothScroller {

        public CenterSmoothScroller(Context context) {
            super(context);
        }

        @Override
        public int calculateDtToFit(int viewStart, int viewEnd, int boxStart, int boxEnd, int snapPreference) {
            return (boxStart + (boxEnd - boxStart) / 2) - (viewStart + (viewEnd - viewStart) / 2);
        }

        @Override
        protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
            return 100f / displayMetrics.densityDpi;
        }
    }

}
