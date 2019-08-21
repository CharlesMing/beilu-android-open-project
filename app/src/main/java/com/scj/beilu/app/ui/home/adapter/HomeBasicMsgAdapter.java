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
 * date:2019/2/16
 * descriptin:
 */
public class HomeBasicMsgAdapter extends RecyclerView.Adapter<HomeBasicMsgAdapter.HomeBasicMsgViewHolder> {
    @NonNull
    @Override
    public HomeBasicMsgViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_seller_basicmsg, parent, false);
        return new HomeBasicMsgViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeBasicMsgViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public class HomeBasicMsgViewHolder extends BaseViewHolder {
        public HomeBasicMsgViewHolder(View itemView) {
            super(itemView);
        }
    }
}
