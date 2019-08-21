package com.scj.beilu.app.mvp.mine.bean;

/**
 * userId : 63
 * userNickName : null
 * userPassword : null
 * userSex : null
 * userBirth : null
 * userTel : null
 * userBrief : null
 * userProvince : null
 * userCity : null
 * userType : 0
 * userOriginalHead : null
 * userCompressionHead : https://beilutest.oss-cn-hangzhou.aliyuncs.com/user/head/987654321-20190214104729.png
 * userAppKey : null
 * userTargetValue : null
 * userBackImg : null
 * openId : null
 */
public class FocusUserInfoBean {
    private int userId;
    private String userNickName;
    private String userPassword;
    private String userSex;
    private String userBirth;
    private String userTel;
    private String userBrief;
    private String userProvince;
    private String userCity;
    private int userType;
    private String userOriginalHead;
    private String userCompressionHead;
    private String userAppKey;
    private String userTargetValue;
    private String userBackImg;
    private String openId;
    private int isFocus;
    private int isFans;//1 相互关注

    public int getIsFans() {
        return isFans;
    }

    public void setIsFans(int isFans) {
        this.isFans = isFans;
    }

    public int getIsFocus() {
        return isFocus;
    }

    public void setIsFocus(int isFocus) {
        this.isFocus = isFocus;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    public String getUserBirth() {
        return userBirth;
    }

    public void setUserBirth(String userBirth) {
        this.userBirth = userBirth;
    }

    public String getUserTel() {
        return userTel;
    }

    public void setUserTel(String userTel) {
        this.userTel = userTel;
    }

    public String getUserBrief() {
        return userBrief;
    }

    public void setUserBrief(String userBrief) {
        this.userBrief = userBrief;
    }

    public String getUserProvince() {
        return userProvince;
    }

    public void setUserProvince(String userProvince) {
        this.userProvince = userProvince;
    }

    public String getUserCity() {
        return userCity;
    }

    public void setUserCity(String userCity) {
        this.userCity = userCity;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public String getUserOriginalHead() {
        return userOriginalHead;
    }

    public void setUserOriginalHead(String userOriginalHead) {
        this.userOriginalHead = userOriginalHead;
    }

    public String getUserCompressionHead() {
        return userCompressionHead;
    }

    public void setUserCompressionHead(String userCompressionHead) {
        this.userCompressionHead = userCompressionHead;
    }

    public String getUserAppKey() {
        return userAppKey;
    }

    public void setUserAppKey(String userAppKey) {
        this.userAppKey = userAppKey;
    }

    public String getUserTargetValue() {
        return userTargetValue;
    }

    public void setUserTargetValue(String userTargetValue) {
        this.userTargetValue = userTargetValue;
    }

    public String getUserBackImg() {
        return userBackImg;
    }

    public void setUserBackImg(String userBackImg) {
        this.userBackImg = userBackImg;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }
}
