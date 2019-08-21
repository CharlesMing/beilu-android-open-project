package com.scj.beilu.app.mvp.wechat.bean;

/**
 * @author Mingxun
 * @time on 2019/2/22 13:19
 */
public class WeChatTokenBean {

    /**
     * access_token : 18_V7XtBX16GdOL3u1-51Vr8VqjZqRnuTAd8P7oDhq-0uCW4Z4GvVLHon_2V0Jx35ZgxUeq8myARyIOKu_kBGDG0OdME2fj8qAeJghMx3HLgZg
     * expires_in : 7200
     * refresh_token : 18_AvoIL1ckaHvWI77XiZlNDlamU7VVGQpasRGrHU3JsT96Nga3Wsyd_cGdYNfUrjHlgEk7EemVz5HVpICoTp6P7MCcszRbS5X38WE5o3E3eCU
     * openid : oDFC71b-odAXxQOnVey2mF9wbeAI
     * scope : snsapi_userinfo
     * unionid : osWSw0Tko6_7oXa-IX2A2xdpaOuM
     */

    private String access_token;
    private int expires_in;
    private String refresh_token;
    private String openid;
    private String scope;
    private String unionid;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }
}
