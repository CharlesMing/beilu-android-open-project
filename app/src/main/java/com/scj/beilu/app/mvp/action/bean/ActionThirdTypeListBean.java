package com.scj.beilu.app.mvp.action.bean;

import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/3/16 15:47
 */
public class ActionThirdTypeListBean extends ResultMsgBean {

    private List<ActionThirdTypeInfoBean> cate;

    public List<ActionThirdTypeInfoBean> getCate() {
        return cate;
    }

    public void setCate(List<ActionThirdTypeInfoBean> cate) {
        this.cate = cate;
    }


}
