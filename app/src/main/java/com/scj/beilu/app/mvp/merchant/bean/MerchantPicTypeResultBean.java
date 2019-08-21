package com.scj.beilu.app.mvp.merchant.bean;

import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;

import java.util.ArrayList;

/**
 * @author Mingxun
 * @time on 2019/4/15 22:23
 */
public class MerchantPicTypeResultBean extends ResultMsgBean {
    private ArrayList<MerchantPicTypeBean> regionList;

    public ArrayList<MerchantPicTypeBean> getRegionList() {
        return regionList;
    }

    public void setRegionList(ArrayList<MerchantPicTypeBean> regionList) {
        this.regionList = regionList;
    }
}
