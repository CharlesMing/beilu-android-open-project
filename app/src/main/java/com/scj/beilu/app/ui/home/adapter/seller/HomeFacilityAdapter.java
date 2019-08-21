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
 * date:2019/2/16
 * descriptin:
 */
public class HomeFacilityAdapter extends RecyclerView.Adapter<HomeFacilityAdapter.HomeFacilityViewHolder> {
    @NonNull
    @Override
    public HomeFacilityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_seller_facility, parent, false);
        return new HomeFacilityViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeFacilityViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 8;
    }

    public class HomeFacilityViewHolder extends BaseViewHolder {

        public HomeFacilityViewHolder(View itemView) {
            super(itemView);
        }
    }
}
