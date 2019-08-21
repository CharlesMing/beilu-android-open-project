package com.scj.beilu.app.ui.home;

import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseSupportAct;

/**
 * @author Mingxun
 * @time on 2019/4/12 02:07
 */
public class HomeSearchAct extends BaseSupportAct {
    @Override
    public int getLayout() {
        return R.layout.act_content;
    }

    @Override
    protected void initView() {
        super.initView();
        loadRootFragment(R.id.fl_content, new HomeSearchParentFrag());
    }
}
