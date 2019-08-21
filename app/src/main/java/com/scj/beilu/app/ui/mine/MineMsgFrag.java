package com.scj.beilu.app.ui.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.view.View;
import android.view.ViewStub;
import android.widget.TextView;

import com.mx.pro.lib.smartrefresh.layout.api.RefreshLayout;
import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseMvpFragment;
import com.scj.beilu.app.listener.OnItemClickListener;
import com.scj.beilu.app.mvp.mine.message.MsgManagerFragPre;
import com.scj.beilu.app.mvp.mine.message.bean.MsgCommentInfoBean;
import com.scj.beilu.app.mvp.mine.message.bean.MsgLikeInfoBean;
import com.scj.beilu.app.mvp.mine.message.bean.MsgReplyInfoBean;
import com.scj.beilu.app.ui.find.FindDetailsAct;
import com.scj.beilu.app.ui.find.FindDetailsFrag;
import com.scj.beilu.app.ui.mine.adapter.MsgCommentListAdapter;
import com.scj.beilu.app.ui.mine.adapter.MsgLikeListAdapter;
import com.scj.beilu.app.ui.mine.adapter.MsgReplyListAdapter;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/4/9 20:37
 */
public class MineMsgFrag extends BaseMvpFragment<MsgManagerFragPre.MsgManagerFragView, MsgManagerFragPre>
        implements MsgManagerFragPre.MsgManagerFragView, OnItemClickListener {

    private static final String INDEX = "index";
    private int mIndex;

    private ViewStub view_load_empty;
    private TextView tv_empty_hint;

    private MsgReplyListAdapter mMsgReplyListAdapter;
    private MsgCommentListAdapter mMsgCommentListAdapter;
    private MsgLikeListAdapter mMsgLikeListAdapter;

    public static MineMsgFrag newInstance(int index) {

        Bundle args = new Bundle();
        args.putInt(INDEX, index);
        MineMsgFrag fragment = new MineMsgFrag();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mIndex = arguments.getInt(INDEX);
        }
    }

    @Override
    public MsgManagerFragPre createPresenter() {
        return new MsgManagerFragPre(mFragmentActivity);
    }

    @Nullable
    @Override
    public int getLayout() {
        return R.layout.frag_message_list;
    }

    @Override
    public void initView() {
        super.initView();
        switch (mIndex) {
            case 0:
                mMsgReplyListAdapter = new MsgReplyListAdapter(this);
                mMsgReplyListAdapter.setOnItemClickListener(this);
                mRecyclerView.setAdapter(mMsgReplyListAdapter);
                break;
            case 1:
                mMsgLikeListAdapter = new MsgLikeListAdapter(this);
                mMsgLikeListAdapter.setOnItemClickListener(this);
                mRecyclerView.setAdapter(mMsgLikeListAdapter);
                break;
            case 2:
                mMsgCommentListAdapter = new MsgCommentListAdapter(this);
                mMsgCommentListAdapter.setOnItemClickListener(this);
                mRecyclerView.setAdapter(mMsgCommentListAdapter);
                break;
        }
        view_load_empty = findViewById(R.id.view_load_empty);


        mRecyclerView.addItemDecoration(new DividerItemDecoration(mFragmentActivity, DividerItemDecoration.VERTICAL));
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mRefreshLayout.autoRefresh();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        super.onRefresh(refreshLayout);
        getPresenter().getMsgList(mIndex, mCurrentPage);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        super.onLoadMore(refreshLayout);
        getPresenter().getMsgList(mIndex, mCurrentPage);
    }

    @Override
    public void onCheckLoadMore(List<?> list) {
        checkIsLoadMore(list);
    }

    @Override
    public void onReplyList(List<MsgReplyInfoBean> replyInfoBeanList) {
        loadEmpty(replyInfoBeanList);
        if (replyInfoBeanList.size() > 0) {
            mMsgReplyListAdapter.setMsgReplyInfoBeans(replyInfoBeanList);
        }
    }

    @Override
    public void onLikeList(List<MsgLikeInfoBean> likeInfoBeanList) {
        loadEmpty(likeInfoBeanList);
        if (likeInfoBeanList.size() > 0) {
            mMsgLikeListAdapter.setMsgLikeInfoBeans(likeInfoBeanList);
        }
    }

    @Override
    public void onCommentList(List<MsgCommentInfoBean> commentInfoBeanList) {
        loadEmpty(commentInfoBeanList);
        if (commentInfoBeanList.size() > 0) {
            mMsgCommentListAdapter.setCommentInfoBeans(commentInfoBeanList);
        }
    }

    private void loadEmpty(List<?> listSize) {
        boolean isLoad = listSize.size() == 0;
        if (isLoad) {
            if (tv_empty_hint == null) {
                View inflate = view_load_empty.inflate();
                tv_empty_hint = inflate.findViewById(R.id.tv_empty_hint);
                inflate.findViewById(R.id.ll_load_empty_view).setVisibility(View.VISIBLE);
                tv_empty_hint.setText("这里空空如也～");
            }
        }
        view_load_empty.setVisibility(isLoad ? View.VISIBLE : View.GONE);
        mRecyclerView.setVisibility(isLoad ? View.GONE : View.VISIBLE);
    }

    @Override
    public void onItemClick(int position, Object entity, View view) {
        int dynamicId = -1;
        if (entity instanceof MsgReplyInfoBean) {
            dynamicId = ((MsgReplyInfoBean) entity).getDynamicId();
        } else if (entity instanceof MsgLikeInfoBean) {
            dynamicId = ((MsgLikeInfoBean) entity).getDynamicId();
        } else if (entity instanceof MsgCommentInfoBean) {
            dynamicId = ((MsgCommentInfoBean) entity).getDynamicId();
        }
        Intent intent = new Intent(mFragmentActivity, FindDetailsAct.class);
        intent.putExtra(FindDetailsFrag.FIND_ID, dynamicId);
        startActivityForResult(intent, 0);
    }
}
