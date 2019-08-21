package com.scj.beilu.app.mvp.action.bean;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/3/16 15:50
 */
public class ActionSortInfoBean {
    /**
     * actionName : 有器械
     * actions : [{"actionId":27,"actionName":"测试","actionPic":"https://beilutest.oss-cn-hangzhou.aliyuncs.com/action/head/7dc18295-f61a-4693-b905-8f9e1ddee0df20190375175606.png","actionContent":null,"actionCateId":1,"actionDesId":1,"actionDesPartId":1,"isIntru":0}]
     */

    private String actionName;
    private List<ActionInfoBean> actions;

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public List<ActionInfoBean> getActions() {
        return actions;
    }

    public void setActions(List<ActionInfoBean> actions) {
        this.actions = actions;
    }
}
