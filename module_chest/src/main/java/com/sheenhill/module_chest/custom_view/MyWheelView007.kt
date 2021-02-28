package com.sheenhill.module_chest.custom_view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.sheenhill.common.util.LogUtil
import com.sheenhill.module_chest.R

class MyWheelView007 : RecyclerView {

    var mLabelList: List<String>

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        // 通过obtainStyledAttributes方法获取到TypedArray对象
        val a = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.MyWheelView,
                0, 0
        )
        var labels: String
        try {
            // 获取相应的属性值
            labels = (a.getString(R.styleable.MyWheelView_labels)) ?: ""
        } finally {
            // TypedArray是共享资源必须在使用后进行回收
            a.recycle()
        }
        mLabelList = if (labels.split(",").isNotEmpty()) labels.split(",") else arrayListOf("☰", "☵", "☶", "☳", "☴", "☲", "☷", "☱")
        initThis()
    }

    private fun initThis() {
        this.layoutManager = wheelLayoutManager
        this.adapter = WheelViewAdapter(mLabelList)
        LinearSnapHelper().attachToRecyclerView(this)
    }

    /**
     *  Adapter
     */
    class WheelViewAdapter(private val data: List<String>) : Adapter<VH>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_wheel_view, parent, false)
            return VH(view)
        }

        override fun onBindViewHolder(holder: VH, position: Int) {
            holder.tv.text = data[position]
        }

        override fun getItemCount(): Int {
            return data.size

        }
    }

    // 默认item样式
    class VH(itemView: View) : ViewHolder(itemView) {
        val tv: TextView = itemView.findViewById(R.id.tv)
    }

    /**
     * LayoutManager
     */
    private val wheelLayoutManager = object : RecyclerView.LayoutManager() {
        override fun generateDefaultLayoutParams(): LayoutParams {
            return LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        }

        override fun canScrollVertically(): Boolean {
            return true
        }

        // 初始化布局
        override fun onLayoutChildren(recycler: Recycler, state: RecyclerView.State) {
            if (itemCount <= 0) {
                return
            }
            LogUtil.d("onLayoutChildren>>>>>>$itemCount")
            if (state.isPreLayout) {
                return
            }
            detachAndScrapAttachedViews(recycler)
            var itemTop = paddingTop - height / 6
            var i = 0
            while (true) {
                if (itemTop >= height - paddingBottom) break
                var position = (i - 1) % itemCount
                position = if (position < 0) position + itemCount else position
                val itemView = recycler.getViewForPosition(position)
//                if(i=)
                // 添加子View
                addView(itemView)
                // 测量
                measureChildWithMargins(itemView, 0, 0)

                val left = paddingLeft
                val right = left + getDecoratedMeasuredWidth(itemView) - paddingRight
                val bottom = itemTop + getDecoratedMeasuredHeight(itemView)
                // 布局
                layoutDecorated(itemView, left, itemTop, right, bottom)
                itemTop = bottom
                i++
            }
            for (i in 0 until childCount) {
//                val item = getChildAt(i)!!
//                val percent = itemPercentage(item, height) - 0.5f  // 距中点的距离比例 [-0.5,0.5]
//                item.rotationX = percent * -90f
//                item.alpha = (-2.4f * Math.pow(percent.toDouble(), 2.0) + 1f).toFloat()
//                item.scaleX = (-0.8f * Math.pow(percent.toDouble(), 2.0) + 1f).toFloat()
//                item.scaleY = (-0.8f * Math.pow(percent.toDouble(), 2.0) + 1f).toFloat()
            }
        }

        override fun scrollVerticallyBy(dy: Int, recycler: Recycler, state: RecyclerView.State): Int {
            fill(recycler, dy > 0)
            offsetChildrenVertical(-dy)  // dy<0:向下滑动   dy>0:向上滑动
            for (i in 0 until childCount) {
                val item = getChildAt(i)!!
                val pp = itemPercentage(item).toDouble()
                LogUtil.d("scrollVerticallyBy  MyWheelView007 itemPercentage >>>>>>${pp} ")
//                val percent = itemPercentage(item, height) - 0.5f  // 距中点的距离比例 [-0.5,0.5]

//                item.pivotY=-10f
//                val scale = resources.displayMetrics.density * 160000
//                item.cameraDistance = scale //设置镜头距离
//                item.pivotY=-item.height/2f
////                item.translationY = (Math.pow(pp, 3.0) * -item.height / 5f).toFloat()
//                item.rotationX = (Math.pow(pp, 3.0) * -60).toFloat()
                item.alpha = (Math.abs(pp) * -0.5f + 1).toFloat()
                item.scaleX = (Math.abs(pp) * -0.1f + 1).toFloat()
                item.scaleY = (Math.abs(pp) * -0.1f + 1).toFloat()
                item.pivotY= if (pp>0) 1f else 0f // fixme:增加偏移，让组件看起来紧凑点  探究pivotY
//                item.translationY = (Math.pow(pp, 3.0) * -item.height / 5f).toFloat()
                item.rotationX = (Math.pow(pp, 3.0) * -60).toFloat()
            }
            recyclerChildView(dy > 0, recycler)
            return dy
        }

        //item的中点相对于RV中点位置的百分比= [-1,1]
        fun itemPercentage(item: View): Float {
//            return  ((item.height/2+item.top)-(height/2+paddingTop)).toFloat()/(height/2).toFloat()
            return (item.height / 2 + item.top - paddingTop).toFloat() / (height / 2).toFloat() - 1f
        }

        // 滑动时填充可见的未填充的空间
        private fun fill(recycler: Recycler, fillEnd: Boolean) {
            if (childCount == 0) return
            if (fillEnd) {
                // 填充尾部
                var anchorView = getChildAt(childCount - 1) // anchorView:当前视图可见的最后一个View，锚点View
                val anchorPosition = getPosition(anchorView!!) // anchorPosition:锚点View下标
                //            for (; anchorView.getRight() < getWidth() - getPaddingRight(); ) { // 当锚点View右侧边界到RV最右边时，循环
                while (anchorView!!.top < height - paddingBottom) {
                    // 此处可增加提前绘制区域，数据无法提前赋予
                    var position = (anchorPosition + 1) % itemCount
                    if (position < 0) position += itemCount

                    //scarp：废品
                    val scrapItem = recycler.getViewForPosition(position)
                    addView(scrapItem)
                    measureChildWithMargins(scrapItem, 0, 0)
                    val left = paddingLeft
                    val top = anchorView.bottom
                    val right = left + getDecoratedMeasuredWidth(scrapItem) - paddingRight
                    val bottom = anchorView.bottom + getDecoratedMeasuredHeight(scrapItem)
                    layoutDecorated(scrapItem, left, top, right, bottom)
                    anchorView = scrapItem
                }
            } else {
                // 填充首部
                var anchorView = getChildAt(0)
                val anchorPosition = getPosition(anchorView!!)
                while (anchorView!!.bottom > paddingTop) {
                    var position = (anchorPosition - 1) % itemCount
                    if (position < 0) position += itemCount
                    val scrapItem = recycler.getViewForPosition(position)
                    addView(scrapItem, 0)
                    measureChildWithMargins(scrapItem, 0, 0)
//                val left = anchorView.left - getDecoratedMeasuredWidth(scrapItem)
                    val left = paddingLeft
//                val top = paddingTop
                    val top = anchorView.top - getDecoratedMeasuredHeight(scrapItem)
//                val right = anchorView.left
                    val right = left + getDecoratedMeasuredWidth(scrapItem) - paddingRight
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

}