package com.scj.beilu.app.mvp.news.model;

import com.mx.pro.lib.mvp.exception.UserException;
import com.scj.beilu.app.api.NewsApi;
import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;
import com.scj.beilu.app.mvp.common.search.SearchImpl;
import com.scj.beilu.app.mvp.news.bean.CommentCountBean;
import com.scj.beilu.app.mvp.news.bean.NewsAuthorListBean;
import com.scj.beilu.app.mvp.news.bean.NewsAuthorInfoResultBean;
import com.scj.beilu.app.mvp.news.bean.NewsListBean;
import com.scj.beilu.app.mvp.news.bean.NewsBannerListBean;
import com.scj.beilu.app.mvp.news.bean.NewsDetailsBean;
import com.scj.beilu.app.mvp.news.bean.NewsDetailsCommentBean;
import com.scj.beilu.app.mvp.news.bean.NewsNavListBean;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/**
 * @author Mingxun
 * @time on 2019/2/11 20:04
 * ---------资讯-----------
 * 1.Banner
 * 2.news list
 * 3.comment list
 * 4.create attention
 * 5.create comment
 * 6.create reply comment
 * 7.search news
 * 8.add the search history
 * 9.get the comment count
 * 10.add the collect
 * See {@link INewsInfo}
 */
public class NewsInfoImpl extends SearchImpl implements INewsInfo {

    private NewsApi mNewsApi;

    public NewsInfoImpl(Builder builder) {
        super(builder);
        mNewsApi = create(NewsApi.class);
    }

    @Override
    public Observable<NewsNavListBean> getNewsNav() {
        return createObservable(mNewsApi.getNewsNav());
    }


    @Override
    public Observable<NewsListBean> setNewsArrayByNavId(final int navId, final int currentPage) {
        return getHeader()
                .flatMap((Function<Map<String, String>, ObservableSource<NewsListBean>>)
                        header -> createObservable(mNewsApi.getNewsArrayByNavId(header, navId, currentPage)));
    }

    @Override
    public Observable<NewsBannerListBean> getNewsBannerArray() {
        return createObservable(mNewsApi.getNewsBannerArray());
    }

    @Override
    public Observable<NewsDetailsBean> getNewsDetailsById(final int newsId) {
        return getHeader()
                .flatMap((Function<Map<String, String>, ObservableSource<NewsDetailsBean>>)
                        token -> createObservable(mNewsApi.getNewsDetails(token, newsId)));
    }

    @Override
    public Observable<ResultMsgBean> setNewsCollectAction(final int newsId, final int collectStatus) {
        return getHeader()
                .flatMap((Function<Map<String, String>, ObservableSource<ResultMsgBean>>) token -> {
                    if (token.size() == 0) {//start to Login View
                        throw new UserException();
                    } else {//start request
                        return createObservable(mNewsApi.setNewsCollectAction(token, newsId, collectStatus));
                    }
                });


    }

    @Override
    public Observable<ResultMsgBean> setParamsCreateComment(final int newsId, final String commentContent) {
        return getHeader()
                .flatMap((Function<Map<String, String>, ObservableSource<ResultMsgBean>>) token -> {
                    if (token.size() == 0) {
                        throw new UserException();
                    } else
                        return createObservable(mNewsApi.createComment(token, newsId, commentContent));
                });
    }

    @Override
    public Observable<NewsDetailsCommentBean> setParamsNewsDetailsCommentByNewsId(int newsId, int currentPage) {
        return createObservable(mNewsApi.getNewsCommentList(newsId, currentPage));
    }

    @Override
    public Observable<CommentCountBean> getCommentCountByNewsId(int newsId) {
        return createObservable(mNewsApi.getCommentCount(newsId));
    }

    @Override
    public Observable<ResultMsgBean> setReplyCommentContent(final int newsComId, final int toUserId, final String content) {
        return getHeader()
                .flatMap((Function<Map<String, String>, ObservableSource<ResultMsgBean>>) token -> {
                    if (token.size() == 0)
                        throw new UserException();
                    else {
                        return createObservable(mNewsApi.createReplyComment(token, newsComId, toUserId, content));
                    }
                });
    }

    @Override
    public Observable<ResultMsgBean> setAttentionParams(final int newsUserId, final int isFocus) {
        return getHeader()
                .flatMap((Function<Map<String, String>, ObservableSource<ResultMsgBean>>) token -> {
                    if (token.size() == 0) {
                        throw new UserException();
                    } else {
                        return createObservable(mNewsApi.attentionAction(token, newsUserId));
                    }
                });
    }

    @Override
    public Observable<NewsListBean> searchNewsParams(String keyWord, final int currentPage) {
        return createObservable(mNewsApi.searchNewsList(keyWord, currentPage));

    }

    @Override
    public Observable<NewsListBean> getMyCollectNews(final int currentPage) {
        return getHeader()
                .flatMap((Function<Map<String, String>, ObservableSource<NewsListBean>>) token -> {
                    if (token.size() == 0) {
                        throw new UserException();
                    } else {
                        return createObservable(mNewsApi.getMyCollectNews(token, currentPage));
                    }
                });
    }

    @Override
    public Observable<NewsAuthorListBean> getNewsAuthorAttentionList(int currentPage) {
        return getHeader()
                .flatMap((Function<Map<String, String>, ObservableSource<NewsAuthorListBean>>)
                        token -> createObservable(mNewsApi.getNewsAuthorAttentionList(token, currentPage)));
    }

    @Override
    public Observable<NewsAuthorListBean> getRecommendAuthor(int currentPage) {
        return getHeader()
                .flatMap((Function<Map<String, String>, ObservableSource<NewsAuthorListBean>>)
                        token -> createObservable(mNewsApi.getRecommendAuthorList(token, currentPage)));
    }

    @Override
    public Observable<NewsAuthorInfoResultBean> getAuthorInfoById(int authorId) {
        return getHeader()
                .flatMap((Function<Map<String, String>, ObservableSource<NewsAuthorInfoResultBean>>)
                        token -> createObservable(mNewsApi.getAuthorInfoById(token, authorId)));
    }

    @Override
    public Observable<NewsListBean> getNewsListByAuthorInfo(int authorId, int currentPage) {
        return createObservable(mNewsApi.getNewsListByAutorInfo(authorId, currentPage));
    }

}
