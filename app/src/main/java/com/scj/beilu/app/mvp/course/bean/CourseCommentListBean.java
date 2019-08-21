package com.scj.beilu.app.mvp.course.bean;

import com.scj.beilu.app.mvp.common.bean.FormatResult;
import com.scj.beilu.app.mvp.common.bean.PageBean;

/**
 * @author Mingxun
 * @time on 2019/3/25 17:28
 */
public class CourseCommentListBean extends FormatResult<CourseCommentInfoBean> {

    /**
     * page : {"currentPage":1,"list":[{"courseCommentId":6,"courseId":21,"userId":72,"userName":"明巡","userHead":"http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLknksDZvB81soMRxgNYEicAyL8w0SKrm7zgHwpz4S3iaNnHBYUib685d4k0BUAicsyHemaROGPL8IU5Q/132","comContent":"理解理解","comDate":"2019-03-25 20:26:06","replies":[]},{"courseCommentId":5,"courseId":21,"userId":65,"userName":"明巡","userHead":"http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLknksDZvB81soMRxgNYEicAyL8w0SKrm7zgHwpz4S3iaNnHBYUib685d4Jr8wA17o5KKH8KX0p54uMw/132","comContent":"评论了","comDate":"2019-03-25 17:46:10","replies":[]},{"courseCommentId":4,"courseId":21,"userId":65,"userName":"明巡","userHead":"http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLknksDZvB81soMRxgNYEicAyL8w0SKrm7zgHwpz4S3iaNnHBYUib685d4Jr8wA17o5KKH8KX0p54uMw/132","comContent":"你好啊","comDate":"2019-03-07 11:10:36","replies":[{"courseComReplyId":4,"courseComId":4,"fromUserId":65,"fromUserName":"明巡","fromUserHead":"http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLknksDZvB81soMRxgNYEicAyL8w0SKrm7zgHwpz4S3iaNnHBYUib685d4Jr8wA17o5KKH8KX0p54uMw/132","toUserId":64,"toUserName":"这里是账号名称","toUserHead":"https://beilutest.oss-cn-hangzhou.aliyuncs.com/user/head/987654321-20190214104729.png","courseComReplyContent":"啦啦啦啦","courseReplyDate":"2019-03-07 11:20:14"}]},{"courseCommentId":3,"courseId":21,"userId":65,"userName":"明巡","userHead":"http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLknksDZvB81soMRxgNYEicAyL8w0SKrm7zgHwpz4S3iaNnHBYUib685d4Jr8wA17o5KKH8KX0p54uMw/132","comContent":"你好啊","comDate":"2019-03-07 11:09:01","replies":[]},{"courseCommentId":2,"courseId":21,"userId":62,"userName":"1","userHead":"https://beilutest.oss-cn-hangzhou.aliyuncs.com/user/head/987654321-20190214104729.png","comContent":"321","comDate":"2019-03-06 19:31:48","replies":[]},{"courseCommentId":1,"courseId":21,"userId":61,"userName":"小明45","userHead":"https://beilutest.oss-cn-hangzhou.aliyuncs.com/user/head/zip5afc7bc0-3da7-42c6-b1bd-cf329f52737120190360221606.jpg","comContent":"123","comDate":"2019-03-06 19:31:36","replies":[{"courseComReplyId":3,"courseComId":1,"fromUserId":64,"fromUserName":"这里是账号名称","fromUserHead":"https://beilutest.oss-cn-hangzhou.aliyuncs.com/user/head/987654321-20190214104729.png","toUserId":63,"toUserName":"beilu63","toUserHead":"https://beilutest.oss-cn-hangzhou.aliyuncs.com/user/head/zip8e19cfa8-c10f-49d3-a3dc-18f38f17d4d920190364112408.jpg","courseComReplyContent":"fdsa发送","courseReplyDate":"2019-03-06 19:39:03"},{"courseComReplyId":2,"courseComId":1,"fromUserId":63,"fromUserName":"beilu63","fromUserHead":"https://beilutest.oss-cn-hangzhou.aliyuncs.com/user/head/zip8e19cfa8-c10f-49d3-a3dc-18f38f17d4d920190364112408.jpg","toUserId":62,"toUserName":"1","toUserHead":"https://beilutest.oss-cn-hangzhou.aliyuncs.com/user/head/987654321-20190214104729.png","courseComReplyContent":"sdf","courseReplyDate":"2019-03-06 19:38:43"},{"courseComReplyId":1,"courseComId":1,"fromUserId":62,"fromUserName":"1","fromUserHead":"https://beilutest.oss-cn-hangzhou.aliyuncs.com/user/head/987654321-20190214104729.png","toUserId":62,"toUserName":"1","toUserHead":"https://beilutest.oss-cn-hangzhou.aliyuncs.com/user/head/987654321-20190214104729.png","courseComReplyContent":"123","courseReplyDate":"2019-03-06 19:38:11"}]}],"startCount":0,"nextPage":1,"totalCount":0}
     */

    private PageBean<CourseCommentInfoBean> page;

    public PageBean<CourseCommentInfoBean> getPage() {
        return page;
    }

    public void setPage(PageBean<CourseCommentInfoBean> page) {
        this.page = page;
    }
}
