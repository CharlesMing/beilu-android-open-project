package com.scj.beilu.app.ui.news;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mx.pro.lib.smartrefresh.layout.api.RefreshLayout;
import com.scj.beilu.app.GlideApp;
import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseMvpFragment;
import com.scj.beilu.app.listener.OnItemClickListener;
import com.scj.beilu.app.mvp.news.NewsAuthorInfoPre;
import com.scj.beilu.app.mvp.news.bean.NewsAuthorInfoBean;
import com.scj.beilu.app.mvp.news.bean.NewsInfoBean;
import com.scj.beilu.app.ui.news.adapter.NewsListAdapter;
import com.scj.beilu.app.widget.DividerItemDecorationToPadding;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/5/13 12:08
 */
public class NewsAuthorInfoFrag extends BaseMvpFragment<NewsAuthorInfoPre.NewsAuthorInfoView, NewsAuthorInfoPre>
        implements NewsAuthorInfoPre.NewsAuthorInfoView, OnItemClickListener, View.OnClickListener,
        NestedScrollView.OnScrollChangeListener {

    private NestedScrollView nested_scroll_view;
    private ImageView iv_news_author_avatar;
    private TextView tv_news_author_name, tv_news_author_fans_count, tv_news_author_attention_status, tv_news_author_desc;
    private TextView menu_tv_news_author_attention_status;
    private RecyclerView recy_author_news_list;
    private View view_divider;
    private NewsListAdapter mNewsListAdapter;
    private int mDividerLocation;

    private static final String AUTHOR_ID = "author_id";
    private int mAuthorId;

    public static NewsAuthorInfoFrag newInstance(int authorId) {

        Bundle args = new Bundle();
        args.putInt(AUTHOR_ID, authorId);
        NewsAuthorInfoFrag fragment = new NewsAuthorInfoFrag();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mAuthorId = arguments.getInt(AUTHOR_ID);
        }
    }

    @Override
    public NewsAuthorInfoPre createPresenter() {
        return new NewsAuthorInfoPre(mFragmentActivity);
    }

    @Nullable
    @Override
    public int getLayout() {
        return R.layout.frag_news_author_info;
    }

    @Override
    public void initView() {
        super.initView();
        nested_scroll_view = findViewById(R.id.nested_scroll_view);
        iv_news_author_avatar = findViewById(R.id.iv_news_author_avatar);
        tv_news_author_name = findViewById(R.id.tv_news_author_name);
        tv_news_author_fans_count = findViewById(R.id.tv_news_author_fans_count);
        tv_news_author_attention_status = findViewById(R.id.tv_news_author_attention_status);
        tv_news_author_desc = findViewById(R.id.tv_news_author_desc);
        recy_author_news_list = findViewById(R.id.recy_author_news_list);
        view_divider = findViewById(R.id.view_divider);
        mNewsListAdapter = new NewsListAdapter(mFragmentActivity, GlideApp.with(this));
        mNewsListAdapter.setOnItemClickListener(this);
        recy_author_news_list.setAdapter(mNewsListAdapter);

        tv_news_author_attention_status.setOnClickListener(this);
        recy_author_news_list.addItemDecoration(new DividerItemDecorationToPadding(mFragmentActivity, DividerItemDecoration.VERTICAL));

        getPresenter().getAuthorInfo(mAuthorId);
        nested_scroll_view.setOnScrollChangeListener(this);
    }

    @Override
    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        if (mDividerLocation <= scrollY) {
            if (mAppToolbar.mTitleTextView.getVisibility() == View.GONE) {
                mAppToolbar.mTitleTextView.setVisibility(View.VISIBLE);
                menu_tv_news_author_attention_status.setVisibility(View.VISIBLE);
                mAppToolbar.setToolbarTitle(tv_news_author_name.getText());
            }
        } else {
            if (mAppToolbar.mTitleTextView.getVisibility() == View.VISIBLE) {
                menu_tv_news_author_attention_status.setVisibility(View.GONE);
                mAppToolbar.mTitleTextView.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        super.onLoadMore(refreshLayout);
        getPresenter().getNewsList(mAuthorId, mCurrentPage);
    }

    @Override
    public void onAuthorInfo(NewsAuthorInfoBean authorInfoBean) {
        try {
            GlideApp.with(this)
                    .load(authorInfoBean.getUserOriginalHead())
                    .thumbnail(GlideApp.with(this).load(authorInfoBean.getUserCompressionHead()).optionalCircleCrop())
                    .optionalCircleCrop()
                    .into(iv_news_author_avatar);
            tv_news_author_name.setText(authorInfoBean.getUserNickName());
            int count = authorInfoBean.getFansCount();
            int fansOut = count / 10000;
            String countStr;
            if (fansOut > 0) {
                countStr = fansOut + "万";
            } else {
                countStr = count + "";
            }
            tv_news_author_fans_count.setText(countStr);
            tv_news_author_attention_status.setVisibility(View.VISIBLE);
            tv_news_author_attention_status.setText(authorInfoBean.getIsFocus() == 1 ? "已关注" : "+ 关注");
            tv_news_author_attention_status.setSelected(authorInfoBean.getIsFocus() == 1);
            String desc = "无";
            if (authorInfoBean.getUserBrief() != null && authorInfoBean.getUserBrief().length() > 0) {
                desc = authorInfoBean.getUserBrief();
            }
            tv_news_author_desc.setText(desc);

            view_divider.post(() -> {
                int[] location = new int[2];
                view_divider.getLocationInWindow(location);
                mDividerLocation = location[1] - mAppToolbar.getHeight();
            });

            mAppToolbar.inflateMenu(R.menu.menu_attention_status);
            if (menu_tv_news_author_attention_status == null) {
                menu_tv_news_author_attention_status = mAppToolbar.findViewById(R.id.menu_tv_news_author_attention_status);
            }

            menu_tv_news_author_attention_status.setVisibility(View.GONE);
            menu_tv_news_author_attention_status.setText(authorInfoBean.getIsFocus() == 1 ? "已关注" : "+ 关注");
            menu_tv_news_author_attention_status.setSelected(authorInfoBean.getIsFocus() == 1);
            menu_tv_news_author_attention_status.setOnClickListener(this);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    @Override
    public void onNewsList(List<NewsInfoBean> newsInfoList) {
        mNewsListAdapter.setNewsListEntityList(newsInfoList);
    }

    @Override
    public void onAttentionResult(String result) {
        boolean selected = tv_news_author_attention_status.isSelected();
        tv_news_author_attention_status.setSelected(!selected);
        tv_news_author_attention_status.setText(!selected ? "已关注" : "+ 关注");

        menu_tv_news_author_attention_status.setSelected(!selected);
        menu_tv_news_author_attention_status.setText(!selected ? "已关注" : "+ 关注");
        mFragmentActivity.setResult(Activity.RESULT_OK);
    }

    @Override
    public void onCheckLoadMore(List<?> list) {
        checkIsLoadMore(list);
    }

    @Override
    public void onItemClick(int position, Object entity, View view) {
        NewsInfoBean infoBean = (NewsInfoBean) entity;
        NewsDetailsAct.startNewsDetailsAct(mFragmentActivity, infoBean);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_news_author_attention_status:
            case R.id.menu_tv_news_author_attention_status:
                getPresenter().setAttentionAuthor(mAuthorId);
                break;

        }
    }

}
