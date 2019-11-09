package com.example.roy.recycleviewtest.adapter;

import android.annotation.SuppressLint;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.roy.recycleviewtest.R;
import com.example.roy.recycleviewtest.entity.HoursPlan;
import com.example.roy.recycleviewtest.util.LogUtil;

import java.text.SimpleDateFormat;
import java.util.List;


public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {
    private List<HoursPlan> mList;

    public MyRecyclerViewAdapter(List<HoursPlan> list){
        this.mList=list;
    }
    @Override
    public int getItemCount() {
        return mList.size();
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    public void onBindViewHolder(@NonNull final MyRecyclerViewAdapter.ViewHolder holder, final int position) {
//        hourPlanAll=LitePal.findAll(HoursPlan.class);
        HoursPlan mhoursPlanData=mList.get(holder.getAdapterPosition());
        String getThisStudyTime = String.valueOf(mhoursPlanData.getThisStudyTime());
        String getResidualTime = String.valueOf(mhoursPlanData.getResidualTime());
        SimpleDateFormat timeSDF = new SimpleDateFormat("MM月dd日 HH:mm");
        String getTime = timeSDF.format(mhoursPlanData.getTime());
        holder.tvStudyTime.setText(getThisStudyTime);
        LogUtil.i("getThisStudyTime");
        holder.tvResidualTime.setText(getResidualTime);
        LogUtil.i("getResidualTime");
        holder.tvTime.setText(getTime);
        LogUtil.i("getTime");
    }

    @NonNull
    public MyRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card,parent,false);
        return new ViewHolder(view);
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        final TextView tvStudyTime;
        final TextView tvResidualTime;
        final TextView tvTime;
        ViewHolder(View itemView){
            super(itemView);
            tvStudyTime=itemView.findViewById(R.id.item_study_time);
            tvResidualTime=itemView.findViewById(R.id.item_residual_time);
            tvTime=itemView.findViewById(R.id.time);
        }
    }

    /**
     * 插入一条数据
     * @param index 下标
     */
    public void addItem(int index ,HoursPlan hp){
//        if(hoursPlanList!=null){   }; 无须在此处做非空判断，在activity中获取HourPlan实例时做为空处理（从源头处理）
        mList.add(index-1,hp);
        notifyItemInserted(index);
    }


    public void addAll(List<HoursPlan> list) {
        int lastIndex = this.mList.size();
        if (this.mList.addAll(list)) {
            notifyItemRangeInserted(lastIndex, list.size());
        }
    }
}
