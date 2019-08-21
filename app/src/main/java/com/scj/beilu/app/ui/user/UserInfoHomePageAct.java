package com.scj.beilu.app.ui.user;

import android.content.Intent;

import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseSupportAct;

/**
 * @author Mingxun
 * @time on 2019/3/2 18:29
 */
public class UserInfoHomePageAct extends BaseSupportAct {

    public static final String EXTRA_USER_ID = "user_id";

    @Override
    public int getLayout() {
        return R.layout.act_content;
    }

    @Override
    protected void initView() {
        super.initView();
        Intent intent = getIntent();
        if (intent == null) return;
        int userID = intent.getIntExtra(EXTRA_USER_ID, -1);
        loadRootFragment(R.id.fl_content, UserHomePageFrag.newInstance(userID));


    }


}
