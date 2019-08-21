package com.scj.beilu.app.mvp.news.bean;

/**
 * @author Mingxun
 * @time on 2019/2/14 16:46
 */
public class NewsBannerBean {


    /**
     * adverId : 8
     * adverTitle : sfg发给个人
     * adverPicAddr : 7f5189ac-eef4-42e2-9f78-5826a0129c5520190117114125.jpg
     * adverPicZip : zip7f5189ac-eef4-42e2-9f78-5826a0129c5520190117114125.jpg
     * adverVideoAddr : null
     * adverDec : dfsfaf
     * adverIsThird : 1
     * adverHyperlink : fgsdfgsrgsdf
     * adverOwn : 2
     * newsId : 10
     * adverCateId : 7
     * adverIsOpen : 1
     * adverCateName : {"adverCateId":7,"adverCateName":"资讯Banner"}
     */

    private int adverId;
    private String adverTitle;
    private String adverPicAddr;
    private String adverPicZip;
    private Object adverVideoAddr;
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

    public Object getAdverVideoAddr() {
        return adverVideoAddr;
    }

    public void setAdverVideoAddr(Object adverVideoAddr) {
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
         * adverCateId : 7
         * adverCateName : 资讯Banner
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
