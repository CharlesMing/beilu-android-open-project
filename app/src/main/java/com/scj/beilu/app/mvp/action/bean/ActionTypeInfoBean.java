package com.scj.beilu.app.mvp.action.bean;

import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;

/**
 * @author Mingxun
 * @time on 2019/3/18 15:22
 */
public class ActionTypeInfoBean extends ResultMsgBean {
    public ActionTopListTypeBean mTopListTypeList;
    public ActionThirdTypeListBean mActionThirdTypeList;

    public ActionTypeInfoBean(ActionTopListTypeBean topListTypeList, ActionThirdTypeListBean actionThirdTypeList) {
        mTopListTypeList = topListTypeList;
        mActionThirdTypeList = actionThirdTypeList;
    }

}
