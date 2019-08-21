package com.scj.beilu.app.mvp.action.bean;

/**
 * @author Mingxun
 * @time on 2019/3/16 15:46
 * 动作库二级分类
 */
public class ActionSecondTypeBean {
    /**
     * actionDesPartId : 1
     * actionDesId : 1
     * actionDesPartName : 胸部
     */

    private int actionDesPartId;
    private int actionDesId;
    private String actionDesPartName;
    private boolean select;

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public int getActionDesPartId() {
        return actionDesPartId;
    }

    public void setActionDesPartId(int actionDesPartId) {
        this.actionDesPartId = actionDesPartId;
    }

    public int getActionDesId() {
        return actionDesId;
    }

    public void setActionDesId(int actionDesId) {
        this.actionDesId = actionDesId;
    }

    public String getActionDesPartName() {
        return actionDesPartName;
    }

    public void setActionDesPartName(String actionDesPartName) {
        this.actionDesPartName = actionDesPartName;
    }
}
