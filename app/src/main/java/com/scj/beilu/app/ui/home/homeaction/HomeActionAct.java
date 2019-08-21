package com.scj.beilu.app.ui.home.homeaction;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseMvpActivity;
import com.scj.beilu.app.mvp.home.HomeAtionPre;

/**
 * author: SunGuiLan
 * date:2019/2/19
 * descriptin:动作库
 */
public class HomeActionAct extends BaseMvpActivity<HomeAtionPre.HomeActionView, HomeAtionPre> implements HomeAtionPre.HomeActionView {

    public static void startHomeActionAct(Activity activity) {
        Intent intent = new Intent(activity, HomeActionAct.class);
        activity.startActivity(intent);
    }

    @Override
    public int getLayout() {
        return R.layout.act_home_action;
    }

    @NonNull
    @Override
    public HomeAtionPre createPresenter() {
        return new HomeAtionPre();
    }
}
