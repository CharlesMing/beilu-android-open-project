package com.scj.beilu.app.mvp.action.bean;

import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/3/16 15:50
 */
public class ActionInfoListBean extends ResultMsgBean {

    private List<ActionInfoBean> actions;
    private double actionTotalPrice;

    public double getActionTotalPrice() {
        return actionTotalPrice;
    }

    public void setActionTotalPrice(double actionTotalPrice) {
        this.actionTotalPrice = actionTotalPrice;
    }

    public List<ActionInfoBean> getActions() {
        return actions;
    }

    public void setActions(List<ActionInfoBean> actions) {
        this.actions = actions;
    }
}
