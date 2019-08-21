package com.scj.beilu.app.ui.merchant.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseViewHolder;
import com.scj.beilu.app.mvp.merchant.bean.MerchantInfoFacilitiesBean;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/4/15 19:33
 * 设施
 */
public class MerchantInfoFacilityListAdapter extends RecyclerView.Adapter<MerchantInfoFacilityListAdapter.FacilityViewHolder> {

    private List<MerchantInfoFacilitiesBean> mFacilitiesBeans;

    public void setFacilitiesBeans(List<MerchantInfoFacilitiesBean> facilitiesBeans) {
        mFacilitiesBeans = facilitiesBeans;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FacilityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_merchant_facility, parent, false);
        return new FacilityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FacilityViewHolder holder, int position) {
        try {
            MerchantInfoFacilitiesBean facilitiesBean = mFacilitiesBeans.get(position);
            holder.mIvFacility.setVisibility(facilitiesBean.getInstallName() == null ? View.INVISIBLE : View.VISIBLE);
            holder.mTvFacility.setText(facilitiesBean.getInstallName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mFacilitiesBeans == null ? 0 : mFacilitiesBeans.size();
    }

    public class FacilityViewHolder extends BaseViewHolder {
        private TextView mTvFacility;
        private ImageView mIvFacility;

        public FacilityViewHolder(View itemView) {
            super(itemView);
            mIvFacility = findViewById(R.id.iv_merchant_facility_);
            mTvFacility = findViewById(R.id.tv_facility_name);
        }
    }

}
