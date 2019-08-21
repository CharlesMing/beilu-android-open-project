package com.scj.beilu.app.mvp.news.model;

import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;
import com.scj.beilu.app.mvp.common.user.IUserInfo;
import com.scj.beilu.app.mvp.news.bean.CommentCountBean;
import com.scj.beilu.app.mvp.news.bean.NewsAuthorListBean;
import com.scj.beilu.app.mvp.news.bean.NewsAuthorInfoResultBean;
import com.scj.beilu.app.mvp.news.bean.NewsBannerListBean;
import com.scj.beilu.app.mvp.news.bean.NewsDetailsBean;
import com.scj.beilu.app.mvp.news.bean.NewsDetailsCommentBean;
import com.scj.beilu.app.mvp.news.bean.NewsListBean;
import com.scj.beilu.app.mvp.news.bean.NewsNavListBean;

import io.reactivex.Observable;

/**
 * @author Mingxun
 * @time on 2019/2/11 20:03
 */
public interface INewsInfo extends IUserInfo {
    /**
     * 资讯导航分类
     */
    Observable<NewsNavListBean> getNewsNav();

    /**
     * 资讯列表
     */
    Observable<NewsListBean> setNewsArrayByNavId(int navId, int currentPage);

    /**
     * 资讯Banner
     */
    Observable<NewsBannerListBean> getNewsBannerArray();

    /**
     * 获取资讯详情
     */
    Observable<NewsDetailsBean> getNewsDetailsById(int newsId);

    /**
     * 添加/删除 收藏
     */
    Observable<ResultMsgBean> setNewsCollectAction(int newsId, int collectStatus);

    /**
     * 获取资讯信息流广告
     */
//    Observable<String >


    /**
     * 发布评论
     */
    Observable<ResultMsgBean> setParamsCreateComment(int newsId, String commentContent);


    /**
     * 获取资讯评论列表
     * 获取该资讯下一页评论
     */
    Observable<NewsDetailsCommentBean> setParamsNewsDetailsCommentByNewsId(int newsId, int currentPage);

    /**
     * 获取评论数量
     */
    Observable<CommentCountBean> getCommentCountByNewsId(int newsId);

    /**
     * 评论回复
     */
    Observable<ResultMsgBean> setReplyCommentContent(int newsComId, int toUserId, String content);

    /**
     * 用户关注操作
     */
    Observable<ResultMsgBean> setAttentionParams(int newsUserId, int isFocus);

    /**
     * 搜索资讯
     */
    Observable<NewsListBean> searchNewsParams(String keyWord, int currentPage);

    /**
     * 我關注的資訊
     */
    Observable<NewsListBean> getMyCollectNews(int currentPage);

    /**
     * 我關注的自媒体
     */
    Observable<NewsAuthorListBean> getNewsAuthorAttentionList(int currentPage);

    /**
     * 获取推荐的自媒体
     */
    Observable<NewsAuthorListBean> getRecommendAuthor(int currentPage);

    /**
     * 获取自媒体详情
     */
    Observable<NewsAuthorInfoResultBean> getAuthorInfoById(int authorId);

    /**
     * 获取作者文章列表
     */
    Observable<NewsListBean> getNewsListByAuthorInfo(int authorId, int currentPage);
}
