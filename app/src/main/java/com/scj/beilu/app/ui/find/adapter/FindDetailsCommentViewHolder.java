package com.scj.beilu.app.ui.find.adapter;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.scj.beilu.app.GlideRequest;
import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseViewHolder;
import com.scj.beilu.app.mvp.find.bean.FindCommentBean;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/2/23 17:50
 */
class FindDetailsCommentViewHolder extends BaseViewHolder {
    private RecyclerView mRvCommentList;
    private FindDetailsParentCommentAdapter mCommentAdapter;

    public FindDetailsCommentViewHolder(View itemView, GlideRequest<Drawable> glideRequest) {
        super(itemView);
        mRvCommentList = findViewById(R.id.rv_comment_list);
        mRvCommentList.addItemDecoration(new DividerItemDecoration(mRvCommentList.getContext(), DividerItemDecoration.VERTICAL));

        mCommentAdapter = new FindDetailsParentCommentAdapter(glideRequest);
        mRvCommentList.setAdapter(mCommentAdapter);

    }

    public void setBindData(List<FindCommentBean> commentList) {
        mCommentAdapter.setFindCommentBeans(commentList);
    }
}
