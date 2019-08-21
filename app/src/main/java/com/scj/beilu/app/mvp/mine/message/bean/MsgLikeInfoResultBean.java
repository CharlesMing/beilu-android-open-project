package com.scj.beilu.app.mvp.mine.message.bean;

import com.scj.beilu.app.mvp.common.bean.FormatResult;
import com.scj.beilu.app.mvp.common.bean.PageBean;

/**
 * @author Mingxun
 * @time on 2019/4/9 20:15
 */
public class MsgLikeInfoResultBean extends FormatResult<MsgLikeInfoBean> {

    private PageBean<MsgLikeInfoBean> page;

    public PageBean<MsgLikeInfoBean> getPage() {
        return page;
    }

    public void setPage(PageBean<MsgLikeInfoBean> page) {
        this.page = page;
    }
}
