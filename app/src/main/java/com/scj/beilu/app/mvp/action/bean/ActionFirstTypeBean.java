package com.scj.beilu.app.mvp.action.bean;

import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/3/16 15:46
 * 动作库 一级分类
 */
public class ActionFirstTypeBean extends ResultMsgBean {

    /**
     * actionDesId : 1
     * actionDesName : 增肌
     * parts : [{"actionDesPartId":1,"actionDesId":1,"actionDesPartName":"胸部"},{"actionDesPartId":2,"actionDesId":1,"actionDesPartName":"肩部"},{"actionDesPartId":3,"actionDesId":1,"actionDesPartName":"背部"},{"actionDesPartId":4,"actionDesId":1,"actionDesPartName":"手臂"},{"actionDesPartId":5,"actionDesId":1,"actionDesPartName":"颈部"},{"actionDesPartId":6,"actionDesId":1,"actionDesPartName":"大腿"},{"actionDesPartId":7,"actionDesId":1,"actionDesPartName":"小腿"}]
     */
    private int actionDesId;
    private String actionDesName;
    private List<ActionSecondTypeBean> parts;

    public int getActionDesId() {
        return actionDesId;
    }

    public void setActionDesId(int actionDesId) {
        this.actionDesId = actionDesId;
    }

    public String getActionDesName() {
        return actionDesName;
    }

    public void setActionDesName(String actionDesName) {
        this.actionDesName = actionDesName;
    }

    public List<ActionSecondTypeBean> getParts() {
        return parts;
    }

    public void setParts(List<ActionSecondTypeBean> parts) {
        this.parts = parts;
    }
}
