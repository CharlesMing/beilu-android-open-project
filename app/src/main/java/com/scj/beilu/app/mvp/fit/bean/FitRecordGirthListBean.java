package com.scj.beilu.app.mvp.fit.bean;

import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/3/13 17:24
 */
public class FitRecordGirthListBean extends ResultMsgBean {

    private List<FitRecordGirthInfoBean> recordCommons;

    public List<FitRecordGirthInfoBean> getRecordCommons() {
        return recordCommons;
    }

    public void setRecordCommons(List<FitRecordGirthInfoBean> recordCommons) {
        this.recordCommons = recordCommons;
    }


}
