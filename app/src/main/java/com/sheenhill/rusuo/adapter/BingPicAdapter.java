package com.sheenhill.rusuo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sheenhill.rusuo.R;
import com.sheenhill.rusuo.entity.BingPicBean;
import com.sheenhill.rusuo.interfaces.ItemClickListener;
import com.sheenhill.rusuo.util.LogUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BingPicAdapter extends RecyclerView.Adapter<BingPicAdapter.ViewHolder>{

    private Context mContext;
    private List<BingPicBean.ImagesBean> mList;


    private ItemClickListener mItemClickListener;//回调接口

    public BingPicAdapter(Context mContext, List<BingPicBean.ImagesBean> list) {
        this.mContext = mContext;
        this.mList = list;
        LogUtil.i("BingPicAdapter");
    }

    //显示不一样的item样式
    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_bing_pic, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

//
        Glide.with(mContext).load(mList.get(position).getUrl()).into(holder.pic);
        holder.tvCopyright.setText(mList.get(position).getNewcopyright());
//        }
//        else
//            LogUtil.i("pic.url=null");

        holder.pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mItemClickListener.onItemClick(view,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList!=null?mList.size():0;
    }


    static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.pic)
        ImageView pic;
        @BindView(R.id.tv_copyright)
        TextView tvCopyright;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


//    public interface OnItemClickListener {
//        void onItemClick(View view, int position);
//    }
    public void setOnItemClickListener(ItemClickListener onItemClickListener) {
        mItemClickListener = onItemClickListener;
    }
}
