package com.scj.beilu.app.mvp.mine.message.bean;

import com.scj.beilu.app.mvp.common.bean.FormatTimeBean;

/**
 * @author Mingxun
 * @time on 2019/4/9 21:18
 */
public class MsgLikeInfoBean extends FormatTimeBean {
    /**
     * likeId : 0
     * dynamicId : 0
     * fromUserId : 72
     * userName : 明巡
     * userHead : http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLknksDZvB81soMRxgNYEicAyL8w0SKrm7zgHwpz4S3iaNnHBYUib685d4k0BUAicsyHemaROGPL8IU5Q/132
     * userHeadZip : http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLknksDZvB81soMRxgNYEicAyL8w0SKrm7zgHwpz4S3iaNnHBYUib685d4k0BUAicsyHemaROGPL8IU5Q/132
     * likeReplyTime : 2019-04-08 17:59
     * likeContent : 明巡喜欢了你的作品
     */

    private int likeId;
    private int dynamicId;
    private int fromUserId;
    private String userName;
    private String userHead;
    private String userHeadZip;
    private String likeReplyTime;
    private String likeContent;

    public int getLikeId() {
        return likeId;
    }

    public void setLikeId(int likeId) {
        this.likeId = likeId;
    }

    public int getDynamicId() {
        return dynamicId;
    }

    public void setDynamicId(int dynamicId) {
        this.dynamicId = dynamicId;
    }

    public int getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(int fromUserId) {
        this.fromUserId = fromUserId;
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

    public String getLikeReplyTime() {
        return likeReplyTime;
    }

    public void setLikeReplyTime(String likeReplyTime) {
        this.likeReplyTime = likeReplyTime;
    }

    public String getLikeContent() {
        return likeContent;
    }

    public void setLikeContent(String likeContent) {
        this.likeContent = likeContent;
    }

    @Override
    public String getFormatDate() {
        return getLikeReplyTime();
    }
}
