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
public class CoachQualityDetailsAdapter extends RecyclerView.Adapter<CoachQualityDetailsAdapter.CoachQualityDetailsViewHolder> {


    @NonNull
    @Override
    public CoachQualityDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_coach_quality_details, parent, false);
        return new CoachQualityDetailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CoachQualityDetailsViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class CoachQualityDetailsViewHolder extends BaseViewHolder {
        public CoachQualityDetailsViewHolder(View itemView) {
            super(itemView);
        }
    }
}
