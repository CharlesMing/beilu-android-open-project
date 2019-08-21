package com.scj.beilu.app.mvp.common.pay.bean;

import com.google.gson.annotations.SerializedName;

/**
 * @author Mingxun
 * @time on 2019/4/2 22:50
 */
public class PayOrderInfoResultBean {
    /**
     * appid : wxec4fad924ee5fd9e
     * noncestr : vmOX8hPcYnN6ZDyP
     * package : Sign=WXPay
     * partnerid : 1526389851
     * prepayid : wx02224914886198f35d64dfb30786516994
     * sign : 95A3677495FC0225407608B1FF111241
     * timestamp : 1554216557
     */
    private String appid;
    private String noncestr;
    @SerializedName("package")
    private String packageX;
    private String partnerid;
    private String prepayid;
    private String sign;
    private int timestamp;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getPackageX() {
        return packageX;
    }

    public void setPackageX(String packageX) {
        this.packageX = packageX;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }
}
