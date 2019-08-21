package com.scj.beilu.app.ui.order;

import android.content.Intent;

import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseSupportAct;

/**
 * @author Mingxun
 * @time on 2019/4/2 22:58
 */
public class OrderInfoAct extends BaseSupportAct {

    public static final String PAY_PRICE = "pay_price";
    public static final String ORDER_NO = "order_no";
    //0: pay success 1:order status 2:order logistics
    public static final String LOAD_PAGE = "load_page";
    public static final int PAY_SUCCESS = 0;
    public static final int ORDER_STATUS = 1;
    public static final int ORDER_LOGISTICS = 2;

    @Override
    public int getLayout() {
        return R.layout.act_content;
    }

    @Override
    protected void initView() {
        super.initView();
        Intent intent = getIntent();
        if (intent == null) finish();
        String mPayPrice = intent.getStringExtra(PAY_PRICE);
        String mOrderNo = intent.getStringExtra(ORDER_NO);
        int loadPage = intent.getIntExtra(LOAD_PAGE, PAY_SUCCESS);
        switch (loadPage) {
            case PAY_SUCCESS:
                loadRootFragment(R.id.fl_content, OrderInfoPaySuccessFrag.newInstance(mPayPrice, mOrderNo));
                break;
            case ORDER_STATUS:
                loadRootFragment(R.id.fl_content, OrderInfoStatusFrag.newInstance(mOrderNo));
                break;
            case ORDER_LOGISTICS:
                loadRootFragment(R.id.fl_content, OrderInfoLogisticsFrag.newInstance(mOrderNo));
                break;
        }

    }
}
