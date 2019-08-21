package com.scj.beilu.app.ui.act;

import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseSupportAct;
import com.scj.beilu.app.ui.mine.MineAccountSettingFrag;

/**
 * @author Mingxun
 * @time on 2019/1/12 17:21
 */
public class SetSystemAct extends BaseSupportAct {

    public static final int REQUEST = 0x001;
    public static final int LOGIN_OUT = 0x0101;

    @Override
    public int getLayout() {
        return R.layout.act_content;
    }

    @Override
    protected void initView() {
        super.initView();
        loadRootFragment(R.id.fl_content, new MineAccountSettingFrag());
    }
}
