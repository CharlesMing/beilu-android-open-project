package com.scj.beilu.app.ui.store;

import android.content.Intent;

import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseSupportAct;

/**
 * @author Mingxun
 * @time on 2019/3/26 03:25
 */
public class StoreInfoAct extends BaseSupportAct {

    public static final String PRODUCT_ID = "product_id";

    @Override
    public int getLayout() {
        return R.layout.act_content;
    }

    @Override
    protected void initView() {
        super.initView();
        Intent intent = getIntent();
        if (intent == null) finish();
        int productId = intent.getIntExtra(PRODUCT_ID, -1);
        if (productId == -1) {
            loadRootFragment(R.id.fl_content, new StoreFrag());
        } else {
            loadRootFragment(R.id.fl_content, GoodsDetailFrag.newInstance(productId));
        }
    }
}
