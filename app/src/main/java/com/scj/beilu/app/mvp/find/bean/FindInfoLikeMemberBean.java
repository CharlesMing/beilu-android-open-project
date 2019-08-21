package com.scj.beilu.app.mvp.find.bean;

/**
 * @author Mingxun
 * @time on 2019/2/23 16:59
 */
public class FindInfoLikeMemberBean {

    /**
     * dynamicId : 1
     * userId : 1
     * userHead : https://beilutest.oss-cn-hangzhou.aliyuncs.com/user/head/360bc012-4584-4ad5-a65d-0ce13fb48d3d20190124163332.jpg
     */

    private int dynamicId;
    private long userId;
    private String userHead;

    public FindInfoLikeMemberBean(int dynamicId, long userId, String userHead) {
        this.dynamicId = dynamicId;
        this.userId = userId;
        this.userHead = userHead;
    }

    public int getDynamicId() {
        return dynamicId;
    }

    public void setDynamicId(int dynamicId) {
        this.dynamicId = dynamicId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserHead() {
        return userHead;
    }

    public void setUserHead(String userHead) {
        this.userHead = userHead;
    }
}
