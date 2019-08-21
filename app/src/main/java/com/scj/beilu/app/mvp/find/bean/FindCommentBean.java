package com.scj.beilu.app.mvp.find.bean;

import com.scj.beilu.app.mvp.common.bean.FormatTimeBean;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/2/23 17:56
 */
public class FindCommentBean extends FormatTimeBean {
    /**
     * comId : 2
     * dynamicId : 1
     * comUserId : 3
     * comUserHead : dsfasf
     * comUserName : wabg
     * comDate : 2019-02-12 14:21:09
     * comContent : 你哈
     * comReplies :[{"dynamicComReplyId":1,"comId":0,"dynamicReplyContent":"我回复了你","fromUserId":56,"fromUserName":"beilu0","fromUserHead":"https://beilutest.oss-cn-hangzhou.aliyuncs.com/user/head/zip79318f38-84c1-4c80-a4ff-89ae79a33a2020190257230537.jpg","toUserId":56,"toUserName":"beilu0","toUserHead":"https://beilutest.oss-cn-hangzhou.aliyuncs.com/user/head/zip79318f38-84c1-4c80-a4ff-89ae79a33a2020190257230537.jpg","dynamicReplyDate":"2019-02-28 11:50:40"}]
     */

    private int comId;
    private int dynamicId;
    private int comUserId;
    private String comUserHead;
    private String comUserName;
    private String comDate;
    private String comContent;
    private List<FindCommentReplyBean> comReplies;

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

    public int getComUserId() {
        return comUserId;
    }

    public void setComUserId(int comUserId) {
        this.comUserId = comUserId;
    }

    public String getComUserHead() {
        return comUserHead;
    }

    public void setComUserHead(String comUserHead) {
        this.comUserHead = comUserHead;
    }

    public String getComUserName() {
        return comUserName;
    }

    public void setComUserName(String comUserName) {
        this.comUserName = comUserName;
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

    public List<FindCommentReplyBean> getComReplies() {
        return comReplies;
    }

    public void setComReplies(List<FindCommentReplyBean> comReplies) {
        this.comReplies = comReplies;
    }

    @Override
    public String getFormatDate() {
        return getComDate();
    }
}
