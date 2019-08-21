package com.scj.beilu.app.mvp.merchant.bean;

import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;

/**
 * @author Mingxun
 * @time on 2019/4/15 21:27
 */
public class MerchantInfoCoachResultBean extends ResultMsgBean {
    private MerchantInfoCoachBean coachEntity;
    private int countAlbum;

    public int getCountAlbum() {
        return countAlbum;
    }

    public void setCountAlbum(int countAlbum) {
        this.countAlbum = countAlbum;
    }

    public MerchantInfoCoachBean getCoachEntity() {
        return coachEntity;
    }

    public void setCoachEntity(MerchantInfoCoachBean coachEntity) {
        this.coachEntity = coachEntity;
    }
}
