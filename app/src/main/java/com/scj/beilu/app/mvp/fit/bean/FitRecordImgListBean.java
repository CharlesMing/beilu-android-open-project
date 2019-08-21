package com.scj.beilu.app.mvp.fit.bean;

import com.scj.beilu.app.mvp.common.bean.PageBean;
import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;

/**
 * @author Mingxun
 * @time on 2019/3/14 17:15
 */
public class FitRecordImgListBean extends ResultMsgBean {

    /**
     * page : {"currentPage":1,"list":[{"recordPicId":2,"userId":13,"picOrgAddr":"https://beilutest.oss-cn-hangzhou.aliyuncs.com/record/pic/ba703c5a-66f3-4658-b69b-b7391a49440220190251194742.jpg","picComAddr":"https://beilutest.oss-cn-hangzhou.aliyuncs.com/record/pic/zipba703c5a-66f3-4658-b69b-b7391a49440220190251194742.jpg"},{"recordPicId":1,"userId":13,"picOrgAddr":"https://beilutest.oss-cn-hangzhou.aliyuncs.com/record/pic/a0629a0e-e38c-4395-a930-c3b89b55b69620190251194724.png","picComAddr":"https://beilutest.oss-cn-hangzhou.aliyuncs.com/record/pic/zipa0629a0e-e38c-4395-a930-c3b89b55b69620190251194724.png"}],"startCount":0,"nextPage":1}
     */

    private PageBean<FitRecordImgInfoBean> page;

    public PageBean<FitRecordImgInfoBean> getPage() {
        return page;
    }

    public void setPage(PageBean<FitRecordImgInfoBean> page) {
        this.page = page;
    }
}
