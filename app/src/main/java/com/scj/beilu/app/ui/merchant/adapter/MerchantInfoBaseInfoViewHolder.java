package com.scj.beilu.app.ui.merchant.adapter;

import android.view.View;
import android.widget.TextView;

import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseViewHolder;

/**
 * @author Mingxun
 * @time on 2019/4/15 19:20
 */
class MerchantInfoBaseInfoViewHolder extends BaseViewHolder {

    public TextView mTvName1, mTvName2, mTvName3, mTvName4;

    public MerchantInfoBaseInfoViewHolder(View itemView) {
        super(itemView);
        mTvName1 = findViewById(R.id.tv_val_1);
        mTvName2 = findViewById(R.id.tv_val_2);
        mTvName3 = findViewById(R.id.tv_val_3);
        mTvName4 = findViewById(R.id.tv_val_4);
    }
}
