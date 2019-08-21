package com.scj.beilu.app.ui.store.adapter;

import android.view.View;
import android.widget.TextView;

import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseViewHolder;
import com.scj.beilu.app.listener.OnGroupItemClickListener;
import com.scj.beilu.app.mvp.store.bean.GoodsSpecificationInfoBean;

/**
 * @author Mingxun
 * @time on 2019/3/28 22:02
 */
class SpecificationChildViewHolder extends BaseViewHolder {
    public TextView mTvSpecificationVal;
    private OnGroupItemClickListener<GoodsSpecificationInfoBean> mGroupItemClickListener;
    private int mGroupPosition;


    public SpecificationChildViewHolder(View itemView, int groupPosition,
                                        OnGroupItemClickListener<GoodsSpecificationInfoBean> groupItemClickListener) {
        super(itemView);
        mGroupItemClickListener = groupItemClickListener;
        mGroupPosition = groupPosition;
        mTvSpecificationVal = findViewById(R.id.tv_goods_specification_val);
    }

    public void onBind(final GoodsSpecificationInfoBean specificationInfo) {
        mTvSpecificationVal.setText(specificationInfo.getObjValue());
        mTvSpecificationVal.setSelected(specificationInfo.isSelect());
        mTvSpecificationVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mGroupItemClickListener != null) {
                    mGroupItemClickListener.onItemClick(mGroupPosition, getAdapterPosition(), specificationInfo, v);
                }
            }
        });
    }
}
