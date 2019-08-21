package com.scj.beilu.app.ui.home.adapter.seller;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseViewHolder;
import com.scj.beilu.app.mvp.home.bean.LeftViewItemBean;

import java.util.List;


/**
 * @类名：
 * @描述：左边列表适配器
 * @Created by lm on 2017/3/7.
 */

public class PopLeftAdapter extends RecyclerView.Adapter<PopLeftAdapter.LeftHolder> {
    private Context context;
    private List<LeftViewItemBean> list;//数据源
    private int selectedPosition = 0;//选中第几行，默认选中第0行
    private OnItemClickListener mOnItemClickListener;//item的点击事件

    public PopLeftAdapter(Context context, List<LeftViewItemBean> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public LeftHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_pop_left, parent,false);
        return new LeftHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final LeftHolder holder, int position) {
        LeftHolder leftHolder = (LeftHolder) holder;
        //选中和没选中时，设置不同的颜色
        if (position == selectedPosition) {
            leftHolder.itemView.setBackgroundColor(Color.parseColor("#fff9f9f9"));

            leftHolder.nameTV.setTextColor(Color.parseColor("#32B9AA"));
            leftHolder.left_line.setVisibility(View.VISIBLE);
        } else {
            leftHolder.itemView.setBackgroundColor(Color.parseColor("#ffffff"));

            leftHolder.nameTV.setTextColor(Color.parseColor("#7A7A7A"));
            leftHolder.left_line.setVisibility(View.INVISIBLE);
        }

        leftHolder.nameTV.setText(list.get(position).getName());
        //判断是否设置了监听器
        if (mOnItemClickListener != null) {
            //为ItemView设置监听器
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getLayoutPosition(); // 1
                    mOnItemClickListener.onItemClick(holder.itemView, position); // 2
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }


    /**
     * @param设置选中第几行
     */
    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
    }

    /**
     * @return获取左侧列表选中的是哪一行
     */
    public int getSelectedPosition() {
        return selectedPosition;
    }

    public class LeftHolder extends BaseViewHolder {
        private TextView nameTV;//名字
        private ImageView left_line;

        public LeftHolder(View itemView) {
            super(itemView);
            nameTV = findViewById(R.id.left_item_name);
            left_line = findViewById(R.id.left_color);

        }
    }
    /**
     * 点击事件接口
     */
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

}

