package com.scj.beilu.app.ui.act;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.mx.pro.lib.fragmentation.support.SupportActivity;
import com.scj.beilu.app.R;
import com.scj.beilu.app.ui.login.LoginAndRegisterFrag;

/**
 * @author Mingxun
 * @time on 2019/1/22 15:32
 */

public class LoginAndRegisterAct extends SupportActivity {

    public static final int LOGIN_REQUEST = 0x1001;
    public static final int LOGIN_RESULT_OK = 0x1000;

    public static void startLoginAndRegisterAct(Activity activity) {
        Intent intent = new Intent(activity, LoginAndRegisterAct.class);
        activity.startActivityForResult(intent, 200);
    }

    @Override
    public int getLayout() {
        return R.layout.act_content;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }


    protected void initView() {
        loadRootFragment(R.id.fl_content, new LoginAndRegisterFrag());
    }
}
