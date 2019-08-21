package com.scj.beilu.app.mvp.action.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Mingxun
 * @time on 2019/3/18 15:52
 */
public class ActionInfoBean implements Parcelable {
    /**
     * actionId : 1
     * actionName : 站姿交替肘碰膝
     * actionPic : https://resource.cqsanchaji.com/action/head/34d3e6ed-9177-4946-a9ec-6b80a78bce8b201905150102902.jpg
     * actionVideoAddr : null
     * actionContent : null
     * actionCateId : 1
     * actionDesId : 3
     * actionDesPartId : 9
     * actionPrice : 0
     * isLock : 1
     */

    private int actionId;
    private String actionName;
    private String actionPic;
    private String actionContent;
    private int actionCateId;
    private int actionDesId;
    private int actionDesPartId;
    private int isIntru;
    private String actionVideoAddr;
    private double actionPrice;
    private int isLock;

    protected ActionInfoBean(Parcel in) {
        actionId = in.readInt();
        actionName = in.readString();
        actionPic = in.readString();
        actionContent = in.readString();
        actionCateId = in.readInt();
        actionDesId = in.readInt();
        actionDesPartId = in.readInt();
        isIntru = in.readInt();
        actionVideoAddr = in.readString();
        actionPrice = in.readDouble();
        isLock = in.readInt();
    }

    public static final Creator<ActionInfoBean> CREATOR = new Creator<ActionInfoBean>() {
        @Override
        public ActionInfoBean createFromParcel(Parcel in) {
            return new ActionInfoBean(in);
        }

        @Override
        public ActionInfoBean[] newArray(int size) {
            return new ActionInfoBean[size];
        }
    };

    public int getActionId() {
        return actionId;
    }

    public void setActionId(int actionId) {
        this.actionId = actionId;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getActionPic() {
        return actionPic;
    }

    public void setActionPic(String actionPic) {
        this.actionPic = actionPic;
    }

    public String getActionVideoAddr() {
        return actionVideoAddr;
    }

    public void setActionVideoAddr(String actionVideoAddr) {
        this.actionVideoAddr = actionVideoAddr;
    }

    public String getActionContent() {
        return actionContent;
    }

    public void setActionContent(String actionContent) {
        this.actionContent = actionContent;
    }

    public int getActionCateId() {
        return actionCateId;
    }

    public void setActionCateId(int actionCateId) {
        this.actionCateId = actionCateId;
    }

    public int getActionDesId() {
        return actionDesId;
    }

    public void setActionDesId(int actionDesId) {
        this.actionDesId = actionDesId;
    }

    public int getActionDesPartId() {
        return actionDesPartId;
    }

    public void setActionDesPartId(int actionDesPartId) {
        this.actionDesPartId = actionDesPartId;
    }

    public double getActionPrice() {
        return actionPrice;
    }

    public void setActionPrice(int actionPrice) {
        this.actionPrice = actionPrice;
    }

    public int getIsLock() {
        return isLock;
    }

    public void setIsLock(int isLock) {
        this.isLock = isLock;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(actionId);
        dest.writeString(actionName);
        dest.writeString(actionPic);
        dest.writeString(actionContent);
        dest.writeInt(actionCateId);
        dest.writeInt(actionDesId);
        dest.writeInt(actionDesPartId);
        dest.writeInt(isIntru);
        dest.writeString(actionVideoAddr);
        dest.writeDouble(actionPrice);
        dest.writeInt(isLock);
    }
}
