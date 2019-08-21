package com.scj.beilu.app.ui.merchant.adapter;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseViewHolder;
import com.scj.beilu.app.listener.OnItemClickListener;
import com.scj.beilu.app.mvp.merchant.bean.MerchantInfoDetailsListBean;
import com.scj.beilu.app.ui.merchant.MerchantDetailFrag;

/**
 * @author Mingxun
 * @time on 2019/4/15 19:20
 */
class MerchantInfoContentViewHolder extends BaseViewHolder {

    public TextView mTvGroupName;
    public RecyclerView mRvChildList;

    private MerchantInfoCoachListAdapter mCoachListAdapter;//教练
    private MerchantInfoMemberConsultantListAdapter mMemberConsultantListAdapter;//顾问
    private MerchantInfoFacilityListAdapter mFacilityListAdapter;//设施

    public MerchantInfoContentViewHolder(MerchantDetailFrag frag, View itemView, int viewType, OnItemClickListener onItemClickListener) {
        super(itemView);
        mTvGroupName = findViewById(R.id.tv_merchant_info_group_name);
        mRvChildList = findViewById(R.id.rv_merchant_info_child_list);
        LinearLayoutManager linearLayoutManager;
        switch (viewType) {
            case MerchantInfoDetailListAdapter.TYPE_COACH:
                mCoachListAdapter = new MerchantInfoCoachListAdapter(frag);
                mCoachListAdapter.setOnItemClickListener(onItemClickListener);
                mRvChildList.setAdapter(mCoachListAdapter);
                linearLayoutManager = new LinearLayoutManager(mRvChildList.getContext(), LinearLayoutManager.HORIZONTAL, false);
                mRvChildList.setLayoutManager(linearLayoutManager);
                break;
            case MerchantInfoDetailListAdapter.TYPE_SHIP:
                mMemberConsultantListAdapter = new MerchantInfoMemberConsultantListAdapter(frag);
                mMemberConsultantListAdapter.setOnItemClickListener(onItemClickListener);
                mRvChildList.setAdapter(mMemberConsultantListAdapter);
                linearLayoutManager = new LinearLayoutManager(mRvChildList.getContext(), LinearLayoutManager.HORIZONTAL, false);
                mRvChildList.setLayoutManager(linearLayoutManager);
                break;
            case MerchantInfoDetailListAdapter.TYPE_FACILITY:
                mFacilityListAdapter = new MerchantInfoFacilityListAdapter();
                mRvChildList.setAdapter(mFacilityListAdapter);
                GridLayoutManager gridLayoutManager = new GridLayoutManager(mRvChildList.getContext(), 3);
                mRvChildList.setLayoutManager(gridLayoutManager);
                break;
        }

    }

    public void onBindData(MerchantInfoDetailsListBean detailsListBean, int viewType) {
        try {
            mTvGroupName.setText(detailsListBean.getGroupName());
            switch (viewType) {
                case MerchantInfoDetailListAdapter.TYPE_COACH:
                    mCoachListAdapter.setCoachBeans(detailsListBean.getCoachList());
                    break;
                case MerchantInfoDetailListAdapter.TYPE_SHIP:
                    mMemberConsultantListAdapter.setMemberShipListBeans(detailsListBean.getMemberShipList());
                    break;
                case MerchantInfoDetailListAdapter.TYPE_FACILITY:
                    mFacilityListAdapter.setFacilitiesBeans(detailsListBean.getInstallList());
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
