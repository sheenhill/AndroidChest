package com.sheenhill.rusuo.adapter;

import android.annotation.SuppressLint;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sheenhill.rusuo.R;
import com.sheenhill.rusuo.entity.HoursPlan;

import java.util.List;


public class StudyPlanRViewAdapter extends RecyclerView.Adapter<StudyPlanRViewAdapter.ViewHolder> {
    private List<HoursPlan> mList;
    HoursPlan mhoursPlanData;

    public StudyPlanRViewAdapter(List<HoursPlan> list){
        this.mList=list;
    }
    @Override
    public int getItemCount() {
        return mList==null?0:mList.size();
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    public void onBindViewHolder(@NonNull final StudyPlanRViewAdapter.ViewHolder holder, int position) {
//        hourPlanAll=LitePal.findAll(HoursPlan.class);
        mhoursPlanData= mList.get(position);
        if(mhoursPlanData!=null){
            holder.tvStudyTime.setText(mhoursPlanData.getThisStudyTime());
//        LogUtil.i("getThisStudyTime");
            holder.tvResidualTime.setText(mhoursPlanData.getResidualTime());
//        LogUtil.i("getResidualTime");
            holder.tvTime.setText(mhoursPlanData.getFormatTime());
//        LogUtil.i("getTime");
        }
    }

    @NonNull
    public StudyPlanRViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
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
