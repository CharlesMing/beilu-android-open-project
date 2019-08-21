package com.scj.beilu.app.mvp.home.bean;

import java.util.List;

/**
 * 左侧菜单Model
 * Created by lm on 2017-3-7.
 */
public class LeftViewItemBean {
    private String id;//编号Id
    private String name;//名称
    private List<RightViewItemBean> secondList;//本一级Model类名下的二级Model集合
    private boolean isChecked;//是否默认是选中状态，左侧列表数据源中应该只有一个默认选中状态是true

    public LeftViewItemBean(String id, String name, boolean isChecked, List<RightViewItemBean> secondList) {
        this.id = id;
        this.name = name;
        this.isChecked = isChecked;
        this.secondList = secondList;
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

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public List<RightViewItemBean> getSecondList() {
        return secondList;
    }

    public void setSecondList(List<RightViewItemBean> secondList) {
        this.secondList = secondList;
    }

}
