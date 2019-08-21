package com.scj.beilu.app.mvp.order.bean;

/**
 * @author Mingxun
 * @time on 2019/4/4 16:26
 */
public class OrderLogisticsInfoBean {
    /**
     * datetime : 2019-03-25 17:45:11
     * remark : 【杭州市】【杭州东新路】（0571-86013750）的科凌集团驻点业务员[18042481803]（18042481803）已揽收
     * zone :
     */

    private String datetime;
    private String remark;
    private String zone;

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }
}
