package com.scj.beilu.app.mvp.find.bean;

import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;

/**
 * @author Mingxun
 * @time on 2019/2/23 17:29
 */
public class FindDetailsViewBean extends ResultMsgBean {

    private FindDetailsBean mFindDetailsBean;
    private FindCommentListBean mFindCommentListBean;

    public FindDetailsViewBean(FindDetailsBean findDetailsBean, FindCommentListBean findCommentListBean) {
        mFindDetailsBean = findDetailsBean;
        mFindCommentListBean = findCommentListBean;
    }

    public FindDetailsBean getFindDetailsBean() {
        return mFindDetailsBean;
    }

    public void setFindDetailsBean(FindDetailsBean findDetailsBean) {
        mFindDetailsBean = findDetailsBean;
    }

    public FindCommentListBean getFindCommentListBean() {
        return mFindCommentListBean;
    }

    public void setFindCommentListBean(FindCommentListBean findCommentListBean) {
        mFindCommentListBean = findCommentListBean;
    }
}
