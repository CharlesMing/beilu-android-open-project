package com.scj.beilu.app.mvp.mine.address.bean;

import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;

import java.io.Serializable;

/**
 * author:SunGuiLan
 * date:2019/2/28
 * descriptin:
 */
public class AddressInfoBean extends ResultMsgBean implements Serializable {


    /**
     * addrId : 1
     * userId : 1
     * userAddrName : 沈月望
     * userAddrTel : 13698369491
     * userAddrProvince : 四川省
     * userAddrCity : 广安市
     * userAddrDetail : 邻水县鼎屏镇御临大道3号
     * userAddrDefault : 0
     */

    private int addrId;
    private int userId;
    private String userAddrName;
    private String userAddrTel;
    private String userAddrProvince;
    private String userAddrCity;
    private String userAddrDetail;
    private int userAddrDefault;

    public int getAddrId() {
        return addrId;
    }

    public void setAddrId(int addrId) {
        this.addrId = addrId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserAddrName() {
        return userAddrName;
    }

    public void setUserAddrName(String userAddrName) {
        this.userAddrName = userAddrName;
    }

    public String getUserAddrTel() {
        return userAddrTel;
    }

    public void setUserAddrTel(String userAddrTel) {
        this.userAddrTel = userAddrTel;
    }

    public String getUserAddrProvince() {
        return userAddrProvince;
    }

    public void setUserAddrProvince(String userAddrProvince) {
        this.userAddrProvince = userAddrProvince;
    }

    public String getUserAddrCity() {
        return userAddrCity;
    }

    public void setUserAddrCity(String userAddrCity) {
        this.userAddrCity = userAddrCity;
    }

    public String getUserAddrDetail() {
        return userAddrDetail;
    }

    public void setUserAddrDetail(String userAddrDetail) {
        this.userAddrDetail = userAddrDetail;
    }

    public int getUserAddrDefault() {
        return userAddrDefault;
    }

    public void setUserAddrDefault(int userAddrDefault) {
        this.userAddrDefault = userAddrDefault;
    }
}
