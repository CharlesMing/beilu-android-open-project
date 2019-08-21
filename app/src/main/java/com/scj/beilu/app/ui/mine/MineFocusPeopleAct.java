package com.scj.beilu.app.ui.mine;

import android.app.Activity;
import android.content.Intent;

import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseSupportAct;

public class MineFocusPeopleAct extends BaseSupportAct {
    public static void startMeAttentionPeopleAct(Activity activity) {
        Intent intent = new Intent(activity, MineFocusPeopleAct.class);
        activity.startActivity(intent);
    }

    @Override
    public int getLayout() {
        return R.layout.act_content;
    }

    protected void initView() {
        loadRootFragment(R.id.fl_content, new MineFocusPeopleFrag());
    }
}
