package com.scj.beilu.app.ui.mine;

import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseSupportAct;

/**
 * @author Mingxun
 * @time on 2019/4/16 22:11
 */
public class MineBindPhoneAct extends BaseSupportAct {
    @Override
    public int getLayout() {
        return R.layout.act_content;
    }

    @Override
    protected void initView() {
        super.initView();
        loadRootFragment(R.id.fl_content, new MinBindPhoneFrag());
    }
}
