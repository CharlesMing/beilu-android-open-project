package com.scj.beilu.app.mvp.notify;

/**
 * @author Mingxun
 * @time on 2019/4/8 17:28
 */
public class MsgContentBean {

    /**
     * msgContent : 笑笑喜欢了你的作品
     * msgType : 2
     * msgTime : 2019-04-08 17:27
     */

    private String msgContent;
    private int msgType;
    private String msgTime;

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    public int getMsgType() {
        return msgType;
    }

    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }

    public String getMsgTime() {
        return msgTime;
    }

    public void setMsgTime(String msgTime) {
        this.msgTime = msgTime;
    }
}
