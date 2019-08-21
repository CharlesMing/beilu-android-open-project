package com.scj.beilu.app.mvp.merchant.bean;

/**
 * @author Mingxun
 * @time on 2019/4/15 16:58
 */
public class MerchantInfoMemberShipListBean {

    /**
     * memberShipId : 0
     * merchantId : 0
     * memberShipName : 张三
     * memberShipTel : 25
     * memberShipHead : https://beilutest.oss-cn-hangzhou.aliyuncs.com/merchant/coach/showPic/ca3f55d3-ae2d-47aa-892b-6ce40e57289f20190381164203.jpg
     * memberShipHeadZip : https://beilutest.oss-cn-hangzhou.aliyuncs.com/merchant/coach/showPic/zipca3f55d3-ae2d-47aa-892b-6ce40e57289f20190381164203.jpg
     */

    private int memberShipId;
    private int merchantId;
    private String memberShipName;
    private String memberShipTel;
    private String memberShipHead;
    private String memberShipHeadZip;

    public int getMemberShipId() {
        return memberShipId;
    }

    public void setMemberShipId(int memberShipId) {
        this.memberShipId = memberShipId;
    }

    public int getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(int merchantId) {
        this.merchantId = merchantId;
    }

    public String getMemberShipName() {
        return memberShipName;
    }

    public void setMemberShipName(String memberShipName) {
        this.memberShipName = memberShipName;
    }

    public String getMemberShipTel() {
        return memberShipTel;
    }

    public void setMemberShipTel(String memberShipTel) {
        this.memberShipTel = memberShipTel;
    }

    public String getMemberShipHead() {
        return memberShipHead;
    }

    public void setMemberShipHead(String memberShipHead) {
        this.memberShipHead = memberShipHead;
    }

    public String getMemberShipHeadZip() {
        return memberShipHeadZip;
    }

    public void setMemberShipHeadZip(String memberShipHeadZip) {
        this.memberShipHeadZip = memberShipHeadZip;
    }
}
