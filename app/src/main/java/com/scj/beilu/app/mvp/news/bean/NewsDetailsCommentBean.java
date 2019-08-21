package com.scj.beilu.app.mvp.news.bean;

import com.scj.beilu.app.mvp.common.bean.PageBean;
import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;

/**
 * @author Mingxun
 * @time on 2019/2/15 21:46
 */
public class NewsDetailsCommentBean extends ResultMsgBean {

    /**
     * result : 5000
     * page : {"list":[{"comId":12,"newsId":72,"userId":12,"userName":"Mingxun","userHead":"https://beilutest.oss-cn-hangzhou.aliyuncs.com/user/head/987654321-20190214104729.png","comDate":"2019-02-15 22:25:16","comContent":"哈哈哈","newsComReplyList":[]},{"comId":13,"newsId":72,"userId":12,"userName":"Mingxun","userHead":"https://beilutest.oss-cn-hangzhou.aliyuncs.com/user/head/987654321-20190214104729.png","comDate":"2019-02-15 22:34:55","comContent":"꒰⌗´͈ ᵕ `͈⌗꒱৩","newsComReplyList":[]},{"comId":14,"newsId":72,"userId":12,"userName":"Mingxun","userHead":"https://beilutest.oss-cn-hangzhou.aliyuncs.com/user/head/987654321-20190214104729.png","comDate":"2019-02-15 22:58:18","comContent":"○ლ(´ڡ`ლ)元宵","newsComReplyList":[]},{"comId":15,"newsId":72,"userId":12,"userName":"Mingxun","userHead":"https://beilutest.oss-cn-hangzhou.aliyuncs.com/user/head/987654321-20190214104729.png","comDate":"2019-02-15 22:59:47","comContent":"(｡◝ᴗ◜ʃƪ)","newsComReplyList":[]},{"comId":16,"newsId":72,"userId":12,"userName":"Mingxun","userHead":"https://beilutest.oss-cn-hangzhou.aliyuncs.com/user/head/987654321-20190214104729.png","comDate":"2019-02-15 22:59:58","comContent":"😂😂😂","newsComReplyList":[]}],"totalCount":5,"totalPageCount":1,"currentPage":1,"startCount":0,"nextPage":1,"previousPage":0,"firstPage":0,"endPage":0}
     */
    private PageBean<NewsCommentBean> page;

    public PageBean<NewsCommentBean> getPage() {
        return page;
    }

    public void setPage(PageBean<NewsCommentBean> page) {
        this.page = page;
    }
}
