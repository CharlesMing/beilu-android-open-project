package com.scj.beilu.app.mvp.merchant.bean;

import java.util.ArrayList;

/**
 * @author Mingxun
 * @time on 2019/4/15 16:59
 */
public class MerchantInfoCoachBean {

    /**
     * coachId : 28
     * merchantId : 45
     * coachName : 渣女大波浪
     * coachSex : null
     * coachAge : 0
     * coachExpert : 阿斯顿
     * coachSummary : null
     * coachHead : https://beilutest.oss-cn-hangzhou.aliyuncs.com/merchant/about/6bd26933-8d79-4453-b395-4211925ade8b20190375201218.jpg
     * coachHeadZip : https://beilutest.oss-cn-hangzhou.aliyuncs.com/merchant/about/6bd26933-8d79-4453-b395-4211925ade8b20190375201218.jpg
     * coachCertifi : null
     * coachCertifiZip : null
     * coachPicList : null
     */
    private int coachId;
    private int merchantId;
    private String coachName;
    private String coachSex;
    private int coachAge;
    private String coachExpert;
    private String coachSummary;
    private String coachHead;
    private String coachHeadZip;
    private String coachCertifi;
    private String coachCertifiZip;
    private String coachCertifiDec;
    private ArrayList<MerchantInfoCoachPicInfoBean> coachPicList;

    public int getCoachId() {
        return coachId;
    }

    public String getCoachCertifiDec() {
        return coachCertifiDec;
    }

    public void setCoachCertifiDec(String coachCertifiDec) {
        this.coachCertifiDec = coachCertifiDec;
    }

    public void setCoachId(int coachId) {
        this.coachId = coachId;
    }

    public int getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(int merchantId) {
        this.merchantId = merchantId;
    }

    public String getCoachName() {
        return coachName;
    }

    public void setCoachName(String coachName) {
        this.coachName = coachName;
    }

    public String getCoachSex() {
        return coachSex;
    }

    public void setCoachSex(String coachSex) {
        this.coachSex = coachSex;
    }

    public int getCoachAge() {
        return coachAge;
    }

    public void setCoachAge(int coachAge) {
        this.coachAge = coachAge;
    }

    public String getCoachExpert() {
        return coachExpert;
    }

    public void setCoachExpert(String coachExpert) {
        this.coachExpert = coachExpert;
    }

    public String getCoachSummary() {
        return coachSummary;
    }

    public void setCoachSummary(String coachSummary) {
        this.coachSummary = coachSummary;
    }

    public String getCoachHead() {
        return coachHead;
    }

    public void setCoachHead(String coachHead) {
        this.coachHead = coachHead;
    }

    public String getCoachHeadZip() {
        return coachHeadZip;
    }

    public void setCoachHeadZip(String coachHeadZip) {
        this.coachHeadZip = coachHeadZip;
    }

    public String getCoachCertifi() {
        return coachCertifi;
    }

    public void setCoachCertifi(String coachCertifi) {
        this.coachCertifi = coachCertifi;
    }

    public String getCoachCertifiZip() {
        return coachCertifiZip;
    }

    public void setCoachCertifiZip(String coachCertifiZip) {
        this.coachCertifiZip = coachCertifiZip;
    }

    public ArrayList<MerchantInfoCoachPicInfoBean> getCoachPicList() {
        return coachPicList;
    }

    public void setCoachPicList(ArrayList<MerchantInfoCoachPicInfoBean> coachPicList) {
        this.coachPicList = coachPicList;
    }
}
