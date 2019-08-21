package com.scj.beilu.app.mvp.merchant.bean;

import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/4/13 18:52
 */
public class MerchantInfoResultBean extends ResultMsgBean {
    private MerchantInfoBean merchantEntity;
    private List<MerchantInfoFacilitiesBean> installList;
    private List<MerchantInfoCoachBean> coachList;
    private List<MerchantInfoMemberShipListBean> memberShipList;

    public MerchantInfoBean getMerchantEntity() {
        return merchantEntity;
    }

    public void setMerchantEntity(MerchantInfoBean merchantEntity) {
        this.merchantEntity = merchantEntity;
    }

    public List<MerchantInfoFacilitiesBean> getInstallList() {
        return installList;
    }

    public void setInstallList(List<MerchantInfoFacilitiesBean> installList) {
        this.installList = installList;
    }

    public List<MerchantInfoCoachBean> getCoachList() {
        return coachList;
    }

    public void setCoachList(List<MerchantInfoCoachBean> coachList) {
        this.coachList = coachList;
    }

    public List<MerchantInfoMemberShipListBean> getMemberShipList() {
        return memberShipList;
    }

    public void setMemberShipList(List<MerchantInfoMemberShipListBean> memberShipList) {
        this.memberShipList = memberShipList;
    }


}
