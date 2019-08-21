package com.scj.beilu.app.ui.home.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseViewHolder;

/**
 * author:SunGuiLan
 * date:2019/2/15
 * descriptin:
 */
public class HomeCoachAdapter extends RecyclerView.Adapter<HomeCoachAdapter.HomeCoachViewHolder> {

    private OnItemClickListen onItemClickListen;

    public void setOnItemClickListener(OnItemClickListen onItemClickListener) {
        this.onItemClickListen = onItemClickListener;
    }

    public interface OnItemClickListen {
        void onItemClick(View view, int position);
    }

    @NonNull
    @Override
    public HomeCoachViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sellerdetails_coach, parent, false);
        return new HomeCoachViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final HomeCoachViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListen != null)
                    onItemClickListen.onItemClick(holder.itemView, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class HomeCoachViewHolder extends BaseViewHolder {

        public HomeCoachViewHolder(View itemView) {
            super(itemView);
        }
    }
}
