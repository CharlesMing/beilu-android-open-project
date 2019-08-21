package com.scj.beilu.app.ui.action.adapter;

import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseViewHolder;
import com.scj.beilu.app.listener.OnItemClickListener;
import com.scj.beilu.app.mvp.action.bean.ActionSecondTypeBean;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/3/16 19:11
 */
public class ActionSecondTypeListAdapter extends RecyclerView.Adapter<ActionSecondTypeListAdapter.SecondViewHolder> {

    private List<ActionSecondTypeBean> mSecondTypeBeans;
    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void setSecondTypeBeans(List<ActionSecondTypeBean> secondTypeBeans) {
        mSecondTypeBeans = secondTypeBeans;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SecondViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_action_second_type, parent, false);
        return new SecondViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SecondViewHolder holder, int position) {
        ActionSecondTypeBean secondTypeBean = mSecondTypeBeans.get(position);
        holder.mTvSecond.setText(secondTypeBean.getActionDesPartName());
        holder.mTvSecond.setTypeface(Typeface.defaultFromStyle(secondTypeBean.isSelect() ? Typeface.BOLD : Typeface.NORMAL));
        holder.mTvSecond.setSelected(secondTypeBean.isSelect());
    }

    @Override
    public int getItemCount() {
        return mSecondTypeBeans == null ? 0 : mSecondTypeBeans.size();
    }

    class SecondViewHolder extends BaseViewHolder {
        private TextView mTvSecond;

        public SecondViewHolder(View itemView) {
            super(itemView);
            mTvSecond = findViewById(R.id.tv_action_second_type);
            mTvSecond.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ActionSecondTypeBean bean = mSecondTypeBeans.get(getAdapterPosition());
                    if (mOnItemClickListener != null && bean != null) {
                        mOnItemClickListener.onItemClick(getAdapterPosition(), bean, v);
                    }
                }
            });
        }
    }

}
