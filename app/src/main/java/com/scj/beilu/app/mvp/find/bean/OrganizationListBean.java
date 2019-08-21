package com.scj.beilu.app.mvp.find.bean;

import com.scj.beilu.app.mvp.common.bean.PageBean;
import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;

/**
 * @author Mingxun
 * @time on 2019/3/1 20:49
 */
public class OrganizationListBean extends ResultMsgBean {

    /**
     * page : {"currentPage":1,"list":[{"organId":1,"organBrief":"非常活跃","organName":"英雄联盟","organCity":null,"organHead":"add.jpg","organHeadZip":"adds.jpg","organCateId":0,"countUser":2,"countDynamic":2,"dynamicList":[]}],"startCount":0,"nextPage":1}
     */

    private PageBean<OrganizationInfoBean> page;

    public PageBean<OrganizationInfoBean> getPage() {
        return page;
    }

    public void setPage(PageBean<OrganizationInfoBean> page) {
        this.page = page;
    }
}
