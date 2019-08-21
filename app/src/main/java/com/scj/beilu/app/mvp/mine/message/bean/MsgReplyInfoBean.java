package com.scj.beilu.app.mvp.mine.message.bean;

import com.scj.beilu.app.mvp.common.bean.FormatTimeBean;

/**
 * @author Mingxun
 * @time on 2019/4/9 20:13
 */
public class MsgReplyInfoBean extends FormatTimeBean {
    /**
     * replyId : 0
     * dynamicId : 109
     * userId : 75
     * userName : 笑笑
     * userHead : http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIneSeCXgywdLQGzqib7hom3O8h7ng8preRtpBu0LYzl68p2BlMwcUU5y4cp8nK9Zo1BWqib8Bp1aRQ/132
     * userHeadZip : http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIneSeCXgywdLQGzqib7hom3O8h7ng8preRtpBu0LYzl68p2BlMwcUU5y4cp8nK9Zo1BWqib8Bp1aRQ/132
     * replyContent : 笑笑回复了你
     * replyTime : 2019-04-09 21:15:53
     */

    private int replyId;
    private int dynamicId;
    private int userId;
    private String userName;
    private String userHead;
    private String userHeadZip;
    private String replyContent;
    private String replyTime;

    public int getReplyId() {
        return replyId;
    }

    public void setReplyId(int replyId) {
        this.replyId = replyId;
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

    public String getUserHeadZip() {
        return userHeadZip;
    }

    public void setUserHeadZip(String userHeadZip) {
        this.userHeadZip = userHeadZip;
    }

    public String getReplyContent() {
        return replyContent;
    }

    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }

    public String getReplyTime() {
        return replyTime;
    }

    public void setReplyTime(String replyTime) {
        this.replyTime = replyTime;
    }

    @Override
    public String getFormatDate() {
        return getReplyTime();
    }
}
