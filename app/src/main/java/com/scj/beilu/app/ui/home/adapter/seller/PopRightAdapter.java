package com.scj.beilu.app.ui.home.adapter.seller;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseViewHolder;
import com.scj.beilu.app.mvp.home.bean.RightViewItemBean;

import java.util.List;


/**
 * @类名：
 * @描述：右侧列表适配器
 * @Created by lm on 2017/3/7.
 */

public class PopRightAdapter extends RecyclerView.Adapter<PopRightAdapter.RightViewHoler>{
    private Context context;
    private List<RightViewItemBean> list;//列表数据源
    private String selectedRightId = "";//右侧列表中被选中的Id，name有可能重复，id一般不会重复，因此使用id进行区分
    private OnItemClickListener mOnItemClickListener;//item的点击事件

    public PopRightAdapter(Context context, List<RightViewItemBean> list, String selectedRightId){
        this.context = context;
        this.list = list;
        this.selectedRightId = selectedRightId;
    }

    /**
     * @param设置被选中行的Id
     */
    public void setSelectedRightId(String selectedRightId) {
        this.selectedRightId = selectedRightId;
    }


    @NonNull
    @Override
    public RightViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_pop_right, parent,false);
        return new RightViewHoler(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final RightViewHoler holder, int position) {
        RightViewHoler rightViewHoler = (RightViewHoler) holder;
        rightViewHoler.nameTV.setText(list.get(position).getName());
        if (selectedRightId.equals(list.get(position).getId())){
            rightViewHoler.nameTV.setTextColor(Color.parseColor("#32B9AA"));
        } else {
            rightViewHoler.nameTV.setTextColor(Color.parseColor("#7A7A7A"));
        }

        //判断是否设置了监听器
        if(mOnItemClickListener != null){
            //为ItemView设置监听器
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getLayoutPosition(); // 1
                    mOnItemClickListener.onItemClick(holder.itemView,position); // 2
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class RightViewHoler extends BaseViewHolder {
        TextView nameTV;//名字
        public RightViewHoler(View itemView) {
            super(itemView);
            nameTV = findViewById(R.id.right_item_name);
        }
    }


    /**
     * 点击事件接口
     */
    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener){
        this.mOnItemClickListener = mOnItemClickListener;
    }
}
