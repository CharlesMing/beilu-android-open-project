package com.scj.beilu.app.mvp.mine.message.model;

import com.scj.beilu.app.mvp.mine.message.bean.MsgCommentInfoResultBean;
import com.scj.beilu.app.mvp.mine.message.bean.MsgLikeInfoResultBean;
import com.scj.beilu.app.mvp.mine.message.bean.MsgReplyInfoResultBean;

import io.reactivex.Observable;

/**
 * @author Mingxun
 * @time on 2019/4/9 20:29
 */
public interface IMsgManager {
    Observable<MsgReplyInfoResultBean> getReplyMsgList(int currentPage);

    Observable<MsgLikeInfoResultBean> getLikeMsgList(int currentPage);

    Observable<MsgCommentInfoResultBean> getCommentMsgList(int currentPage);
}
