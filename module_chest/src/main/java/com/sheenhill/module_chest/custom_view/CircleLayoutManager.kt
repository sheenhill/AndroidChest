package com.sheenhill.module_chest.custom_view

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler

class CircleLayoutManager : RecyclerView.LayoutManager() {
    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

//    override fun canScrollHorizontally(): Boolean {
//        return true
//    }

    override fun canScrollVertically(): Boolean {
        return true
    }

    override fun onLayoutChildren(recycler: Recycler, state: RecyclerView.State) {
        if (itemCount <= 0) {
            return
        }
        if (state.isPreLayout) {
            return
        }
        detachAndScrapAttachedViews(recycler)
//        var itemLeft = paddingLeft
        var itemTop=paddingTop
        var i = 0
        while (true) {
            if(itemTop>=height-paddingBottom) break
            val itemView = recycler.getViewForPosition(i % itemCount)
            // 添加子View
            addView(itemView)
            // 测量
            measureChildWithMargins(itemView, 0, 0)

            val left= paddingLeft
            val right = left + getDecoratedMeasuredWidth(itemView)-paddingRight
            val bottom = itemTop + getDecoratedMeasuredHeight(itemView)
            // 布局
//            itemView.scaleY = 0.8f
//            itemView.scaleX = 0.8f
            layoutDecorated(itemView, left, itemTop, right, bottom)
            itemTop = bottom
            i++
        }
    }

//    override fun scrollHorizontallyBy(dx: Int, recycler: Recycler, state: RecyclerView.State): Int {
//        fill(recycler, dx > 0)
//        offsetChildrenHorizontal(-dx)
//        recyclerChildView(dx > 0, recycler)
//        return dx
//    }

    override fun scrollVerticallyBy(dy: Int, recycler: Recycler, state: RecyclerView.State): Int {
        fill(recycler, dy > 0)
        offsetChildrenVertical(-dy)
        recyclerChildView(dy > 0, recycler)
        return dy
    }

    // 滑动时填充可见的未填充的空间
    private fun fill(recycler: Recycler, fillEnd: Boolean) {
        if (childCount == 0) return
        if (fillEnd) {
            // 填充尾部
            var anchorView = getChildAt(childCount - 1) // anchorView:当前视图可见的最后一个View，锚点View
            val anchorPosition = getPosition(anchorView!!) // anchorPosition:锚点View下标
            //            for (; anchorView.getRight() < getWidth() - getPaddingRight(); ) { // 当锚点View右侧边界到RV最右边时，循环
            while (anchorView!!.bottom < height - paddingBottom) {
                // 此处可增加提前绘制区域，数据无法提前赋予
                var position = (anchorPosition + 1) % itemCount
                if (position < 0) position += itemCount

                //scarp：废品
                val scrapItem = recycler.getViewForPosition(position)
                addView(scrapItem)
                measureChildWithMargins(scrapItem, 0, 0)
                val left = paddingLeft
                val top = anchorView.bottom
                val right = left + getDecoratedMeasuredWidth(scrapItem)-paddingRight
                val bottom = anchorView.bottom + getDecoratedMeasuredHeight(scrapItem)
                layoutDecorated(scrapItem, left, top, right, bottom)
                anchorView = scrapItem
            }
        } else {
            // 填充首部
            var anchorView = getChildAt(0)
            val anchorPosition = getPosition(anchorView!!)
            //            for (; anchorView.getLeft() > getPaddingLeft()-getWidth(); ) {
//            while (anchorView!!.left > paddingLeft) {
            while (anchorView!!.top > paddingTop) {
                var position = (anchorPosition - 1) % itemCount
                if (position < 0) position += itemCount
                val scrapItem = recycler.getViewForPosition(position)
                addView(scrapItem, 0)
                measureChildWithMargins(scrapItem, 0, 0)
//                val left = anchorView.left - getDecoratedMeasuredWidth(scrapItem)
                val left = paddingLeft
//                val top = paddingTop
                val top = anchorView.top-getDecoratedMeasuredHeight(scrapItem)
//                val right = anchorView.left
                val right = left+getDecoratedMeasuredWidth(scrapItem)-paddingRight
//                val bottom = top + getDecoratedMeasuredHeight(scrapItem) - paddingBottom
                val bottom = anchorView.top
                layoutDecorated(scrapItem, left, top, right, bottom)
                anchorView = scrapItem
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
    private fun recyclerChildView(fillEnd: Boolean, recycler: Recycler) {
        if (fillEnd) {
            // 回收头部
            var i = 0
            while (true) {
                val view = getChildAt(i)
                val needRecycler = view != null && view.bottom < paddingTop
                if (needRecycler) {
                    removeAndRecycleView(view!!, recycler)
                } else {
                    return
                }
                i++
            }
        } else {
            //回收尾部
            var i = childCount - 1
            while (true) {
                val view = getChildAt(i)
                val needRecycler = view != null && view.top > height - paddingBottom
                if (needRecycler) {
                    removeAndRecycleView(view!!, recycler)
                } else {
                    return
                }
                i--
            }
        }
    }
}