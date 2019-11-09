package com.example.roy.recycleviewtest.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.roy.recycleviewtest.R;
import com.example.roy.recycleviewtest.entity.BingPicBean;
import com.example.roy.recycleviewtest.util.LogUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BingPicAdapter extends RecyclerView.Adapter<BingPicAdapter.ViewHolder>{

    private Context mContext;
    private List<BingPicBean.ImagesBean> mList;

    private String url;
    private String copyrightStr;
    private String[] strArr;
    private int length0fLineOne;
    private String tempStr;
    private SpannableString spannableString;
    private ForegroundColorSpan colorSpan;

    private OnItemClickListener mOnItemClickListener;//给上层暴露接口

    public BingPicAdapter(Context mContext, List<BingPicBean.ImagesBean> list) {
        this.mContext = mContext;
        this.mList = list;
        LogUtil.i("BingPicAdapter");
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_bing_pic, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        if(mList.get(position).getUrl()!=null){
            url="https://www.bing.com"+mList.get(position).getUrl();
            copyrightStr=mList.get(position).getCopyright().trim();
            strArr=copyrightStr.split("\\(");
            length0fLineOne=strArr[0].length();
            tempStr=strArr[0]+"\n"+strArr[1].replace(")","");

            url=url.replaceFirst("1920x1080", "800x480");
            spannableString=new SpannableString(tempStr);
            colorSpan=new ForegroundColorSpan(Color.parseColor("#000000"));
            spannableString.setSpan(colorSpan,0,length0fLineOne, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            //样式text，参考链接 https://blog.csdn.net/u012551350/article/details/51722893

            Glide.with(mContext).load(url).into(holder.pic);
            holder.tvCopyright.setText(spannableString);
        }
        else
            LogUtil.i("pic.url=null");

        holder.pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickListener.onItemClick(view,position);
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


    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }
}
