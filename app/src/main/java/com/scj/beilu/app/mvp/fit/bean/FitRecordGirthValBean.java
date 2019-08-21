package com.scj.beilu.app.mvp.fit.bean;

/**
 * @author Mingxun
 * @time on 2019/3/13 20:16
 */
public class FitRecordGirthValBean {
    /**
     * recordId : 10
     * recordData : 50
     * recordDate : 2019-03-13 20:15:06
     */

    private int recordId;
    private float recordData;
    private String recordDate;

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public float getRecordData() {
        return recordData;
    }

    public void setRecordData(float recordData) {
        this.recordData = recordData;
    }

    public String getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(String recordDate) {
        this.recordDate = recordDate;
    }
}
