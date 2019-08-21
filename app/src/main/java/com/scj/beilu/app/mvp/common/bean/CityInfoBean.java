package com.scj.beilu.app.mvp.common.bean;

import java.util.List;

/**
 * author:SunGuiLan
 * date:2019/3/1
 * descriptin:
 */
public class CityInfoBean {
    /**
     * name : 北京市
     * area : ["东城区","西城区","崇文区","宣武区","朝阳区","丰台区","石景山区","海淀区","门头沟区","房山区","通州区","顺义区","昌平区","大兴区","平谷区","怀柔区","密云县","延庆县"]
     */

    private String name;
    private List<String> area;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getArea() {
        return area;
    }

    public void setArea(List<String> area) {
        this.area = area;
    }
}
