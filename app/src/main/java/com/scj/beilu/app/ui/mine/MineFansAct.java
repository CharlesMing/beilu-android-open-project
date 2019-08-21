package com.scj.beilu.app.ui.mine;

import android.app.Activity;
import android.content.Intent;

import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseSupportAct;

public class MineFansAct extends BaseSupportAct {
    public static void startMeFansAct(Activity activity) {
        Intent intent = new Intent(activity, MineFansAct.class);
        activity.startActivity(intent);
    }

    @Override
    public int getLayout() {
        return R.layout.act_content;
    }

    @Override
    protected void initView() {
        super.initView();
        loadRootFragment(R.id.fl_content, new MineFansFrag());
    }

}
