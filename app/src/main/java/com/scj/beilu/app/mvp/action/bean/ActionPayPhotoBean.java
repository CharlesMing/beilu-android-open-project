package com.scj.beilu.app.mvp.action.bean;

import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;

public class ActionPayPhotoBean extends ResultMsgBean {

    /**
     * qrcodeAddr : https://resource.cqsanchaji.com/action/post/f02a0e6c-0abf-417f-a446-a6afe0689ad9201906153215507.png
     * postAddr : https://resource.cqsanchaji.com/action/post/poster.png
     */

    private String qrcodeAddr;
    private String postAddr;

    public String getQrcodeAddr() {
        return qrcodeAddr;
    }

    public void setQrcodeAddr(String qrcodeAddr) {
        this.qrcodeAddr = qrcodeAddr;
    }

    public String getPostAddr() {
        return postAddr;
    }

    public void setPostAddr(String postAddr) {
        this.postAddr = postAddr;
    }
}
