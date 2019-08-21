package com.scj.beilu.app.mvp.common.bean;

/**
 * @author Mingxun
 * @time on 2019/3/13 16:52
 */
public class BannerInfoBean {
    /**
     * adverId : 12
     * adverTitle : 主页Banner
     * adverPicAddr : https://beilutest.oss-cn-hangzhou.aliyuncs.com/adver/pic/1d97459f-8a89-4b9f-85d1-75142ef532b920190372103442.png
     * adverPicZip : https://beilutest.oss-cn-hangzhou.aliyuncs.com/adver/pic/zip1d97459f-8a89-4b9f-85d1-75142ef532b920190372103442.png
     * adverVideoAddr : null
     * adverDec :
     * adverIsThird : 0
     * adverHyperlink : null
     * adverOwn : 1
     * newsId : 0
     * adverCateId : 1
     * adverIsOpen : 1
     * adverCateName : {"adverCateId":1,"adverCateName":"主页Banner"}
     */

    private int adverId;
    private String adverTitle;
    private String adverPicAddr;
    private String adverPicZip;
    private String adverVideoAddr;
    private String adverDec;
    private int adverIsThird;
    private String adverHyperlink;
    private int adverOwn;
    private int newsId;
    private int adverCateId;
    private int adverIsOpen;
    private AdverCateNameBean adverCateName;

    public int getAdverId() {
        return adverId;
    }

    public void setAdverId(int adverId) {
        this.adverId = adverId;
    }

    public String getAdverTitle() {
        return adverTitle;
    }

    public void setAdverTitle(String adverTitle) {
        this.adverTitle = adverTitle;
    }

    public String getAdverPicAddr() {
        return adverPicAddr;
    }

    public void setAdverPicAddr(String adverPicAddr) {
        this.adverPicAddr = adverPicAddr;
    }

    public String getAdverPicZip() {
        return adverPicZip;
    }

    public void setAdverPicZip(String adverPicZip) {
        this.adverPicZip = adverPicZip;
    }

    public String getAdverVideoAddr() {
        return adverVideoAddr;
    }

    public void setAdverVideoAddr(String adverVideoAddr) {
        this.adverVideoAddr = adverVideoAddr;
    }

    public String getAdverDec() {
        return adverDec;
    }

    public void setAdverDec(String adverDec) {
        this.adverDec = adverDec;
    }

    public int getAdverIsThird() {
        return adverIsThird;
    }

    public void setAdverIsThird(int adverIsThird) {
        this.adverIsThird = adverIsThird;
    }

    public String getAdverHyperlink() {
        return adverHyperlink;
    }

    public void setAdverHyperlink(String adverHyperlink) {
        this.adverHyperlink = adverHyperlink;
    }

    public int getAdverOwn() {
        return adverOwn;
    }

    public void setAdverOwn(int adverOwn) {
        this.adverOwn = adverOwn;
    }

    public int getNewsId() {
        return newsId;
    }

    public void setNewsId(int newsId) {
        this.newsId = newsId;
    }

    public int getAdverCateId() {
        return adverCateId;
    }

    public void setAdverCateId(int adverCateId) {
        this.adverCateId = adverCateId;
    }

    public int getAdverIsOpen() {
        return adverIsOpen;
    }

    public void setAdverIsOpen(int adverIsOpen) {
        this.adverIsOpen = adverIsOpen;
    }

    public AdverCateNameBean getAdverCateName() {
        return adverCateName;
    }

    public void setAdverCateName(AdverCateNameBean adverCateName) {
        this.adverCateName = adverCateName;
    }

    public static class AdverCateNameBean {
        /**
         * adverCateId : 1
         * adverCateName : 主页Banner
         */

        private int adverCateId;
        private String adverCateName;

        public int getAdverCateId() {
            return adverCateId;
        }

        public void setAdverCateId(int adverCateId) {
            this.adverCateId = adverCateId;
        }

        public String getAdverCateName() {
            return adverCateName;
        }

        public void setAdverCateName(String adverCateName) {
            this.adverCateName = adverCateName;
        }
    }
}
