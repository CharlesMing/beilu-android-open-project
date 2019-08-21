package com.scj.beilu.app.ui.find;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseMvpActivity;
import com.scj.beilu.app.mvp.find.publishstatus.PublishWayPre;

/**
 * date:2019/1/31
 * descriptin:选择发布方式
 */
public class FindPublishWayAct extends BaseMvpActivity<PublishWayPre.PublishWayView, PublishWayPre>
        implements PublishWayPre.PublishWayView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initView() {
        super.initView();
        loadRootFragment(R.id.fl_content, new FindPublishTypeFrag(),false,true);

    }

    @Override
    public int getLayout() {
        return R.layout.act_content;
    }

    @NonNull
    @Override
    public PublishWayPre createPresenter() {
        return new PublishWayPre();
    }


}
