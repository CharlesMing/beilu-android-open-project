package com.scj.beilu.app.mvp.action.bean;

import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/3/16 18:03
 */
public class ActionTopListTypeBean extends ResultMsgBean {

    private List<ActionFirstTypeBean> des;

    public List<ActionFirstTypeBean> getDes() {
        return des;
    }

    public void setDes(List<ActionFirstTypeBean> des) {
        this.des = des;
    }
}
