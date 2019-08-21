package com.scj.beilu.app.mvp.mine.message.model;

import com.mx.pro.lib.mvp.exception.UserException;
import com.scj.beilu.app.api.MessageApi;
import com.scj.beilu.app.mvp.base.user.BaseLoadUserInfoDelegate;
import com.scj.beilu.app.mvp.mine.message.bean.MsgCommentInfoBean;
import com.scj.beilu.app.mvp.mine.message.bean.MsgCommentInfoResultBean;
import com.scj.beilu.app.mvp.mine.message.bean.MsgLikeInfoBean;
import com.scj.beilu.app.mvp.mine.message.bean.MsgLikeInfoResultBean;
import com.scj.beilu.app.mvp.mine.message.bean.MsgReplyInfoBean;
import com.scj.beilu.app.mvp.mine.message.bean.MsgReplyInfoResultBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/**
 * @author Mingxun
 * @time on 2019/4/9 20:32
 */
public class MsgManagerImpl extends BaseLoadUserInfoDelegate implements IMsgManager {
    private MessageApi mMessageApi;

    public MsgManagerImpl(Builder builder) {
        super(builder);
        mMessageApi = create(MessageApi.class);
    }

    @Override
    public Observable<MsgReplyInfoResultBean> getReplyMsgList(final int currentPage) {
        return getHeader()
                .flatMap(new Function<Map<String, String>, ObservableSource<MsgReplyInfoResultBean>>() {
                    @Override
                    public ObservableSource<MsgReplyInfoResultBean> apply(Map<String, String> token) throws Exception {
                        if (token.size() == 0) {
                            throw new UserException();
                        } else {
                            return createObservable(mMessageApi.getReplyMsgList(token, currentPage));
                        }
                    }
                })
                .flatMap(new Function<MsgReplyInfoResultBean, ObservableSource<MsgReplyInfoResultBean>>() {
                    @Override
                    public ObservableSource<MsgReplyInfoResultBean> apply(MsgReplyInfoResultBean commentListBean) throws Exception {
                        List<MsgReplyInfoBean> list = commentListBean.getPage().getList();
                        //处理时间格式
                        return dealWithTime(list, commentListBean);
                    }
                });
    }

    @Override
    public Observable<MsgLikeInfoResultBean> getLikeMsgList(final int currentPage) {
        return getHeader()
                .flatMap(new Function<Map<String, String>, ObservableSource<MsgLikeInfoResultBean>>() {
                    @Override
                    public ObservableSource<MsgLikeInfoResultBean> apply(Map<String, String> token) throws Exception {
                        if (token.size() == 0) {
                            throw new UserException();
                        } else {
                            return createObservable(mMessageApi.getLikeMsgList(token, currentPage));
                        }
                    }
                })
                .flatMap(new Function<MsgLikeInfoResultBean, ObservableSource<MsgLikeInfoResultBean>>() {
                    @Override
                    public ObservableSource<MsgLikeInfoResultBean> apply(MsgLikeInfoResultBean commentListBean) throws Exception {
                        List<MsgLikeInfoBean> list = commentListBean.getPage().getList();
                        //处理时间格式
                        return dealWithTime(list, commentListBean);
                    }
                });
    }

    @Override
    public Observable<MsgCommentInfoResultBean> getCommentMsgList(final int currentPage) {
        return getHeader()
                .flatMap(new Function<Map<String, String>, ObservableSource<MsgCommentInfoResultBean>>() {
                    @Override
                    public ObservableSource<MsgCommentInfoResultBean> apply(Map<String, String> token) throws Exception {
                        if (token.size() == 0) {
                            throw new UserException();
                        } else {
                            return createObservable(mMessageApi.getCommentMsgList(token, currentPage));
                        }
                    }
                })
                .flatMap(new Function<MsgCommentInfoResultBean, ObservableSource<MsgCommentInfoResultBean>>() {
                    @Override
                    public ObservableSource<MsgCommentInfoResultBean> apply(MsgCommentInfoResultBean commentListBean) throws Exception {
                        List<MsgCommentInfoBean> list = commentListBean.getPage().getList();
                        //处理时间格式
                        return dealWithTime(list, commentListBean);
                    }
                });
    }
}
