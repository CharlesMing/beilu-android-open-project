package com.scj.beilu.app.mvp.home.bean;

import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;
import com.scj.beilu.app.mvp.find.bean.FindInfoBean;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/3/12 17:51
 */
public class HomePageFindListBean extends ResultMsgBean {

    private List<FindInfoBean> dynamics;

    public List<FindInfoBean> getDynamics() {
        return dynamics;
    }

    public void setDynamics(List<FindInfoBean> dynamics) {
        this.dynamics = dynamics;
    }


}
