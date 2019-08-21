package com.scj.beilu.app.mvp.find.bean;

import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/2/23 17:29
 */
public class FindDetailsViewListBean extends ResultMsgBean {

    private List<FindDetailsViewBean> mFindDetailsViewList;

    public List<FindDetailsViewBean> getFindDetailsViewList() {
        return mFindDetailsViewList;
    }

    public void setFindDetailsViewList(List<FindDetailsViewBean> findDetailsViewList) {
        mFindDetailsViewList = findDetailsViewList;
    }
}
