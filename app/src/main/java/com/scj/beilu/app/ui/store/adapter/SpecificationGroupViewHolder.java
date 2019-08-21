package com.scj.beilu.app.ui.store.adapter;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseViewHolder;
import com.scj.beilu.app.listener.OnGroupItemClickListener;
import com.scj.beilu.app.mvp.store.bean.GoodsSpecificationInfoBean;
import com.scj.beilu.app.mvp.store.bean.GoodsSpecificationListBean;
import com.scj.beilu.app.widget.FlowLayoutManager;

/**
 * @author Mingxun
 * @time on 2019/3/28 22:02
 */
class SpecificationGroupViewHolder extends BaseViewHolder {
    private TextView mTvTitle;
    private RecyclerView mRvSpecificationList;
    private GoodsSpecificationChildListAdapter mChildListAdapter;

    public SpecificationGroupViewHolder(View itemView, OnGroupItemClickListener<GoodsSpecificationInfoBean> groupItemClickListener) {
        super(itemView);
        mTvTitle = findViewById(R.id.tv_goods_specification_title);
        mRvSpecificationList = findViewById(R.id.rv_goods_specification);
        FlowLayoutManager layoutManager = new FlowLayoutManager();
        mRvSpecificationList.setLayoutManager(layoutManager);
        mRvSpecificationList.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(5, 5, 5, 5);
            }
        });

        mChildListAdapter = new GoodsSpecificationChildListAdapter(groupItemClickListener);
        mRvSpecificationList.setAdapter(mChildListAdapter);
    }

    public void onBind(GoodsSpecificationListBean listBean, int groupPosition) {
        mTvTitle.setText(listBean.getPropName());
        mChildListAdapter.setSpecificationInfoBeans(listBean.getProductFors(), groupPosition);
    }
}
