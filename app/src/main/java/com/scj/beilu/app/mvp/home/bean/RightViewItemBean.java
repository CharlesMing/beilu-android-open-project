package com.scj.beilu.app.mvp.home.bean;

/**
 * 右侧菜单数据Model
 * Created by lm on 2017-3-7.
 */
public class RightViewItemBean {
    private String id;//编号Id
    private String name;//名称
    public RightViewItemBean(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
