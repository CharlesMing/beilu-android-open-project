package com.scj.beilu.app.mvp.find.bean;

import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;
import com.scj.beilu.app.mvp.user.bean.RecommendUserInfoBean;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/2/22 19:53
 */
public class FindDetailsRecommendBean extends ResultMsgBean {

    private List<RecommendUserInfoBean> users;

    public List<RecommendUserInfoBean> getUsers() {
        return users;
    }

    public void setUsers(List<RecommendUserInfoBean> users) {
        this.users = users;
    }

}
