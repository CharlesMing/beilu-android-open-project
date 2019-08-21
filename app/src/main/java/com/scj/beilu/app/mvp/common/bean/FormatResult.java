package com.scj.beilu.app.mvp.common.bean;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/2/26 00:31
 */
public class FormatResult<T extends FormatTimeBean> extends ResultMsgBean {
    private List<T> list;

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
