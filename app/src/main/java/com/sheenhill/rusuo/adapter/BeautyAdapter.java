package com.sheenhill.rusuo.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.sheenhill.rusuo.R;
import com.sheenhill.rusuo.entity.GirlBean;
import com.sheenhill.rusuo.util.LogUtil;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者    没有感情的S686
 * 时间    2018/12/13 17:42
 * 文件    Beauty_12_13
 * 描述
 */
public class BeautyAdapter extends RecyclerView.Adapter<BeautyAdapter.ViewHolder> {

    private Context mContext;
    private List<GirlBean.ResultsBean> mList;

    public BeautyAdapter(Context mContext, List<GirlBean.ResultsBean> list) {
        this.mContext = mContext;
        this.mList = list;
        setImageScale();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.beauty_pic_item, parent,false);
        return new ViewHolder(view);
    }

    /**
     * layoutParams.width   imageView宽度（单位：px）
     * metrics.widthPixels  屏幕宽度（单位：px）
     * metrics.density      屏幕密度（单位：px）
     * 屏幕宽度（px）/密度=屏幕实际宽度（dp）
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        String url = mList.get(position).getUrl();
        final ViewGroup.LayoutParams layoutParams = viewHolder.picBeauty.getLayoutParams();
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();//描述关于显示器的一般信息，如大小、密度和字体缩放。
        wm.getDefaultDisplay().getMetrics(metrics);
        layoutParams.width=
                (int)(metrics.widthPixels-30*metrics.density)/2;//30为屏幕除2个imageView以外的空白之和
        if(mList.get(position).getScale()!=0)
            layoutParams.height=(int)(layoutParams.width/mList.get(position).getScale());
        Glide.with(mContext)
                .load(url)
                .transition(new DrawableTransitionOptions().crossFade())    //过渡动画，防止图片变形
                .into(viewHolder.picBeauty);
        LogUtil.i("I get one!");
    }

    @Override
    public int getItemCount() {
        if (mList == null)
            return 0;
        return mList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.pic_Beauty)
        ImageView picBeauty;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
    private void setImageScale() {
        for (final GirlBean.ResultsBean gr:mList) {
            Glide.with(mContext).load(gr.getUrl()).into(new SimpleTarget<Drawable>() {
                @Override
                public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                    float scale = resource.getIntrinsicWidth() / (float) resource.getIntrinsicHeight();
                    gr.setScale(scale);
                    notifyDataSetChanged();
                }
            });
        }
    }
}
