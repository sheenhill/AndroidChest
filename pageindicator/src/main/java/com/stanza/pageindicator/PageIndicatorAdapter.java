package com.stanza.pageindicator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PageIndicatorAdapter extends RecyclerView.Adapter<PageIndicatorAdapter.vHolder> {

    private List<PageBean> pageList;
    private Context mContext;
    private ItemClickListener mItemClickListener;// 点击回调接口

    public PageIndicatorAdapter(Context context, List<PageBean> list) {
        mContext = context;
        pageList = list;
    }

    @Override
    public void onBindViewHolder(@NonNull vHolder holder, final int position) {
        holder.infoGape.setText(pageList.get(position).getPageNum());
        holder.infoGape.setOnClickListener(v -> mItemClickListener.onItemClick(v,position));
    }

    @NonNull
    @Override
    public vHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_tv, parent, false);
        return new vHolder(view);
    }

    @Override
    public int getItemCount() {
        return pageList == null ? 0 : pageList.size();
    }

    static class vHolder extends RecyclerView.ViewHolder {
        private TextView infoGape;

        public vHolder(@NonNull View itemView) {
            super(itemView);
            infoGape=itemView.findViewById(R.id.info_gape);
        }
    }

    public void setOnItemClickListener(ItemClickListener onItemClickListener) {
        mItemClickListener = onItemClickListener;
    }

    public interface ItemClickListener{
        void onItemClick(View v, int position);
    }




}