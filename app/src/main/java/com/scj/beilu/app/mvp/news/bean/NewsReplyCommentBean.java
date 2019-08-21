package com.scj.beilu.app.mvp.news.bean;

/**
 * @author Mingxun
 * @time on 2019/2/17 15:29
 */
public class NewsReplyCommentBean {

    /**
     * newsComReplyId : 9
     * newsComId : 16
     * newsReplyContent : ꒰⌗´͈ ᵕ `͈⌗꒱৩
     * fromUserId : 11
     * fromUserName : 我可以吗
     * fromUserHead : https://beilutest.oss-cn-hangzhou.aliyuncs.com/user/head/zip/41609b3b-75ee-4ef1-af22-24d511dbd52a20190247124231.jpeg
     * toUserId : 12
     * toUserName : Mingxun
     * toUserHead : https://beilutest.oss-cn-hangzhou.aliyuncs.com/user/head/987654321-20190214104729.png
     * newsReplyDate : 2019-02-16 16:14:07
     */

    private int newsComReplyId;
    private int newsComId;
    private String newsReplyContent;
    private int fromUserId;
    private String fromUserName;
    private String fromUserHead;
    private int toUserId;
    private String toUserName;
    private String toUserHead;
    private String newsReplyDate;

    public int getNewsComReplyId() {
        return newsComReplyId;
    }

    public void setNewsComReplyId(int newsComReplyId) {
        this.newsComReplyId = newsComReplyId;
    }

    public int getNewsComId() {
        return newsComId;
    }

    public void setNewsComId(int newsComId) {
        this.newsComId = newsComId;
    }

    public String getNewsReplyContent() {
        return newsReplyContent;
    }

    public void setNewsReplyContent(String newsReplyContent) {
        this.newsReplyContent = newsReplyContent;
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

    public String getNewsReplyDate() {
        return newsReplyDate;
    }

    public void setNewsReplyDate(String newsReplyDate) {
        this.newsReplyDate = newsReplyDate;
    }

    @Override
    public String toString() {
        return "NewsReplyCommentBean{" +
                "newsComReplyId=" + newsComReplyId +
                ", newsComId=" + newsComId +
                ", newsReplyContent='" + newsReplyContent + '\'' +
                ", fromUserId=" + fromUserId +
                ", fromUserName='" + fromUserName + '\'' +
                ", fromUserHead='" + fromUserHead + '\'' +
                ", toUserId=" + toUserId +
                ", toUserName='" + toUserName + '\'' +
                ", toUserHead='" + toUserHead + '\'' +
                ", newsReplyDate='" + newsReplyDate + '\'' +
                '}';
    }
}
