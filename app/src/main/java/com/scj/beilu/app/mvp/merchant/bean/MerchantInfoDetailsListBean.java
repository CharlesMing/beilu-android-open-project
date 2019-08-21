package com.scj.beilu.app.mvp.merchant.bean;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/4/15 17:13
 */
public class MerchantInfoDetailsListBean {
    private List<MerchantInfoFacilitiesBean> installList;
    private List<MerchantInfoMemberShipListBean> memberShipList;
    private List<MerchantInfoCoachBean> coachList;
    private String startTime;
    private String endTime;
    private String startBusinessTime;
    private String fitmentTime;
    private String groupName;


    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<MerchantInfoFacilitiesBean> getInstallList() {
        return installList;
    }

    public void setInstallList(List<MerchantInfoFacilitiesBean> installList) {
        this.installList = installList;
    }

    public List<MerchantInfoMemberShipListBean> getMemberShipList() {
        return memberShipList;
    }

    public void setMemberShipList(List<MerchantInfoMemberShipListBean> memberShipList) {
        this.memberShipList = memberShipList;
    }

    public List<MerchantInfoCoachBean> getCoachList() {
        return coachList;
    }

    public void setCoachList(List<MerchantInfoCoachBean> coachList) {
        this.coachList = coachList;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStartBusinessTime() {
        return startBusinessTime;
    }

    public void setStartBusinessTime(String startBusinessTime) {
        this.startBusinessTime = startBusinessTime;
    }

    public String getFitmentTime() {
        return fitmentTime;
    }

    public void setFitmentTime(String fitmentTime) {
        this.fitmentTime = fitmentTime;
    }
}
