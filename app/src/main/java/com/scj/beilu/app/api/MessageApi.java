package com.scj.beilu.app.api;

import com.scj.beilu.app.mvp.mine.message.bean.MsgCommentInfoResultBean;
import com.scj.beilu.app.mvp.mine.message.bean.MsgLikeInfoResultBean;
import com.scj.beilu.app.mvp.mine.message.bean.MsgReplyInfoResultBean;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;

/**
 * @author Mingxun
 * @time on 2019/4/9 20:11
 */
public interface MessageApi {
    /**
     * 查看回复消息
     */
    @FormUrlEncoded
    @POST("/apigateway/message/api/message/queryReplyMessageReply")
    Observable<MsgReplyInfoResultBean> getReplyMsgList(@HeaderMap Map<String, String> token, @Field("currentPage") int currentPage);

    /**
     * 喜歡消息
     */
    @FormUrlEncoded
    @POST("/apigateway/message/api/message/queryLikeMessageReply")
    Observable<MsgLikeInfoResultBean> getLikeMsgList(@HeaderMap Map<String, String> token, @Field("currentPage") int currentPage);

    /**
     * 評論消息
     */
    @FormUrlEncoded
    @POST("/apigateway/message/api/message/queryComMessageReply")
    Observable<MsgCommentInfoResultBean> getCommentMsgList(@HeaderMap Map<String, String> token, @Field("currentPage") int currentPage);

}
