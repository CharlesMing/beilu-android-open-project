package com.scj.beilu.app.mvp.merchant.bean;

import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;

import java.util.ArrayList;

/**
 * @author Mingxun
 * @time on 2019/4/16 17:18
 */
public class MerchantInfoCoachPicInfoResultBean extends ResultMsgBean {

    private ArrayList<MerchantInfoCoachPicInfoBean> coachPicList;

    public ArrayList<MerchantInfoCoachPicInfoBean> getCoachPicList() {
        return coachPicList;
    }

    public void setCoachPicList(ArrayList<MerchantInfoCoachPicInfoBean> coachPicList) {
        this.coachPicList = coachPicList;
    }
}
