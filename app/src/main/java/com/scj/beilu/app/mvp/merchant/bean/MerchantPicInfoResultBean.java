package com.scj.beilu.app.mvp.merchant.bean;

import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;

import java.util.ArrayList;

/**
 * @author Mingxun
 * @time on 2019/4/15 22:23
 */
public class MerchantPicInfoResultBean extends ResultMsgBean {

    private ArrayList<MerchantPicInfoBean> regionList;

    public ArrayList<MerchantPicInfoBean> getRegionList() {
        return regionList;
    }

    public void setRegionList(ArrayList<MerchantPicInfoBean> regionList) {
        this.regionList = regionList;
    }

}
