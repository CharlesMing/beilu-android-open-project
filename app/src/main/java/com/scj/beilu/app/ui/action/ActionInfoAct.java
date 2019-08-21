package com.scj.beilu.app.ui.action;

import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseSupportAct;

/**
 * @author Mingxun
 * @time on 2019/3/16 16:24
 * 动作库
 */
public class ActionInfoAct extends BaseSupportAct {
    @Override
    public int getLayout() {
        return R.layout.act_content;
    }

    @Override
    protected void initView() {
        super.initView();
        loadRootFragment(R.id.fl_content, new ActionNavListFrag());
    }
}
