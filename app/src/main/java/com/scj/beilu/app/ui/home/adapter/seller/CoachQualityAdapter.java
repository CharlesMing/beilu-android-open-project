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
public class CoachQualityAdapter extends RecyclerView.Adapter<CoachQualityAdapter.CoachQualityViewHolder> {
    @NonNull
    @Override
    public CoachQualityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_coach_service, parent, false);
        return new CoachQualityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CoachQualityViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public class CoachQualityViewHolder extends BaseViewHolder {
        public CoachQualityViewHolder(View itemView) {
            super(itemView);
        }
    }
}
