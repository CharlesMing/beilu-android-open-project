package com.scj.beilu.app.ui.news;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseMvpFragment;
import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;
import com.scj.beilu.app.mvp.news.NewsDetailsFragPre;
import com.scj.beilu.app.mvp.news.bean.NewsInfoBean;
import com.scj.beilu.app.util.ToastUtils;
import com.scj.beilu.app.widget.NewsDetailsWebView;
import com.tencent.mm.opensdk.modelbase.BaseResp;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/2/15 21:22
 * 资讯详情
 */
public class NewsDetailFrag extends BaseMvpFragment<NewsDetailsFragPre.NewsDetailsView, NewsDetailsFragPre>
        implements NewsDetailsFragPre.NewsDetailsView, View.OnClickListener {

    private NewsDetailsWebView mWebView;
    private EditText mEtCommentContent;
    private Button mBtnStartComment;
    private TextView mTvCommentDetails;
    private View mViewStar;
    private ProgressBar progressBar;

    private static final String EXTRA_CONTENT = "CONTENT";
    private NewsInfoBean mNewsListBean;
    private int mCount;

    public static NewsDetailFrag newInstance(NewsInfoBean infoBean) {

        Bundle args = new Bundle();
        args.putParcelable(EXTRA_CONTENT, infoBean);
        NewsDetailFrag fragment = new NewsDetailFrag();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mNewsListBean = arguments.getParcelable(EXTRA_CONTENT);
        }
    }


    @Override

    public int getLayout() {
        return R.layout.frag_news_details;
    }

    @Override
    public void initView() {
        super.initView();
        mWebView = findViewById(R.id.web_news_details);
        mEtCommentContent = findViewById(R.id.et_comment_content);
        mTvCommentDetails = findViewById(R.id.tv_comment_detail);
        mBtnStartComment = findViewById(R.id.btn_send_comment);
        progressBar = findViewById(R.id.progressBar);
        mViewStar = findViewById(R.id.menu_star);

        mTvCommentDetails.setOnClickListener(this);
        mBtnStartComment.setOnClickListener(this);
        mEtCommentContent.addTextChangedListener(mTextWatcher);
        mWebView.setProgressBar(progressBar);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        getPresenter().getDetailsById(mNewsListBean.getNewsId());
    }

    @Override
    protected void onReLoad() {
        getPresenter().getDetailsById(mNewsListBean.getNewsId());
    }

    private final TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.length() > 0) {
                mTvCommentDetails.setVisibility(View.GONE);
                mBtnStartComment.setVisibility(View.VISIBLE);
            } else {
                mBtnStartComment.setVisibility(View.GONE);
                mTvCommentDetails.setVisibility(View.VISIBLE);
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_comment_detail:
                start(NewsDetailsCommentListFrag.newInstance(mNewsListBean.getNewsId(), mCount));
                break;
            case R.id.btn_send_comment:
                String content = mEtCommentContent.getText().toString();
                getPresenter().crateComment(mNewsListBean.getNewsId(), content);
                break;
        }
    }

    @NonNull
    @Override
    public NewsDetailsFragPre createPresenter() {
        return new NewsDetailsFragPre(mFragmentActivity);
    }

    @Override
    public void onNewsInfo(NewsInfoBean infoBean) {
        mViewStar.setSelected(infoBean.getIsCollect() == 1);
        mCount = infoBean.getCommentCount();
        mTvCommentDetails.setText(String.valueOf(infoBean.getCommentCount()));
        mNewsListBean = infoBean;

        mWebView.setIsFocus(infoBean.getIsFocus());
        mWebView.loadDataWithBaseURL(null, infoBean.getNewsContent(), "text/html", "utf-8", null);
        //设置本地调用对象及其接口
        mWebView.addJavascriptInterface(new JsInteraction(), "app");
    }

    @Override
    public void onFindDel() {
        //do not
    }

    @Override
    public void onShareCountResult() {
        //do not
    }

    @Override
    public void onFindDelResult(ResultMsgBean result) {
        //do not
    }

    @Override
    public void onCheckLoadMore(List<?> list) {
        //do not
    }

    public class JsInteraction {
        @JavascriptInterface
        public void changeAttention() {   //提供给js调用的方法
            mWebView.post(() ->
                    getPresenter().setAttentionAction(mNewsListBean.getUserId(), mNewsListBean.getIsFocus()));
        }
    }

    @Override
    public void onCommentResult(String result) {
        ToastUtils.showToast(mFragmentActivity, result);
        mEtCommentContent.setText("");
        hideSoftInput();

        getPresenter().getCommentCount(mNewsListBean.getNewsId());
    }

    @Override
    public void onCommentCountResult(int count) {
        mCount = count;
        mTvCommentDetails.setText(String.valueOf(count));
    }

    @Override
    public void onAttentionResult(String result) {

        ToastUtils.showToast(mFragmentActivity, result);
        mFragmentActivity.setResult(Activity.RESULT_OK);
        mWebView.post(() -> {

            if (mNewsListBean.getIsFocus() == 1) {
                mNewsListBean.setIsFocus(0);
            } else {
                mNewsListBean.setIsFocus(1);
            }

            if (mNewsListBean.getIsFocus() == 1) {
                mWebView.loadUrl("javascript:changeAttention(false)");
            } else {
                //已关注
                mWebView.loadUrl("javascript:changeAttention(true)");
            }
        });

    }

    @Override
    public void onCollectResult(String msg) {
        ToastUtils.showToast(mFragmentActivity, msg);
        mFragmentActivity.setResult(Activity.RESULT_OK);
        if (mViewStar.isSelected()) {
            mViewStar.setSelected(false);
        } else {
            mViewStar.setSelected(true);
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId() == R.id.menu_star) {
            getPresenter().setCollectAction(mNewsListBean.getNewsId(), mViewStar.isSelected() ? 0 : 1);
        } else if (item.getItemId() == R.id.menu_share) {
            if (!EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().register(this);
            }
            getPresenter().startShareNews(NewsDetailFrag.this, mNewsListBean);
        }
        return super.onMenuItemClick(item);
    }

    @Subscribe
    public void onShareResult(BaseResp resp) {
        ToastUtils.showToast(mFragmentActivity, "分享成功");
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
