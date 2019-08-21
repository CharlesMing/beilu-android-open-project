package com.scj.beilu.app.mvp.common.bean;

/**
 * @author Mingxun
 * @time on 2019/2/13 12:28
 */
public class ResultMsgBean {

    /**
     * result : 验证码错误，请确认验证码
     * code : 1007
     * obj:{token:}
     */

    private String result;
    private int code;
    private String token;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "{" +
                "result='" + result + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
