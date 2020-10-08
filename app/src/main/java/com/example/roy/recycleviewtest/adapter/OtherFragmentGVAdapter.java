package com.example.roy.recycleviewtest.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.roy.recycleviewtest.R;
import com.example.roy.recycleviewtest.interfaces.ItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OtherFragmentGVAdapter extends BaseAdapter {
    private List<String> cateList;
    private LayoutInflater layoutInflater;
    private ItemClickListener mItemClickListener;// 点击回调接口
    public OtherFragmentGVAdapter(Context context, List<String> cateList) {
        this.cateList = cateList;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return cateList.size();
    }

    @Override
    public Object getItem(int position) {
        return cateList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_fragment_other, parent,false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvCate.setText(cateList.get(position));
        ((View)holder.tvCate.getParent()).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemClickListener.onItemClick(v,position);
            }
        });

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tv_cate)
        TextView tvCate;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public void setOnItemClickListener(ItemClickListener onItemClickListener) {
        mItemClickListener = onItemClickListener;
    }
}
