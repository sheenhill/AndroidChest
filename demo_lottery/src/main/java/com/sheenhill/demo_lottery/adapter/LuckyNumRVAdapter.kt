package com.sheenhill.demo_lottery.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.sheenhill.common.base.BaseRVAdapter
import com.sheenhill.demo_lottery.JCrawlerViewModel
import com.sheenhill.demo_lottery.R
import com.sheenhill.demo_lottery.databinding.ItemLuckyNumberBinding
import com.sheenhill.demo_lottery.databinding.ItemLuckyNumberBtnBinding

class LuckyNumRVAdapter(var vm: JCrawlerViewModel) : BaseRVAdapter<List<String>>() {

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            dataList.size - 1 -> R.layout.item_lucky_number_btn
            else -> R.layout.item_lucky_number
        }
    }

    private var dataList = MutableList(7) { List(7) {"$it" } }


    override fun onCreateViewHolder(parent: ViewGroup, @LayoutRes viewType: Int)
            : RecyclerView.ViewHolder {
        val viewRoot = LayoutInflater.from(parent.context)
                .inflate(viewType, parent, false)
        return when (viewType) {
            R.layout.item_lucky_number -> {
                val binding = DataBindingUtil.bind<ItemLuckyNumberBinding>(viewRoot)
                LuckyNumVH.VH1(binding!!)
            }
            else -> {
                val binding = DataBindingUtil.bind<ItemLuckyNumberBtnBinding>(viewRoot)
                LuckyNumVH.VH2(binding!!)
            }
        }
    }

    override fun getItemCount(): Int = dataList.size

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            R.layout.item_lucky_number -> {
                val holder = holder as LuckyNumVH.VH1
                holder.binding.data = dataList[position]
//                holder.binding.executePendingBindings()
            }
            else -> {
                val holder = holder as LuckyNumVH.VH2
                holder.binding.button.setOnClickListener { view->
                    vm.updateLuckyNumByDB()
//                    run {
//                        list.add(getLuckyNums())
//                    }
                }
//                holder.binding.executePendingBindings()
            }
        }

    }

    override fun getList(): MutableList<List<String>> {
        return this.dataList
    }

    override fun setList(list: MutableList<List<String>>?) {
        this.dataList = list!!
    }


    sealed class LuckyNumVH() {
        data class VH1(val binding: ItemLuckyNumberBinding) : RecyclerView.ViewHolder(binding.root)
        data class VH2(val binding: ItemLuckyNumberBtnBinding) : RecyclerView.ViewHolder(binding.root)
    }


}