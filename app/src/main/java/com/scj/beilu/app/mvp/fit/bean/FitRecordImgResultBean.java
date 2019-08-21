package com.scj.beilu.app.mvp.fit.bean;

import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;

/**
 * @author Mingxun
 * @time on 2019/3/14 17:06
 */
public class FitRecordImgResultBean extends ResultMsgBean {

    /**
     * orgFile : https://beilutest.oss-cn-hangzhou.aliyuncs.com/record/pic/80875a7d-f345-421e-b78c-fbf1462cf34320190373181629.jpg
     * comFile : https://beilutest.oss-cn-hangzhou.aliyuncs.com/record/pic/zip80875a7d-f345-421e-b78c-fbf1462cf34320190373181629.jpg
     */

    private String orgFile;
    private String comFile;
    private SendMessageToWX.Req mReq;

    public SendMessageToWX.Req getReq() {
        return mReq;
    }

    public void setReq(SendMessageToWX.Req req) {
        mReq = req;
    }

    public String getOrgFile() {
        return orgFile;
    }

    public void setOrgFile(String orgFile) {
        this.orgFile = orgFile;
    }

    public String getComFile() {
        return comFile;
    }

    public void setComFile(String comFile) {
        this.comFile = comFile;
    }
}
