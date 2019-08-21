package com.scj.beilu.app.ui.find;

import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseSupportAct;

/**
 * @author Mingxun
 * @time on 2019/4/10 19:07
 */
public class SearchFindAct extends BaseSupportAct {

    @Override
    public int getLayout() {
        return R.layout.act_content;
    }

    @Override
    public void initView() {
        super.initView();

        loadRootFragment(R.id.fl_content, new SearchFindFrag());
    }

}
