package com.scj.beilu.app.mvp.course.bean;

import com.scj.beilu.app.mvp.common.bean.FormatTimeBean;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/3/25 17:28
 */
public class CourseCommentInfoBean extends FormatTimeBean {

    /**
     * courseCommentId : 6
     * courseId : 21
     * userId : 72
     * userName : 明巡
     * userHead : http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLknksDZvB81soMRxgNYEicAyL8w0SKrm7zgHwpz4S3iaNnHBYUib685d4k0BUAicsyHemaROGPL8IU5Q/132
     * userHeadCompression : http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLknksDZvB81soMRxgNYEicAyL8w0SKrm7zgHwpz4S3iaNnHBYUib685d4k0BUAicsyHemaROGPL8IU5Q/132
     * comContent : 理解理解
     * comDate : 2019-03-25 20:26:06
     * replies : []
     */

    private int courseCommentId;
    private int courseId;
    private int userId;
    private String userName;
    private String userHead;
    private String userHeadCompression;
    private String comContent;
    private String comDate;
    private List<?> replies;

    public int getCourseCommentId() {
        return courseCommentId;
    }

    public void setCourseCommentId(int courseCommentId) {
        this.courseCommentId = courseCommentId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
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

    public String getUserHeadCompression() {
        return userHeadCompression;
    }

    public void setUserHeadCompression(String userHeadCompression) {
        this.userHeadCompression = userHeadCompression;
    }

    public String getComContent() {
        return comContent;
    }

    public void setComContent(String comContent) {
        this.comContent = comContent;
    }

    public String getComDate() {
        return comDate;
    }

    public void setComDate(String comDate) {
        this.comDate = comDate;
    }

    public List<?> getReplies() {
        return replies;
    }

    public void setReplies(List<?> replies) {
        this.replies = replies;
    }

    @Override
    public String getFormatDate() {
        return getComDate();
    }


}
