package com.scj.beilu.app.ui.act;

import android.app.Activity;
import android.content.Intent;

import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseSupportAct;
import com.scj.beilu.app.ui.mine.AboutUsFrag;

/**
 * author:SunGuiLan
 * date:2019/3/4
 * descriptin:
 */
public class AboutUsAct extends BaseSupportAct {
    public static void startAboutUsAct(Activity activity) {
        Intent intent = new Intent(activity, AboutUsAct.class);
        activity.startActivity(intent);
    }

    @Override
    public int getLayout() {
        return R.layout.act_about_us;
    }

    @Override
    protected void initView() {
        super.initView();
        loadRootFragment(R.id.fl_content, new AboutUsFrag());
    }
}
