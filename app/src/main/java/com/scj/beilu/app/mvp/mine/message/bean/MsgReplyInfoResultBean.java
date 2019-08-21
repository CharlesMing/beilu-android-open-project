package com.scj.beilu.app.mvp.mine.message.bean;

import com.scj.beilu.app.mvp.common.bean.FormatResult;
import com.scj.beilu.app.mvp.common.bean.PageBean;

/**
 * @author Mingxun
 * @time on 2019/4/9 20:13
 */
public class MsgReplyInfoResultBean extends FormatResult<MsgReplyInfoBean> {

    private PageBean<MsgReplyInfoBean> page;

    public PageBean<MsgReplyInfoBean> getPage() {
        return page;
    }

    public void setPage(PageBean<MsgReplyInfoBean> page) {
        this.page = page;
    }
}
