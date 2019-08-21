package com.scj.beilu.app.mvp.find.bean;

import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;

/**
 * @author Mingxun
 * @time on 2019/2/23 17:02
 */
public class LikeResultBean extends ResultMsgBean {
    private int like;

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }
}
