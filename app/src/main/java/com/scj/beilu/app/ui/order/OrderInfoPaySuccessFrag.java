package com.scj.beilu.app.ui.order;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.gyf.immersionbar.ImmersionBar;
import com.mx.pro.lib.fragmentation.support.SupportFragment;
import com.scj.beilu.app.R;
import com.scj.beilu.app.ui.store.StoreInfoAct;
import com.scj.beilu.app.widget.AppToolbar;

/**
 * @author Mingxun
 * @time on 2019/4/2 23:02
 */
public class OrderInfoPaySuccessFrag extends SupportFragment implements View.OnClickListener {

    private AppToolbar mAppToolbar;
    private TextView mTvPayPrice;
    private TextView mTvAgainShopping, mTvOrder;

    private static final String PAY_PRICE = "pay_price";
    private static final String ORDER_NO = "order_no";
    private String mPayPrice;
    private String mOrderNo;

    public static OrderInfoPaySuccessFrag newInstance(String price, String orderNo) {

        Bundle args = new Bundle();
        args.putString(PAY_PRICE, price);
        args.putString(ORDER_NO, orderNo);
        OrderInfoPaySuccessFrag fragment = new OrderInfoPaySuccessFrag();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mPayPrice = arguments.getString(PAY_PRICE);
            mOrderNo = arguments.getString(ORDER_NO);
        }
    }

    @Nullable
    @Override
    public int getLayout() {
        return R.layout.frag_order_pay_success;
    }

    @Override
    public void initView() {
        super.initView();
        mAppToolbar = findViewById(R.id.toolbar);
        ImmersionBar.with(this)
                .titleBar(mAppToolbar)
                .statusBarDarkFont(true,0.2f)
                .init();
        mTvPayPrice = findViewById(R.id.tv_pay_price);
        mTvAgainShopping = findViewById(R.id.tv_again_shopping);
        mTvOrder = findViewById(R.id.tv_order);

        mTvAgainShopping.setOnClickListener(this);
        mTvOrder.setOnClickListener(this);
        mAppToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFragmentActivity.onBackPressed();
            }
        });

        mTvPayPrice.setText(String.format("实付 ¥%s", mPayPrice));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_again_shopping:
                Intent intent = new Intent(mFragmentActivity, StoreInfoAct.class);
                startActivity(intent);
                break;
            case R.id.tv_order:
                mFragmentActivity.setResult(Activity.RESULT_OK);
                startWithPop(OrderInfoStatusFrag.newInstance(mOrderNo));
                break;
        }
    }
}
