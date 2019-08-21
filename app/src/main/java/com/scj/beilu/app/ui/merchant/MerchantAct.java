package com.scj.beilu.app.ui.merchant;

import android.content.Intent;

import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseSupportAct;

/**
 * @author Mingxun
 * @time on 2019/4/13 18:23
 */
public class MerchantAct extends BaseSupportAct {

    public static final String CITY_NAME = "city_name";
    public static final String MERCHANT_ID = "merchant_id";

    @Override
    public int getLayout() {
        return R.layout.act_content;
    }

    @Override
    protected void initView() {
        super.initView();
        Intent intent = getIntent();
        if (intent == null) return;
        try {
            String cityName = intent.getStringExtra(CITY_NAME);
            int merchantId = intent.getIntExtra(MERCHANT_ID, 0);
            if (merchantId == 0) {
                loadRootFragment(R.id.fl_content, MerchantListFrag.newInstance(cityName));
            } else {
                loadRootFragment(R.id.fl_content, MerchantDetailFrag.newInstance(merchantId));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
