package com.scj.beilu.app.mvp.news.bean;

import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;

/**
 * @author Mingxun
 * @time on 2019/5/13 12:12
 */
public class NewsAuthorInfoResultBean extends ResultMsgBean {
    private NewsAuthorInfoBean mediaUser;

    public NewsAuthorInfoBean getMediaUser() {
        return mediaUser;
    }

    public void setMediaUser(NewsAuthorInfoBean mediaUser) {
        this.mediaUser = mediaUser;
    }
}
