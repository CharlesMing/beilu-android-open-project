package com.scj.beilu.app.ui.news;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.gyf.immersionbar.ImmersionBar;
import com.mx.pro.lib.smartrefresh.layout.api.RefreshLayout;
import com.scj.beilu.app.GlideApp;
import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseMvpFragment;
import com.scj.beilu.app.listener.OnItemClickListener;
import com.scj.beilu.app.mvp.news.NewsCommentPre;
import com.scj.beilu.app.mvp.news.bean.NewsCommentBean;
import com.scj.beilu.app.mvp.news.bean.NewsReplyCommentBean;
import com.scj.beilu.app.ui.news.adapter.NewsCommentListParentAdapter;
import com.scj.beilu.app.util.CheckUtils;
import com.scj.beilu.app.util.ToastUtils;

import java.util.List;


/**
 * @author Mingxun
 * @time on 2019/2/15 21:45
 */
public class NewsDetailsCommentListFrag extends BaseMvpFragment<NewsCommentPre.NewsCommentView, NewsCommentPre>
        implements NewsCommentPre.NewsCommentView, OnItemClickListener, View.OnClickListener {


    private RecyclerView mRvCommentList;
    private NestedScrollView mNestedScrollView;
    private TextView mTvCommentCount;
    private EditText mEtCommentContent;
    private Button mBtnStartComment;

    private NewsCommentListParentAdapter mNewsCommentListParentAdapter;

    private static final String ARG_NEWS_ID = "news_id";
    private static final String ARG_COMMENT_COUNT = "count";

    private int mNewsId;
    private int mCount;
    private NewsCommentBean mReplyToUser;
    private NewsReplyCommentBean mReplyChild;
    private int mPosition = -1;


    public static NewsDetailsCommentListFrag newInstance(int newsId, int commentCount) {

        Bundle args = new Bundle();
        args.putInt(ARG_NEWS_ID, newsId);
        args.putInt(ARG_COMMENT_COUNT, commentCount);
        NewsDetailsCommentListFrag fragment = new NewsDetailsCommentListFrag();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mNewsId = arguments.getInt(ARG_NEWS_ID);
            mCount = arguments.getInt(ARG_COMMENT_COUNT);
        }
    }

    @Override
    public NewsCommentPre createPresenter() {
        return new NewsCommentPre(mFragmentActivity);
    }

    @Nullable
    @Override
    public int getLayout() {
        return R.layout.frag_news_comment_list;
    }

    @Override
    public void initView() {
        super.initView();
        ImmersionBar.with(this)
                .titleBar(R.id.refresh_layout)
                .statusBarDarkFont(true,0.2f)
                .keyboardEnable(true)
                .init();
        mRvCommentList = findViewById(R.id.rv_news_comment_list);
        mNestedScrollView = findViewById(R.id.nested_scroll_view);
        mTvCommentCount = findViewById(R.id.tv_comment_count);
        mEtCommentContent = findViewById(R.id.et_comment_content);
        mBtnStartComment = findViewById(R.id.btn_send_comment);

        mBtnStartComment.setOnClickListener(this);
        mTvCommentCount.setText(String.valueOf(mCount));
        mRefreshLayout.autoRefresh();

        mNewsCommentListParentAdapter = new NewsCommentListParentAdapter(GlideApp.with(this), mFragmentActivity);
        mNewsCommentListParentAdapter.setItemClickListener(this);
        mRvCommentList.setAdapter(mNewsCommentListParentAdapter);

        mRvCommentList.addItemDecoration(new DividerItemDecoration(mFragmentActivity, DividerItemDecoration.VERTICAL));
        mEtCommentContent.setHint("说点什么呗~");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mNestedScrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    if (mReplyToUser != null) {
                        mReplyToUser = null;
                        mReplyChild = null;
                        mEtCommentContent.setHint("说点什么呗~");
                    }
                }
            });
        }

    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        super.onRefresh(refreshLayout);
        getPresenter().getCommentList(mNewsId, mCurrentPage);

    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        super.onLoadMore(refreshLayout);
        getPresenter().getCommentList(mNewsId, mCurrentPage);
    }


    @Override
    public void onCheckLoadMore(List<?> list) {
        checkIsLoadMore(list);
    }

    @Override
    public void onCommentList(List<NewsCommentBean> commentList) {
        mNewsCommentListParentAdapter.setCommentParentList(commentList);
        if (mCurrentPage == 0) {//用户在发布评论成功后，自动滑动到顶部
            mNestedScrollView.fling(0);
            mNestedScrollView.smoothScrollTo(0, 0);
        }
    }

    @Override
    public void onReplyResult(String result) {
        if (mPosition > 0) {//回复评论成功后，通知Item更新数据，先设为本地
            String content = mEtCommentContent.getText().toString();
            getPresenter().notifyReplyContentChanged(mPosition, mReplyToUser, mReplyChild, content);
        }
        ToastUtils.showToast(mFragmentActivity, result);
        mReplyToUser = null;
        mReplyChild = null;
        restEd();

    }


    @Override
    public void onCommentResult(String result) {
        ToastUtils.showToast(mFragmentActivity, result);
        restEd();
        mRefreshLayout.autoRefresh();
    }

    @Override
    public void notifyItemChange(int position) {
        mNewsCommentListParentAdapter.notifyItemChanged(position);
        mPosition = -1;
    }

    private void restEd() {
        mEtCommentContent.setText("");
        mEtCommentContent.setHint("说点什么呗~");
    }

    @Override
    public void onItemClick(int position, Object entity, View view) {
        mPosition = position;
        switch (view.getId()) {
            case R.id.tv_news_comment_content://点击一级评论内容
                mReplyToUser = (NewsCommentBean) entity;
                mEtCommentContent.setHint(String.format(mFragmentActivity.getResources().getString(R.string.txt_reply_touser), mReplyToUser.getUserName()));
                showSoftInput(mEtCommentContent);
                break;
            case R.id.tv_comment_reply://点击二级评论内容
                mReplyChild = (NewsReplyCommentBean) entity;
                mEtCommentContent.setHint(String.format(mFragmentActivity.getResources().getString(R.string.txt_reply_touser), mReplyChild.getToUserName()));
                showSoftInput(mEtCommentContent);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send_comment://回复评论
                try {
                    String content = mEtCommentContent.getText().toString();
                    CheckUtils.checkStringIsNull(content, "请输入回复内容");

                    if (mReplyToUser != null) {//点击一级评论内容
                        getPresenter().replyCommentContent(mReplyToUser.getComId(), mReplyToUser.getUserId(), content);
                    } else if (mReplyChild != null) {//点击二级评论内容
                        getPresenter().replyCommentContent(mReplyChild.getNewsComId(), mReplyChild.getFromUserId(), content);
                    } else {//直接评论
                        getPresenter().crateComment(mNewsId, content);
                    }
                } catch (RuntimeException e) {
                    ToastUtils.showToast(mFragmentActivity, e.getMessage());
                }
                break;
        }
    }
}
