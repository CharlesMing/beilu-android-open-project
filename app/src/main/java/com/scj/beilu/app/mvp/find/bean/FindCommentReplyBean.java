package com.scj.beilu.app.mvp.find.bean;

/**
 * @author Mingxun
 * @time on 2019/2/28 11:53
 */
public class FindCommentReplyBean {

    /**
     * dynamicComReplyId : 1
     * comId : 0
     * dynamicReplyContent : 我回复了你
     * fromUserId : 56
     * fromUserName : beilu0
     * fromUserHead : https://beilutest.oss-cn-hangzhou.aliyuncs.com/user/head/zip79318f38-84c1-4c80-a4ff-89ae79a33a2020190257230537.jpg
     * toUserId : 56
     * toUserName : beilu0
     * toUserHead : https://beilutest.oss-cn-hangzhou.aliyuncs.com/user/head/zip79318f38-84c1-4c80-a4ff-89ae79a33a2020190257230537.jpg
     * dynamicReplyDate : 2019-02-28 11:50:40
     */

    private int dynamicComReplyId;
    private int comId;
    private String dynamicReplyContent;
    private int fromUserId;
    private String fromUserName;
    private String fromUserHead;
    private int toUserId;
    private String toUserName;
    private String toUserHead;
    private String dynamicReplyDate;

    public int getDynamicComReplyId() {
        return dynamicComReplyId;
    }

    public void setDynamicComReplyId(int dynamicComReplyId) {
        this.dynamicComReplyId = dynamicComReplyId;
    }

    public int getComId() {
        return comId;
    }

    public void setComId(int comId) {
        this.comId = comId;
    }

    public String getDynamicReplyContent() {
        return dynamicReplyContent;
    }

    public void setDynamicReplyContent(String dynamicReplyContent) {
        this.dynamicReplyContent = dynamicReplyContent;
    }

    public int getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(int fromUserId) {
        this.fromUserId = fromUserId;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public String getFromUserHead() {
        return fromUserHead;
    }

    public void setFromUserHead(String fromUserHead) {
        this.fromUserHead = fromUserHead;
    }

    public int getToUserId() {
        return toUserId;
    }

    public void setToUserId(int toUserId) {
        this.toUserId = toUserId;
    }

    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }

    public String getToUserHead() {
        return toUserHead;
    }

    public void setToUserHead(String toUserHead) {
        this.toUserHead = toUserHead;
    }

    public String getDynamicReplyDate() {
        return dynamicReplyDate;
    }

    public void setDynamicReplyDate(String dynamicReplyDate) {
        this.dynamicReplyDate = dynamicReplyDate;
    }
}
