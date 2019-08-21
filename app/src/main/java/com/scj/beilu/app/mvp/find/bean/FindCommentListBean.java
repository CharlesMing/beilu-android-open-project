package com.scj.beilu.app.mvp.find.bean;

import com.scj.beilu.app.mvp.common.bean.FormatResult;
import com.scj.beilu.app.mvp.common.bean.PageBean;

/**
 * @author Mingxun
 * @time on 2019/2/23 17:56
 */
public class FindCommentListBean extends FormatResult<FindCommentBean> {

    /**
     * page : {"currentPage":1,"list":[{"comId":2,"dynamicId":1,"comUserId":3,"comUserHead":"dsfasf","comUserName":"wabg","comDate":"2019-02-12 14:21:09","comContent":"你哈","comReplies":[]},{"comId":1,"dynamicId":1,"comUserId":2,"comUserHead":"safasdfsd","comUserName":"shen","comDate":"2019-01-30 14:20:29","comContent":"你好","comReplies":[{"dynamicComReplyId":1,"comId":0,"dynamicReplyContent":"好嗨哟","fromUserId":1,"fromUserName":"shenyuewang","fromUserHead":"https://beilutest.oss-cn-hangzhou.aliyuncs.com/zip360bc012-4584-4ad5-a65d-0ce13fb48d3d20190124163332.jpg","toUserId":2,"toUserName":"张三","toUserHead":"https://beilutest.oss-cn-hangzhou.aliyuncs.com/zip360bc012-4584-4ad5-a65d-0ce13fb48d3d20190124163332.jpg","dynamicReplyDate":"2019-02-13 15:23:26"},{"dynamicComReplyId":2,"comId":0,"dynamicReplyContent":"感觉人生已经到达了高潮","fromUserId":2,"fromUserName":"张三","fromUserHead":"https://beilutest.oss-cn-hangzhou.aliyuncs.com/user/head/360bc012-4584-4ad5-a65d-0ce13fb48d3d20190124163332.jpg","toUserId":1,"toUserName":"shenyuewang","toUserHead":"https://beilutest.oss-cn-hangzhou.aliyuncs.com/user/head/360bc012-4584-4ad5-a65d-0ce13fb48d3d20190124163332.jpg","dynamicReplyDate":"2019-02-13 15:24:31"}]}],"startCount":0,"nextPage":0}
     */

    private PageBean<FindCommentBean> page;

    public PageBean<FindCommentBean> getPage() {
        return page;
    }

    public void setPage(PageBean<FindCommentBean> page) {
        this.page = page;
    }
}
