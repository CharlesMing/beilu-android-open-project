package com.scj.beilu.app.mvp.wechat.bean;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/2/22 14:52
 */
public class WeChatUserInfoBean {

    /**
     * openid : oDFC71b-odAXxQOnVey2mF9wbeAI
     * nickname : 笑笑
     * sex : 2
     * language : zh_CN
     * city :
     * province :
     * country : CN
     * headimgurl : http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIneSeCXgywdLQGzqib7hom3O8h7ng8preRtpBu0LYzl68p2BlMwcUU5y4cp8nK9Zo1BWqib8Bp1aRQ/132
     * privilege : []
     * unionid : osWSw0Tko6_7oXa-IX2A2xdpaOuM
     */

    private String openid;
    private String nickname;
    private int sex;
    private String language;
    private String city;
    private String province;
    private String country;
    private String headimgurl;
    private String unionid;
    private List<?> privilege;

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public List<?> getPrivilege() {
        return privilege;
    }

    public void setPrivilege(List<?> privilege) {
        this.privilege = privilege;
    }
}
