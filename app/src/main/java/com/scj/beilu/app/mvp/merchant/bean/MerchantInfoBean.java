package com.scj.beilu.app.mvp.merchant.bean;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/4/13 18:49
 */
public class MerchantInfoBean {


    /**
     * merchantId : 56
     * merchantName : 天心
     * merchantDec : null
     * merchantAddr : 重庆渝北君悦世纪1栋
     * merchantLongitude : 106.52729
     * merchantLatitude : 29.610151
     * merchantTel : null
     * merchantPhone : null
     * merchantCate : 0
     * merchantOpenShop : null
     * merchantCloseShop : null
     * merchantStartBusiness : null
     * merchantDecoration : null
     * merchantProvince : 重庆市
     * merchantCity : 重庆市
     * merchantArea : null
     * countMerchantAlbum : 0
     * merchantPicAddr : https://beilutest.oss-cn-hangzhou.aliyuncs.com/merchant/coach/showPic/ca3f55d3-ae2d-47aa-892b-6ce40e57289f20190381164203.jpg
     * merchantPicAddrZip : https://beilutest.oss-cn-hangzhou.aliyuncs.com/merchant/coach/showPic/zipca3f55d3-ae2d-47aa-892b-6ce40e57289f20190381164203.jpg
     * merchantPicList : [{"picId":0,"merchantId":56,"picAddr":null,"picAddrZip":null}]
     * installList : [{"installId":0,"installName":null,"installRe":[{"merchantId":56,"installId":0}]}]
     * cateEntity : null
     */

    private int merchantId;
    private String merchantName;
    private String merchantDec;
    private String merchantAddr;
    private String merchantLongitude;
    private String merchantLatitude;
    private String merchantTel;
    private String merchantPhone;
    private int merchantCate;
    private String merchantOpenShop;
    private String merchantCloseShop;
    private String merchantStartBusiness;
    private String merchantDecoration;
    private String merchantProvince;
    private String merchantCity;
    private String merchantArea;
    private int countMerchantAlbum;
    private String merchantPicAddr;
    private String merchantPicAddrZip;
    private String cateEntity;
    private List<MerchantPicListBean> merchantPicList;
    private List<MerchantInfoFacilitiesBean> installList;
    private List<MerchantInfoMemberShipListBean> memberShipList;
    private List<MerchantInfoCoachBean> coachList;

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

    public int getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(int merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getMerchantDec() {
        return merchantDec;
    }

    public void setMerchantDec(String merchantDec) {
        this.merchantDec = merchantDec;
    }

    public String getMerchantAddr() {
        return merchantAddr;
    }

    public void setMerchantAddr(String merchantAddr) {
        this.merchantAddr = merchantAddr;
    }

    public String getMerchantLongitude() {
        return merchantLongitude;
    }

    public void setMerchantLongitude(String merchantLongitude) {
        this.merchantLongitude = merchantLongitude;
    }

    public String getMerchantLatitude() {
        return merchantLatitude;
    }

    public void setMerchantLatitude(String merchantLatitude) {
        this.merchantLatitude = merchantLatitude;
    }

    public String getMerchantTel() {
        return merchantTel;
    }

    public void setMerchantTel(String merchantTel) {
        this.merchantTel = merchantTel;
    }

    public String getMerchantPhone() {
        return merchantPhone;
    }

    public void setMerchantPhone(String merchantPhone) {
        this.merchantPhone = merchantPhone;
    }

    public int getMerchantCate() {
        return merchantCate;
    }

    public void setMerchantCate(int merchantCate) {
        this.merchantCate = merchantCate;
    }

    public String getMerchantOpenShop() {
        return merchantOpenShop;
    }

    public void setMerchantOpenShop(String merchantOpenShop) {
        this.merchantOpenShop = merchantOpenShop;
    }

    public String getMerchantCloseShop() {
        return merchantCloseShop;
    }

    public void setMerchantCloseShop(String merchantCloseShop) {
        this.merchantCloseShop = merchantCloseShop;
    }

    public String getMerchantStartBusiness() {
        return merchantStartBusiness;
    }

    public void setMerchantStartBusiness(String merchantStartBusiness) {
        this.merchantStartBusiness = merchantStartBusiness;
    }

    public String getMerchantDecoration() {
        return merchantDecoration;
    }

    public void setMerchantDecoration(String merchantDecoration) {
        this.merchantDecoration = merchantDecoration;
    }

    public String getMerchantProvince() {
        return merchantProvince;
    }

    public void setMerchantProvince(String merchantProvince) {
        this.merchantProvince = merchantProvince;
    }

    public String getMerchantCity() {
        return merchantCity;
    }

    public void setMerchantCity(String merchantCity) {
        this.merchantCity = merchantCity;
    }

    public String getMerchantArea() {
        return merchantArea;
    }

    public void setMerchantArea(String merchantArea) {
        this.merchantArea = merchantArea;
    }

    public int getCountMerchantAlbum() {
        return countMerchantAlbum;
    }

    public void setCountMerchantAlbum(int countMerchantAlbum) {
        this.countMerchantAlbum = countMerchantAlbum;
    }

    public String getMerchantPicAddr() {
        return merchantPicAddr;
    }

    public void setMerchantPicAddr(String merchantPicAddr) {
        this.merchantPicAddr = merchantPicAddr;
    }

    public String getMerchantPicAddrZip() {
        return merchantPicAddrZip;
    }

    public void setMerchantPicAddrZip(String merchantPicAddrZip) {
        this.merchantPicAddrZip = merchantPicAddrZip;
    }

    public String getCateEntity() {
        return cateEntity;
    }

    public void setCateEntity(String cateEntity) {
        this.cateEntity = cateEntity;
    }

    public List<MerchantPicListBean> getMerchantPicList() {
        return merchantPicList;
    }

    public void setMerchantPicList(List<MerchantPicListBean> merchantPicList) {
        this.merchantPicList = merchantPicList;
    }

    public List<MerchantInfoFacilitiesBean> getInstallList() {
        return installList;
    }

    public void setInstallList(List<MerchantInfoFacilitiesBean> installList) {
        this.installList = installList;
    }

    public static class MerchantPicListBean {
        /**
         * picId : 0
         * merchantId : 56
         * picAddr : null
         * picAddrZip : null
         */

        private int picId;
        private int merchantId;
        private String picAddr;
        private String picAddrZip;

        public int getPicId() {
            return picId;
        }

        public void setPicId(int picId) {
            this.picId = picId;
        }

        public int getMerchantId() {
            return merchantId;
        }

        public void setMerchantId(int merchantId) {
            this.merchantId = merchantId;
        }

        public String getPicAddr() {
            return picAddr;
        }

        public void setPicAddr(String picAddr) {
            this.picAddr = picAddr;
        }

        public String getPicAddrZip() {
            return picAddrZip;
        }

        public void setPicAddrZip(String picAddrZip) {
            this.picAddrZip = picAddrZip;
        }
    }


}
