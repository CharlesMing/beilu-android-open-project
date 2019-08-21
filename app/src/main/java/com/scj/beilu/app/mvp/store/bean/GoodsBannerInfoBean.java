package com.scj.beilu.app.mvp.store.bean;

/**
 * @author Mingxun
 * @time on 2019/3/26 03:30
 */
public class GoodsBannerInfoBean {
    /**
     * adverId : 3
     * adverTitle : 商城Banner
     * adverPicAddr : https://beilutest.oss-cn-hangzhou.aliyuncs.com/adver/pic/092477d7-db2e-4184-a553-33839d54b04b20190245181335.png
     * adverPicZip : https://beilutest.oss-cn-hangzhou.aliyuncs.com/adver/pic/zip092477d7-db2e-4184-a553-33839d54b04b20190245181335.png
     * adverVideoAddr : null
     * adverDec : 轮播
     * adverIsThird : 0
     * adverHyperlink : lsjkerg;lskjh
     * adverOwn : 3
     * newsId : 0
     * adverCateId : 2
     * adverIsOpen : 1
     * adverCateName : {"adverCateId":2,"adverCateName":"电商Banner"}
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


}
