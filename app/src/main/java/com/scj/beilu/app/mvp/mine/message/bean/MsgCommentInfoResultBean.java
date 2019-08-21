package com.scj.beilu.app.mvp.mine.message.bean;

import com.scj.beilu.app.mvp.common.bean.FormatResult;
import com.scj.beilu.app.mvp.common.bean.PageBean;

/**
 * @author Mingxun
 * @time on 2019/4/9 20:16
 */
public class MsgCommentInfoResultBean extends FormatResult<MsgCommentInfoBean> {

    /**
     * page : {"currentPage":1,"list":[{"comId":0,"dynamicId":109,"userId":72,"userName":"笑笑","userHead":"http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIneSeCXgywdLQGzqib7hom3O8h7ng8preRtpBu0LYzl68p2BlMwcUU5y4cp8nK9Zo1BWqib8Bp1aRQ/132","userHeadZip":"http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIneSeCXgywdLQGzqib7hom3O8h7ng8preRtpBu0LYzl68p2BlMwcUU5y4cp8nK9Zo1BWqib8Bp1aRQ/132","comContent":"明巡评论了你","comTime":"2019-04-09 21:12:52"}],"startCount":0,"nextPage":1,"totalCount":0}
     */

    private PageBean<MsgCommentInfoBean> page;

    public PageBean<MsgCommentInfoBean> getPage() {
        return page;
    }

    public void setPage(PageBean<MsgCommentInfoBean> page) {
        this.page = page;
    }
}
