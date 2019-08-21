package com.scj.beilu.app.mvp.find.bean;

/**
 * @author Mingxun
 * @time on 2019/4/24 20:35
 */
public class EventAttentionNotify {

    public int attentionUserId;
    public int actionAttentionIndex;
    //1 热门  2 关注 0 默认

    public EventAttentionNotify(int attentionUserId, int actionAttentionIndex) {
        this.attentionUserId = attentionUserId;
        this.actionAttentionIndex = actionAttentionIndex;
    }
}
