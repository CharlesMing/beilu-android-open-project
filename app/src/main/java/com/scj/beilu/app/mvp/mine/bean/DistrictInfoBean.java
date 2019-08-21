package com.scj.beilu.app.mvp.mine.bean;


import com.contrarywind.interfaces.IPickerViewData;
import com.scj.beilu.app.mvp.common.bean.CityInfoBean;

import java.util.List;

/**
 */
public class DistrictInfoBean implements IPickerViewData {
    private String name;
    private List<CityInfoBean> city;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CityInfoBean> getCity() {
        return city;
    }

    public void setCity(List<CityInfoBean> city) {
        this.city = city;
    }

    // 实现 IPickerViewData 接口，
    // 这个用来显示在PickerView上面的字符串，
    // PickerView会通过IPickerViewData获取getPickerViewText方法显示出来。
    @Override
    public String getPickerViewText() {
        return this.name;
    }

}
