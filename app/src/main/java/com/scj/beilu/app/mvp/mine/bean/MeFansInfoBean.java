package com.scj.beilu.app.mvp.mine.bean;

/**
 * userId : 61
 * userNickName : 小明45
 * userCompressionHead : https://beilutest.oss-cn-hangzhou.aliyuncs.com/user/head/zip5afc7bc0-3da7-42c6-b1bd-cf329f52737120190360221606.jpg
 * isFocus : 1
 */
public class MeFansInfoBean {
    private int userId;
    private String userNickName;
    private String userCompressionHead;
    private int isFocus;
    private int isFans;

    public int getIsFans() {
        return isFans;
    }

    public void setIsFans(int isFans) {
        this.isFans = isFans;
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

    public String getUserCompressionHead() {
        return userCompressionHead;
    }

    public void setUserCompressionHead(String userCompressionHead) {
        this.userCompressionHead = userCompressionHead;
    }
    public int getIsFocus() {
        return isFocus;
    }
    public void setIsFocus(int isFocus) {
        this.isFocus = isFocus;
    }
}
