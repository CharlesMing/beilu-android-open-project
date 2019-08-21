package com.scj.beilu.app.ui.mine.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseViewHolder;

/**
 * @author Mingxun
 * @time on 2019/1/15 20:57
 */
public class SystemListAdapter extends RecyclerView.Adapter {
    private final int VIEW_TYPE_ORDER = 1;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ORDER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_system_msg_order, parent, false);
            return new OrderViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_system_msg_other, parent, false);
            return new OtherViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (position == 0) {

        }
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return VIEW_TYPE_ORDER;
        } else {
            return super.getItemViewType(position);
        }
    }

    class OrderViewHolder extends BaseViewHolder {
        public OrderViewHolder(View itemView) {
            super(itemView);
        }
    }

    class OtherViewHolder extends BaseViewHolder {
        public OtherViewHolder(View itemView) {
            super(itemView);
        }
    }

}
