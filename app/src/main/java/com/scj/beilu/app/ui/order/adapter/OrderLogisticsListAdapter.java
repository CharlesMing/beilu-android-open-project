package com.scj.beilu.app.ui.order.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseViewHolder;
import com.scj.beilu.app.mvp.order.bean.OrderLogisticsInfoBean;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/4/8 22:27
 */
public class OrderLogisticsListAdapter extends RecyclerView.Adapter<OrderLogisticsListAdapter.LogisticsViewHolder> {

    private List<OrderLogisticsInfoBean> mLogisticsInfoBeans;

    public void setLogisticsInfoBeans(List<OrderLogisticsInfoBean> logisticsInfoBeans) {
        mLogisticsInfoBeans = logisticsInfoBeans;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public LogisticsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_logistics_content, parent, false);
        return new LogisticsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LogisticsViewHolder holder, int position) {
        try {
            OrderLogisticsInfoBean infoBean = mLogisticsInfoBeans.get(position);
            holder.mTvLogisticName.setText(infoBean.getRemark());
            holder.mTvLogisticDate.setText(infoBean.getDatetime());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mLogisticsInfoBeans == null ? 0 : mLogisticsInfoBeans.size();
    }

    public class LogisticsViewHolder extends BaseViewHolder {
        private TextView mTvLogisticName;
        private TextView mTvLogisticDate;

        public LogisticsViewHolder(View itemView) {
            super(itemView);
            mTvLogisticName = findViewById(R.id.tv_logistic_name);
            mTvLogisticDate = findViewById(R.id.tv_logistic_date);
        }
    }
}
