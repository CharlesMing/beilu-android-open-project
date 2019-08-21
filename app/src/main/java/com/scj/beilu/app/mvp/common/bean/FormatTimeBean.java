package com.scj.beilu.app.mvp.common.bean;

/**
 * @author Mingxun
 * @time on 2019/2/25 23:55
 */
public abstract class FormatTimeBean extends ResultMsgBean {
    public String mFormatDate;

    public abstract String getFormatDate();

    public void setFormatDate(String formatDate) {
        mFormatDate = formatDate;
    }

    public String getDate() {
        return mFormatDate;
    }
}
