package com.scj.beilu.app.mvp.news.bean;

import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;

/**
 * @author Mingxun
 * @time on 2019/5/11 17:59
 */
public class NewsAuthorInfoBean extends ResultMsgBean {


    /**
     * userId : 1
     * userNickName : 童童
     * userOriginalHead : https://resource.cqsanchaji.com/user/head/c64b21dc-fdcc-4bd6-a23a-88cc455af3d9201904115111714.jpg
     * userCompressionHead : https://resource.cqsanchaji.com/user/head/zip/c64b21dc-fdcc-4bd6-a23a-88cc455af3d9201904115111714.jpg
     * userBrief : 这个人很懒，什么都没留下
     * fansCount : 0
     * isFocus : 0
     */
    private int userId;
    private String userNickName;
    private String userOriginalHead;
    private String userCompressionHead;
    private String userBrief;
    private int fansCount;
    private int isFocus;

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

    public String getUserBrief() {
        return userBrief;
    }

    public void setUserBrief(String userBrief) {
        this.userBrief = userBrief;
    }

    public int getFansCount() {
        return fansCount;
    }

    public void setFansCount(int fansCount) {
        this.fansCount = fansCount;
    }

    public int getIsFocus() {
        return isFocus;
    }

    public void setIsFocus(int isFocus) {
        this.isFocus = isFocus;
    }
}
