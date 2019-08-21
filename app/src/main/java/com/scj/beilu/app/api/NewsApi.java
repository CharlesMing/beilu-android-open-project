package com.scj.beilu.app.api;

import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;
import com.scj.beilu.app.mvp.news.bean.CommentCountBean;
import com.scj.beilu.app.mvp.news.bean.NewsAuthorListBean;
import com.scj.beilu.app.mvp.news.bean.NewsAuthorInfoResultBean;
import com.scj.beilu.app.mvp.news.bean.NewsBannerListBean;
import com.scj.beilu.app.mvp.news.bean.NewsDetailsBean;
import com.scj.beilu.app.mvp.news.bean.NewsDetailsCommentBean;
import com.scj.beilu.app.mvp.news.bean.NewsListBean;
import com.scj.beilu.app.mvp.news.bean.NewsNavListBean;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;

/**
 * @author Mingxun
 * @time on 2019/1/28 19:16
 */
public interface NewsApi {
    /**
     * 获取分类
     */
    @POST("/apigateway/news/api/mobile/news/getAllNewsCate")
    Observable<NewsNavListBean> getNewsNav();

    /**
     * 根据分类Id 获取资讯列表
     */
    @FormUrlEncoded
    @POST("/apigateway/news/api/mobile/news/getMoreNews")
    Observable<NewsListBean> getNewsArrayByNavId(@HeaderMap Map<String, String> headerMap, @Field("cateId") int navId, @Field("currentPage") int currentPage);

    /**
     * 资讯Banner
     */
    @POST("/apigateway/news/api/mobile/news/getNewsBanner")
    Observable<NewsBannerListBean> getNewsBannerArray();

    /**
     * 资讯详情
     */
    @FormUrlEncoded
    @POST("/apigateway/news/api/mobile/news/getNewsDetailById")
    Observable<NewsDetailsBean> getNewsDetails(@HeaderMap Map<String, String> token, @Field("newsId") int newsId);

    /**
     * 收藏资讯
     */
    @FormUrlEncoded
    @POST("/apigateway/news/api/mobile/news/collectNews")
    Observable<ResultMsgBean> setNewsCollectAction(@HeaderMap Map<String, String> token, @Field("newsId") long newsId, @Field("collectStatus") int collectStatus);


    /**
     * 资讯评论列表
     */
    @FormUrlEncoded
    @POST("/apigateway/news/api/mobile/news/getNextPageCommentByNewsId")
    Observable<NewsDetailsCommentBean> getNewsCommentList(@Field("newsId") int newsId, @Field("currentPage") int currentPage);

    /**
     * 发布评论
     */
    @FormUrlEncoded
    @POST("/apigateway/news/api/mobile/news/commentNews")
    Observable<ResultMsgBean> createComment(@HeaderMap Map<String, String> token, @Field("newsId") int newsId, @Field("comContent") String commentContent);


    /**
     * 获取评论数量
     */
    @FormUrlEncoded
    @POST("/apigateway/news/api/mobile/news/getCommentCount")
    Observable<CommentCountBean> getCommentCount(@Field("newsId") int newsId);

    /**
     * 回复评论
     */
    @FormUrlEncoded
    @POST("/apigateway/news/api/mobile/news/commentReply")
    Observable<ResultMsgBean> createReplyComment(@HeaderMap Map<String, String> token, @Field("newsComId") int newsComId,
                                                 @Field("toUserId") int toUserId, @Field("newsReplyContent") String newsReplyContent);

    /**
     * 用户关注操作
     */
    @FormUrlEncoded
    @POST("/apigateway/news/api/mobile/news/attenNews")
    Observable<ResultMsgBean> attentionAction(@HeaderMap Map<String, String> token, @Field("newsUserId") int newsUserId);

    /**
     * 资讯搜索
     */
    @FormUrlEncoded
    @POST("/apigateway/news/api/mobile/news/getMoreSearchByKeyName")
    Observable<NewsListBean> searchNewsList(@Field("keyWord") String keyWord, @Field("currentPage") int currentPage);

    /**
     * 获取我的资讯收藏
     */
    @FormUrlEncoded
    @POST("/apigateway/news/api/mobile/news/getMyNewsCollection")
    Observable<NewsListBean> getMyCollectNews(@HeaderMap Map<String, String> token, @Field("currentPage") int currentPage);

    @FormUrlEncoded
    @POST("/apigateway/news/api/mobile/news/getMyFocusMedia")
    Observable<NewsAuthorListBean> getNewsAuthorAttentionList(@HeaderMap Map<String, String> token, @Field("currentPage") int currentPage);

    @FormUrlEncoded
    @POST("/apigateway/news/api/mobile/news/getRecomMedia")
    Observable<NewsAuthorListBean> getRecommendAuthorList(@HeaderMap Map<String, String> token, @Field("currentPage") int currentPage);

    /**
     * 获取作者信息
     *
     * @param token
     * @param authorId
     * @return
     */
    @FormUrlEncoded
    @POST("/apigateway/news/api/mobile/news/getMediaInfo")
    Observable<NewsAuthorInfoResultBean> getAuthorInfoById(@HeaderMap Map<String, String> token, @Field("mediaId") int authorId);

    /**
     * 获取作者的文章列表
     */
    @FormUrlEncoded
    @POST("/apigateway/news/api/mobile/news/getMediaNews")
    Observable<NewsListBean> getNewsListByAutorInfo(@Field("userId") int userId, @Field("currentPage") int currentPage);
}
