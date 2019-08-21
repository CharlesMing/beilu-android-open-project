package com.scj.beilu.app.mvp.mine.bean;

import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;


/**
 * version : {"versionId":1,"versionNum":"1.0","versionTitle":"北鹿运动，旗下官方出品。","versionDec":"完整记录个人训练记录，丰富有趣的成长进阶体系，见证你的健身历程，让训练不再枯燥，激发自己的无穷动力。完整记录个人训练数据","versionUrl":"www.beilu.com","versionEmail":"beilu@999.com","versionProtocol":"123发酵的看法","versionApkAddr":"www.beilu.android.com","versionRestric":0,"versionDate":"2019-03-04 20:03:48"}
 */
public class AboutUsBean extends ResultMsgBean {
    private VersionBean version;

    public VersionBean getVersion() {
        return version;
    }

    public void setVersion(VersionBean version) {
        this.version = version;
    }

}
