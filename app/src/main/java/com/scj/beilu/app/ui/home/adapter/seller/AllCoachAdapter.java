package com.scj.beilu.app.ui.home.adapter.seller;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseViewHolder;

/**
 * author:SunGuiLan
 * date:2019/2/18
 * descriptin:
 */
public class AllCoachAdapter extends RecyclerView.Adapter<AllCoachAdapter.HomeAllCoachViewHolder> {
    @NonNull
    @Override
    public HomeAllCoachViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sellerdetails_allcoach, parent, false);
        return new HomeAllCoachViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeAllCoachViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class HomeAllCoachViewHolder extends BaseViewHolder {

        public HomeAllCoachViewHolder(View itemView) {
            super(itemView);
        }
    }
}
