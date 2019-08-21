package com.scj.beilu.app.mvp.action.bean;

/**
 * @author Mingxun
 * @time on 2019/3/18 13:17
 */
public class ActionThirdTypeInfoBean {
    /**
     * actionCateId : 1
     * actionCateName : 热身运动
     */

    private int actionCateId;
    private String actionCateName;
    private String actionCatePic;
    private String actionCatePicZip;
    private boolean selected;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public int getActionCateId() {
        return actionCateId;
    }

    public void setActionCateId(int actionCateId) {
        this.actionCateId = actionCateId;
    }

    public String getActionCateName() {
        return actionCateName;
    }

    public void setActionCateName(String actionCateName) {
        this.actionCateName = actionCateName;
    }

    public String getActionCatePic() {
        return actionCatePic;
    }

    public void setActionCatePic(String actionCatePic) {
        this.actionCatePic = actionCatePic;
    }

    public String getActionCatePicZip() {
        return actionCatePicZip;
    }

    public void setActionCatePicZip(String actionCatePicZip) {
        this.actionCatePicZip = actionCatePicZip;
    }
}
