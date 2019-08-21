package com.scj.beilu.app.mvp.news.bean;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/2/16 14:26
 */
public class NewsCommentBean {
    /**
     * comId : 12
     * newsId : 72
     * userId : 12
     * userName : Mingxun
     * userHead : https://beilutest.oss-cn-hangzhou.aliyuncs.com/user/head/987654321-20190214104729.png
     * comDate : 2019-02-15 22:25:16
     * comContent : 哈哈哈
     * newsComReplyList : []
     */

    private int comId;
    private int newsId;
    private int userId;
    private String userName;
    private String userHead;
    private String comDate;
    private String comContent;
    private List<NewsReplyCommentBean> newsComReplyList;

    public int getComId() {
        return comId;
    }

    public void setComId(int comId) {
        this.comId = comId;
    }

    public int getNewsId() {
        return newsId;
    }

    public void setNewsId(int newsId) {
        this.newsId = newsId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserHead() {
        return userHead;
    }

    public void setUserHead(String userHead) {
        this.userHead = userHead;
    }

    public String getComDate() {
        return comDate;
    }

    public void setComDate(String comDate) {
        this.comDate = comDate;
    }

    public String getComContent() {
        return comContent;
    }

    public void setComContent(String comContent) {
        this.comContent = comContent;
    }

    public List<NewsReplyCommentBean> getNewsComReplyList() {
        return newsComReplyList;
    }

    public void setNewsComReplyList(List<NewsReplyCommentBean> newsComReplyList) {
        this.newsComReplyList = newsComReplyList;
    }
}
