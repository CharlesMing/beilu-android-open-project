package com.scj.beilu.app.ui.act;

import android.app.Activity;
import android.content.Intent;

import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseSupportAct;
import com.scj.beilu.app.ui.mine.FeedBackFrag;

/**
 * @author Mingxun
 * @time on 2019/1/12 17:24
 * 意见反馈
 */
public class FeedbackAct extends BaseSupportAct {

    public static void startFeedbackAct(Activity activity) {
        Intent intent = new Intent(activity, FeedbackAct.class);
        activity.startActivity(intent);
    }

    @Override
    public int getLayout() {
        return R.layout.act_content;
    }

    @Override
    protected void initView() {
        super.initView();
        loadRootFragment(R.id.fl_content, new FeedBackFrag());
    }
}
