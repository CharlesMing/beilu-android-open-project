package com.scj.beilu.app.mvp.store.bean;

/**
 * @author Mingxun
 * @time on 2019/3/28 21:24
 */
public class GoodsSpecificationInfoBean {
    /**
     * formatId : 10
     * propProductId : 0
     * propId : 2
     * objValue : 黄色
     */

    private int formatId;
    private int propProductId;
    private int propId;
    private String objValue;
    private boolean select;

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public int getFormatId() {
        return formatId;
    }

    public void setFormatId(int formatId) {
        this.formatId = formatId;
    }

    public int getPropProductId() {
        return propProductId;
    }

    public void setPropProductId(int propProductId) {
        this.propProductId = propProductId;
    }

    public int getPropId() {
        return propId;
    }

    public void setPropId(int propId) {
        this.propId = propId;
    }

    public String getObjValue() {
        return objValue;
    }

    public void setObjValue(String objValue) {
        this.objValue = objValue;
    }
}
