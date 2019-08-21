package com.scj.beilu.app.mvp.find.bean;

import com.scj.beilu.app.mvp.common.bean.FormatTimeBean;

import java.util.ArrayList;

/**
 * @author Mingxun
 * @time on 2019/2/19 10:33
 */
public class FindInfoBean extends FormatTimeBean {

    /**
     * dynamicId : 10
     * userId : 1
     * userHead : https://beilutest.oss-cn-hangzhou.aliyuncs.com/user/head/360bc012-4584-4ad5-a65d-0ce13fb48d3d20190124163332.jpg
     * userNickName : null
     * dynamicDec : 开饭啦刷卡机热量高科技hi喜欢
     * dynamicDate : 2019-01-31 18:26:59
     * dynamicVideoAddr : https://beilutest.oss-cn-hangzhou.aliyuncs.com/dynamic/video/1675fb18bb9e7433a502ef92eb4c00da.mp4
     * dynamicVideoPic : https://beilutest.oss-cn-hangzhou.aliyuncs.com/dynamic/videoPic/12323453465678765.jpg
     * dynamicLikeCount : 2112
     * dynamicBrowseCount : 44
     * dynamicOrganId : 0
     * commentCount : 25
     * dynamicPic : []
     * isLike : 0
     * isFocus : 0
     * isOwn : 0
     * dynamicOrganName : null
     */


    private int dynamicId;
    private int userId;
    private String userHead;
    private String userNickName;
    private String dynamicDec;
    private String dynamicDate;
    private String dynamicVideoAddr;
    private String dynamicVideoPic;
    private int dynamicLikeCount;
    private int dynamicBrowseCount;
    private int dynamicOrganId;
    private int commentCount;
    private long dynamicShareCount;
    private int isLike;
    private int isFocus;//0未关注 1 是已关注
    private String dynamicOrganName;
    private ArrayList<FindImageBean> dynamicPic;
    private int isOwn;
    private String dynamicShareAddr;

    public String getDynamicShareAddr() {
        return dynamicShareAddr;
    }

    public void setDynamicShareAddr(String dynamicShareAddr) {
        this.dynamicShareAddr = dynamicShareAddr;
    }

    public long getDynamicShareCount() {
        return dynamicShareCount;
    }

    public void setDynamicShareCount(long dynamicShareCount) {
        this.dynamicShareCount = dynamicShareCount;
    }

    public int getIsOwn() {
        return isOwn;
    }

    public void setIsOwn(int isOwn) {
        this.isOwn = isOwn;
    }

    public int getDynamicId() {
        return dynamicId;
    }

    public void setDynamicId(int dynamicId) {
        this.dynamicId = dynamicId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserHead() {
        return userHead;
    }

    public void setUserHead(String userHead) {
        this.userHead = userHead;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public String getDynamicDec() {
        return dynamicDec;
    }

    public void setDynamicDec(String dynamicDec) {
        this.dynamicDec = dynamicDec;
    }

    public String getDynamicDate() {
        return dynamicDate;
    }

    public void setDynamicDate(String dynamicDate) {
        this.dynamicDate = dynamicDate;
    }

    public String getDynamicVideoAddr() {
        return dynamicVideoAddr;
    }

    public void setDynamicVideoAddr(String dynamicVideoAddr) {
        this.dynamicVideoAddr = dynamicVideoAddr;
    }

    public String getDynamicVideoPic() {
        return dynamicVideoPic;
    }

    public void setDynamicVideoPic(String dynamicVideoPic) {
        this.dynamicVideoPic = dynamicVideoPic;
    }

    public int getDynamicLikeCount() {
        return dynamicLikeCount;
    }

    public void setDynamicLikeCount(int dynamicLikeCount) {
        this.dynamicLikeCount = dynamicLikeCount;
    }

    public int getDynamicBrowseCount() {
        return dynamicBrowseCount;
    }

    public void setDynamicBrowseCount(int dynamicBrowseCount) {
        this.dynamicBrowseCount = dynamicBrowseCount;
    }

    public int getDynamicOrganId() {
        return dynamicOrganId;
    }

    public void setDynamicOrganId(int dynamicOrganId) {
        this.dynamicOrganId = dynamicOrganId;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public int getIsLike() {
        return isLike;
    }

    public void setIsLike(int isLike) {
        this.isLike = isLike;
    }

    public int getIsFocus() {
        return isFocus;
    }

    public void setIsFocus(int isFocus) {
        this.isFocus = isFocus;
    }

    public String getDynamicOrganName() {
        return dynamicOrganName;
    }

    public void setDynamicOrganName(String dynamicOrganName) {
        this.dynamicOrganName = dynamicOrganName;
    }

    public ArrayList<FindImageBean> getDynamicPic() {
        return dynamicPic;
    }

    public void setDynamicPic(ArrayList<FindImageBean> dynamicPic) {
        this.dynamicPic = dynamicPic;
    }

    @Override
    public String getFormatDate() {
        return getDynamicDate();
    }
}
