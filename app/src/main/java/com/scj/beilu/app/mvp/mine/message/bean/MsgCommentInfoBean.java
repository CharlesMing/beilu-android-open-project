package com.scj.beilu.app.mvp.mine.message.bean;

import com.scj.beilu.app.mvp.common.bean.FormatTimeBean;

/**
 * @author Mingxun
 * @time on 2019/4/9 21:17
 */
public class MsgCommentInfoBean extends FormatTimeBean {
    /**
     * comId : 0
     * dynamicId : 109
     * userId : 72
     * userName : 笑笑
     * userHead : http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIneSeCXgywdLQGzqib7hom3O8h7ng8preRtpBu0LYzl68p2BlMwcUU5y4cp8nK9Zo1BWqib8Bp1aRQ/132
     * userHeadZip : http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIneSeCXgywdLQGzqib7hom3O8h7ng8preRtpBu0LYzl68p2BlMwcUU5y4cp8nK9Zo1BWqib8Bp1aRQ/132
     * comContent : 明巡评论了你
     * comTime : 2019-04-09 21:12:52
     */

    private int comId;
    private int dynamicId;
    private int userId;
    private String userName;
    private String userHead;
    private String userHeadZip;
    private String comContent;
    private String comTime;

    public int getComId() {
        return comId;
    }

    public void setComId(int comId) {
        this.comId = comId;
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

    public String getComContent() {
        return comContent;
    }

    public void setComContent(String comContent) {
        this.comContent = comContent;
    }

    public String getComTime() {
        return comTime;
    }

    public void setComTime(String comTime) {
        this.comTime = comTime;
    }

    @Override
    public String getFormatDate() {
        return getComTime();
    }
}
