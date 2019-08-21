package com.scj.beilu.app.ui.find;

import android.content.Intent;

import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseSupportAct;
import com.scj.beilu.app.ui.find.FindDetailsFrag;

/**
 * @author Mingxun
 * @time on 2019/2/23 15:46
 */
public class FindDetailsAct extends BaseSupportAct {

    @Override
    public int getLayout() {
        return R.layout.act_content;
    }

    @Override
    protected void initView() {
        super.initView();
        Intent intent = getIntent();
        if (intent == null) return;
        int findId = intent.getIntExtra(FindDetailsFrag.FIND_ID, -1);
        loadRootFragment(R.id.fl_content, FindDetailsFrag.newInstance(findId));
    }

}
