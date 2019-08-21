package com.scj.beilu.app.ui.action;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseMvpFragment;
import com.scj.beilu.app.mvp.action.ActionPayDescPre;

import java.math.BigDecimal;

public class ActionPayDescriptionFrag extends BaseMvpFragment<ActionPayDescPre.ActionPayDescView, ActionPayDescPre>
        implements ActionPayDescPre.ActionPayDescView {
    private ImageView iv_action_lock_photo, iv_qr_code;
    private TextView tv_action_price_pay;

    private static final String TOTAL_PRICE = "price";

    private double mPrice;

    public static ActionPayDescriptionFrag newInstance(double price) {

        Bundle args = new Bundle();
        args.putDouble(TOTAL_PRICE, price);
        ActionPayDescriptionFrag fragment = new ActionPayDescriptionFrag();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mPrice = arguments.getDouble(TOTAL_PRICE);
        }
    }

    @Override
    public ActionPayDescPre createPresenter() {
        return new ActionPayDescPre(mFragmentActivity);
    }

    @Nullable
    @Override
    public int getLayout() {
        return R.layout.frag_action_pay_description;
    }

    @Override
    public void initView() {
        super.initView();
        tv_action_price_pay = findViewById(R.id.tv_action_price_pay);
        if (isAdded()) {
            BigDecimal b = BigDecimal.valueOf(mPrice);
            b.setScale(2, BigDecimal.ROUND_HALF_UP);
            String price = getResources().getString(R.string.action_pay_price);
            price = String.format(price, b);
            tv_action_price_pay.setText(price);
        }
    }
}
