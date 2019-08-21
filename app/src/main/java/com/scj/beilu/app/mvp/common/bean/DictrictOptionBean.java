package com.scj.beilu.app.mvp.common.bean;

import com.scj.beilu.app.mvp.mine.bean.DistrictInfoBean;

import java.util.ArrayList;
import java.util.List;

/**
 * author:SunGuiLan
 * date:2019/3/1
 * descriptin:
 */
public class DictrictOptionBean {
    private List<DistrictInfoBean> options1Items;
    private ArrayList<ArrayList<String>> options2Items;

    public List<DistrictInfoBean> getOptions1Items() {
        return options1Items;
    }

    public void setOptions1Items(List<DistrictInfoBean> options1Items) {
        this.options1Items = options1Items;
    }

    public ArrayList<ArrayList<String>> getOptions2Items() {
        return options2Items;
    }

    public void setOptions2Items(ArrayList<ArrayList<String>> options2Items) {
        this.options2Items = options2Items;
    }
}
