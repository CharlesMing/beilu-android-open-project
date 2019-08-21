package com.scj.beilu.app.mvp.find.bean;

/**
 * @author Mingxun
 * @time on 2019/3/1 20:49
 */
public class OrganizationInfoBean {
    /**
     * organId : 1
     * organBrief : 非常活跃
     * organName : 英雄联盟
     * organCity : null
     * organHead : add.jpg
     * organHeadZip : adds.jpg
     * organCateId : 0
     * countUser : 2
     * countDynamic : 2
     * dynamicList : []
     */

    private int organId;
    private String organBrief;
    private String organName;
    private String organCity;
    private String organHead;
    private String organHeadZip;
    private int organCateId;
    private int countUser;
    private int countDynamic;


    public int getOrganId() {
        return organId;
    }

    public void setOrganId(int organId) {
        this.organId = organId;
    }

    public String getOrganBrief() {
        return organBrief;
    }

    public void setOrganBrief(String organBrief) {
        this.organBrief = organBrief;
    }

    public String getOrganName() {
        return organName;
    }

    public void setOrganName(String organName) {
        this.organName = organName;
    }

    public String getOrganCity() {
        return organCity;
    }

    public void setOrganCity(String organCity) {
        this.organCity = organCity;
    }

    public String getOrganHead() {
        return organHead;
    }

    public void setOrganHead(String organHead) {
        this.organHead = organHead;
    }

    public String getOrganHeadZip() {
        return organHeadZip;
    }

    public void setOrganHeadZip(String organHeadZip) {
        this.organHeadZip = organHeadZip;
    }

    public int getOrganCateId() {
        return organCateId;
    }

    public void setOrganCateId(int organCateId) {
        this.organCateId = organCateId;
    }

    public int getCountUser() {
        return countUser;
    }

    public void setCountUser(int countUser) {
        this.countUser = countUser;
    }

    public int getCountDynamic() {
        return countDynamic;
    }

    public void setCountDynamic(int countDynamic) {
        this.countDynamic = countDynamic;
    }

}
