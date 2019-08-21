package com.scj.beilu.app.ui.merchant.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.scj.beilu.app.mvp.merchant.bean.MerchantPicTypeBean;
import com.scj.beilu.app.ui.merchant.MerchantPhotoListFrag;

import java.util.ArrayList;

/**
 * @author Mingxun
 * @time on 2019/4/15 23:23
 */
public class MerchantInfoPicManagerListAdapter extends FragmentStatePagerAdapter {

    public static ArrayList<MerchantPicTypeBean> PIC_TYPE_LIST;
    private int mMerchantId;

    public MerchantInfoPicManagerListAdapter(FragmentManager fm, int merchantId) {
        super(fm);
        this.mMerchantId = merchantId;
    }

    public void setPicTypeList(ArrayList<MerchantPicTypeBean> picTypeList) {
        PIC_TYPE_LIST = picTypeList;
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        MerchantPicTypeBean picTypeBean = PIC_TYPE_LIST.get(position);
        return MerchantPhotoListFrag.newInstance(mMerchantId, picTypeBean.getRegionId(), position);
    }

    @Override
    public int getCount() {
        return PIC_TYPE_LIST == null ? 0 : PIC_TYPE_LIST.size();
    }
}
