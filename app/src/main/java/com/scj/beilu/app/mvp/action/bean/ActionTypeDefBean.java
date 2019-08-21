package com.scj.beilu.app.mvp.action.bean;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/3/18 17:52
 */
public class ActionTypeDefBean {

    public List<ActionSecondTypeBean> secondTypeList;
    public List<ActionThirdTypeInfoBean> thirdTypeList;

    public ActionTypeDefBean(List<ActionSecondTypeBean> secondTypeList, List<ActionThirdTypeInfoBean> thirdTypeList) {
        this.secondTypeList = secondTypeList;
        this.thirdTypeList = thirdTypeList;
    }
}
