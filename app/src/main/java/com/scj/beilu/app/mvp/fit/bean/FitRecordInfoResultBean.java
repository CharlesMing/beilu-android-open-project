package com.scj.beilu.app.mvp.fit.bean;

import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;

/**
 * @author Mingxun
 * @time on 2019/3/14 11:45
 */
public class FitRecordInfoResultBean extends ResultMsgBean {

    /**
     * record : {"weight":80.9,"fatRate":0,"bone":0,"leftArm":0,"rightArm":0,"leftThigh":0,"rightThigh":0,"waistline":0,"hips":0,"bust":0}
     */

    private FitRecordInfoBean record;

    public FitRecordInfoBean getRecord() {
        return record;
    }

    public void setRecord(FitRecordInfoBean record) {
        this.record = record;
    }

}
