package com.scj.beilu.app.ui.act;

import android.app.Activity;
import android.content.Intent;

import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseSupportAct;
import com.scj.beilu.app.ui.news.SearchNewsFrag;

/**
 * @author Mingxun
 * @time on 2019/2/11 17:50
 */
public class SearchNewsAct extends BaseSupportAct {



    public static void startSearchNewsAct(Activity activity) {
        Intent intent = new Intent(activity, SearchNewsAct.class);
        activity.startActivity(intent);
    }

    @Override
    public int getLayout() {
        return R.layout.act_content;
    }

    @Override
    protected void initView() {
        super.initView();
        loadRootFragment(R.id.fl_content, SearchNewsFrag.newInstance());

    }



}
