package com.scj.beilu.app.ui.merchant.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseViewHolder;

/**
 * @author Mingxun
 * @time on 2019/4/16 16:51
 */
public class MerchantCoachAdvantageCourseListAdapter extends RecyclerView.Adapter<MerchantCoachAdvantageCourseListAdapter.AdvantageViewHolder> {

    private String[] mCoachExperts;

    public void setCoachExperts(String coachExperts) {
        try {
            if (coachExperts != null && coachExperts.contains(",")) {
                mCoachExperts = coachExperts.split(",");
            } else if (coachExperts != null) {
                this.mCoachExperts = new String[]{coachExperts};
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public AdvantageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_merchant_advantage_content, parent, false);
        return new AdvantageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdvantageViewHolder holder, int position) {
        holder.tv_advantage_course.setText(mCoachExperts[position]);
    }

    @Override
    public int getItemCount() {
        return mCoachExperts == null ? 0 : mCoachExperts.length;
    }

    public class AdvantageViewHolder extends BaseViewHolder {
        private TextView tv_advantage_course;

        public AdvantageViewHolder(View itemView) {
            super(itemView);
            tv_advantage_course = findViewById(R.id.tv_advantage_course);
        }
    }

}
