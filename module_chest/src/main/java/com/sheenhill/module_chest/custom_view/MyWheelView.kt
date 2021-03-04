package com.sheenhill.module_chest.custom_view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.sheenhill.common.util.LogUtil
import com.sheenhill.module_chest.R
import java.lang.Math.abs

class MyWheelView(context: Context, attrs: AttributeSet) : RecyclerView(context, attrs) {
    val TAG = "狮子头"
    var mLabelList: List<String>
    var mWheelListener: WheelListener? = null
    var mItemHeight: Int = 0

    init {
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
            mItemHeight = a.getDimension(R.styleable.MyWheelView_itemHeight, 0f).toInt()
        } finally {
            // TypedArray是共享资源必须在使用后进行回收
            a.recycle()
        }
        mLabelList = if (labels.split(",").isNotEmpty()) labels.split(",") else arrayListOf("☰", "☵", "☶", "☳", "☴", "☲", "☷", "☱")
    }

    init {
        val mLayoutManager = WheelLayoutManager(mItemHeight)
        this.layoutManager = mLayoutManager
        this.adapter = WheelViewAdapter(mLabelList, mItemHeight)
        this.addOnScrollListener(object : OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                LogUtil.d("(layoutManager as WheelLayoutManager).findCenterPosition()=${(layoutManager as WheelLayoutManager).findCenterPosition()}")
                val str = if ((layoutManager as WheelLayoutManager).findCenterPosition() == -1) "" else mLabelList[(layoutManager as WheelLayoutManager).findCenterPosition()]
                mWheelListener?.popValue(str)
                Log.d(TAG, "center view string=${str}")
            }
        })
        LinearSnapHelper().attachToRecyclerView(this)
    }

    fun setWheelListener(listener: WheelListener) {
        mWheelListener = listener
    }

    interface WheelListener {
        fun popValue(str: String)
    }

    /**
     *  Adapter
     */
    class WheelViewAdapter(private val data: List<String>, private val itemHeight: Int) : Adapter<VH>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_wheel_view, parent, false)
            return VH(view)
        }

        override fun onBindViewHolder(holder: VH, position: Int) {
            holder.tv.text = data[position]
            holder.tv.height = itemHeight
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
    private class WheelLayoutManager(val mItemHeight: Int) : RecyclerView.LayoutManager() {
        val TAG = "狮子头"
        var mCenterViewPosition = -1
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
            Log.d("TAG", "onLayoutChildren>>>>>>$itemCount")
            if (state.isPreLayout) {
                return
            }
            detachAndScrapAttachedViews(recycler)

            for (i in 0 until 3) {
                var position = (i - 1) % itemCount
                position = if (position < 0) position + itemCount else position
                val itemView = recycler.getViewForPosition(position)
                // 添加子View
                addView(itemView)
                // 测量
                measureChildWithMargins(itemView, 0, 0)
                val midLine = height * 0.5f
//                var top = paddingTop
                val left = paddingLeft
                val right = left + getDecoratedMeasuredWidth(itemView) - paddingRight
//                val bottom = itemTop + getDecoratedMeasuredHeight(itemView)
                // 布局
                when (i) {
                    0 -> {
                        layoutDecorated(itemView, left, (midLine - 1.5 * mItemHeight).toInt(), right, (midLine - 0.5 * mItemHeight).toInt())
                    }
                    1 -> {
                        layoutDecorated(itemView, left, (midLine - 0.5 * mItemHeight).toInt(), right, (midLine + 0.5 * mItemHeight).toInt())
                    }
                    2 -> {
                        layoutDecorated(itemView, left, (midLine + 0.5 * mItemHeight).toInt(), right, (midLine + 1.5 * mItemHeight).toInt())
                    }
                }
            }
            for (x in 0 until 3) {
                val item = getChildAt(x)!!
                val pp = itemPercentage(item).toDouble()
                Log.d(TAG, "pp>>>>>>$pp")
                if (pp <0.2&&pp>-0.2) mCenterViewPosition = getPosition(item)
                if(x!=1)
                item.alpha = 0f
            }
        }

        override fun scrollVerticallyBy(dy: Int, recycler: Recycler, state: RecyclerView.State): Int {
            fill(recycler, dy > 0)
            offsetChildrenVertical(-dy)  // dy<0:向下滑动   dy>0:向上滑动
            for (i in 0 until childCount) {
                val item = getChildAt(i)!!
                val pp = itemPercentage(item).toDouble()
                Log.d(TAG, "pp>>>>>>$pp")
                if (pp <0.2&&pp>-0.2) mCenterViewPosition = getPosition(item)
                item.alpha = (abs(pp) * -0.5f + 1).toFloat()
//                item.translationY = when {
//                    pp < -0.5 -> -item.height / 2f
//                    pp > 0.5 -> item.height / 2f
//                    else -> (-pp * item.height).toFloat()
//                }
                val transY = (pp * mItemHeight / 1.5f).toFloat()
                LogUtil.d("transY=$transY")
                item.translationY = transY
//                item.rotationX = (Math.pow(pp, 3.0) * -45).toFloat()
                item.rotationX = (pp * -60f).toFloat()
                item.translationY = -transY
                item.scaleX = (abs(pp) * -0.1f + 1).toFloat()
                item.scaleY = (abs(pp) * -0.1f + 1).toFloat()
//                item.translationY = when {
//                    pp < -0.5 -> item.height / 2f
//                    pp > 0.5 -> -item.height / 2f
//                    else -> (pp * item.height).toFloat()
//                }
            }
            recyclerChildView(dy > 0, recycler)
            return dy
        }

        fun findCenterPosition(): Int {
            return mCenterViewPosition
        }

        //item的中点相对于RV中点位置的百分比= [-1,1] , item中点到了边界才能是-1或者1
        fun itemPercentage(item: View): Float {
            val iml = mItemHeight * 0.5f + item.top
            val pml = height * 0.5f
            val psl = pml - 1.5f * mItemHeight
            // psl和pel之间的3*item.height范围就是[-1,1]
            val pp = (iml - psl) / (pml - psl) - 1f
            Log.d(TAG, "itemPercentage>>>>IML=$iml,PML-PSL=${pml - psl},pp=$pp")
            return when {
                pp < -1 -> -1f
                pp > 1 -> 1f
                else -> pp
            }
        }

        // 滑动时填充可见的未填充的空间
        private fun fill(recycler: Recycler, fillEnd: Boolean) {
            if (childCount == 0) return
            if (fillEnd) {
                // 填充尾部
                var anchorView = getChildAt(childCount - 1) // anchorView:当前视图可见的最后一个View，锚点View
                val anchorPosition = getPosition(anchorView!!) // anchorPosition:锚点View下标
                // 当锚点View顶部侧边界到RV最下边时，循环
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
                    val left = paddingLeft
                    val top = anchorView.top - getDecoratedMeasuredHeight(scrapItem)
                    val right = left + getDecoratedMeasuredWidth(scrapItem) - paddingRight
                    val bottom = anchorView.top
                    layoutDecorated(scrapItem, left, top, right, bottom)
                    anchorView = scrapItem
                }
            }
        }

        /**
         * 回收子View
         *
         * 一个View只是暂时被清除掉，稍后立刻就要用到，使用detach。它会被缓存进scrapCache的区域。
         * 一个View 不再显示在屏幕上，需要被清除掉，并且下次再显示它的时机目前未知 ，使用remove。
         * 它会被以viewType分组，缓存进RecyclerViewPool里。
         *
         * 注意：一个View只被detach，没有被recycle的话，不会放进RecyclerViewPool里，会一直存在recycler的scrap 中。
         * 网上有人的Demo就是如此，因此View也没有被复用，有多少ItemCount，就会new出多少个ViewHolder。
         */
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