package com.sheenhill.animatordemo.rv_widget;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CircleLayoutManager extends RecyclerView.LayoutManager {

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public boolean canScrollHorizontally() {
        return true;
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (getItemCount() <= 0) {
            return;
        }
        if (state.isPreLayout()) {
            return;
        }
        detachAndScrapAttachedViews(recycler);
        int itemLeft = getPaddingLeft();
        for (int i = 0; ; i++) {
            if (itemLeft >= getWidth() - getPaddingRight())
                break;
            View itemView = recycler.getViewForPosition(i % getItemCount());
//            itemView.setScaleY(0.5f);
            // 添加子View
            addView(itemView);
            // 测量
            measureChildWithMargins(itemView, 0, 0);

            int right = itemLeft + getDecoratedMeasuredWidth(itemView);
            int top = getPaddingTop();
            int bottom = top + getDecoratedMeasuredHeight(itemView) - getPaddingBottom();
            // 布局
            itemView.setScaleY(0.8f);
            itemView.setScaleX(0.8f);
            layoutDecorated(itemView, itemLeft, top, right, bottom);
            itemLeft = right;
        }
    }

    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        fill(recycler, dx > 0);
        offsetChildrenHorizontal(-dx);
        recyclerChildView(dx > 0, recycler);
        return dx;
    }

    // 滑动时填充可见的未填充的空间
    private void fill(RecyclerView.Recycler recycler, boolean fillEnd) {
        if (getChildCount() == 0) return;
        if (fillEnd) {
            // 填充尾部
            View anchorView = getChildAt(getChildCount() - 1);   // anchorView:当前视图可见的最后一个View，锚点View
            int anchorPosition = getPosition(anchorView);  // anchorPosition:锚点View下标
//            for (; anchorView.getRight() < getWidth() - getPaddingRight(); ) { // 当锚点View右侧边界到RV最右边时，循环
            for (; anchorView.getRight() < getWidth() - getPaddingRight(); ) { // 此处可增加提前绘制区域，数据无法提前赋予
                int position = (anchorPosition + 1) % getItemCount();
                if (position < 0) position += getItemCount();

                //scarp：废品
                View scrapItem = recycler.getViewForPosition(position);
                addView(scrapItem);
                measureChildWithMargins(scrapItem, 0, 0);

                int left = anchorView.getRight();
                int top = getPaddingTop();
                int right = left + getDecoratedMeasuredWidth(scrapItem);
                int bottom = top + getDecoratedMeasuredHeight(scrapItem) - getPaddingBottom();
                layoutDecorated(scrapItem, left, top, right, bottom);
                anchorView = scrapItem;
            }
        } else {
            // 填充首部
            View anchorView = getChildAt(0);
            int anchorPosition = getPosition(anchorView);
//            for (; anchorView.getLeft() > getPaddingLeft()-getWidth(); ) {
            for (; anchorView.getLeft() > getPaddingLeft(); ) {
                int position = (anchorPosition - 1) % getItemCount();
                if (position < 0) position += getItemCount();

                View scrapItem = recycler.getViewForPosition(position);
                addView(scrapItem, 0);
                measureChildWithMargins(scrapItem, 0, 0);
                int left = anchorView.getLeft() - getDecoratedMeasuredWidth(scrapItem);
                int top = getPaddingTop();
                int right = anchorView.getLeft();
                int bottom = top + getDecoratedMeasuredHeight(scrapItem) - getPaddingBottom();
                layoutDecorated(scrapItem, left, top, right, bottom);
                anchorView = scrapItem;
            }
        }
    }

    /**
     * 一个View只是暂时被清除掉，稍后立刻就要用到，使用detach。它会被缓存进scrapCache的区域。
     * 一个View 不再显示在屏幕上，需要被清除掉，并且下次再显示它的时机目前未知 ，使用remove。
     * 它会被以viewType分组，缓存进RecyclerViewPool里。
     *
     * 注意：一个View只被detach，没有被recycle的话，不会放进RecyclerViewPool里，会一直存在recycler的scrap 中。
     * 网上有人的Demo就是如此，因此View也没有被复用，有多少ItemCount，就会new出多少个ViewHolder。
     */

    // 回收不可见的子View
    private void recyclerChildView(boolean fillEnd, RecyclerView.Recycler recycler) {
        if (fillEnd) {
            // 回收头部
            for (int i = 0; ; i++) {
                View view = getChildAt(i);
                boolean needRecycler = view != null && view.getRight() < getPaddingLeft();
                if (needRecycler) {
                    removeAndRecycleView(view, recycler);
                } else {
                    return;
                }
            }
        } else {
            //回收尾部
            for (int i = getChildCount() - 1; ; i--) {
                View view = getChildAt(i);
                boolean needRecycler = view != null && view.getLeft() > getWidth() - getPaddingRight();
                if (needRecycler) {
                    removeAndRecycleView(view, recycler);
                } else {
                    return;
                }
            }
        }
    }
}
