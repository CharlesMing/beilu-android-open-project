package com.scj.beilu.app.mvp.fit.bean;

import com.github.mikephil.charting.data.LineData;
import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/3/12 16:27
 */
public class FitRecordGirthInfoBean extends ResultMsgBean {

    /**
     * unit : kg
     * recordName : weight
     * recordData : []
     */
    private String unit;
    private String recordName;
    private String recordKey;
    private List<FitRecordGirthValBean> recordData;
    private LineData mLineData;

    public LineData getLineData() {
        return mLineData;
    }

    public void setLineData(LineData lineData) {
        mLineData = lineData;
    }

    public String getUnit() {
        return unit;
    }

    public String getRecordKey() {
        return recordKey;
    }

    public void setRecordKey(String recordKey) {
        this.recordKey = recordKey;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getRecordName() {
        return recordName;
    }

    public void setRecordName(String recordName) {
        this.recordName = recordName;
    }

    public List<FitRecordGirthValBean> getRecordData() {
        return recordData;
    }

    public void setRecordData(List<FitRecordGirthValBean> recordData) {
        this.recordData = recordData;
    }
}
