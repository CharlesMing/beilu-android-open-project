package com.scj.beilu.app.ui.news;

import android.app.Activity;
import android.content.Intent;

import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseSupportAct;
import com.scj.beilu.app.mvp.news.bean.NewsInfoBean;

/**
 * @author Mingxun
 * @time on 2019/1/29 16:12
 */
public class NewsDetailsAct extends BaseSupportAct {


    public static final String EXTRA_CONTENT = "CONTENT";
    private NewsInfoBean mNewsListBean;
    private NewsDetailFrag mNewsDetailFrag;

    public static void startNewsDetailsAct(Activity activity, NewsInfoBean newsContent) {
        Intent intent = new Intent(activity, NewsDetailsAct.class);
        intent.putExtra(EXTRA_CONTENT, newsContent);
        activity.startActivity(intent);
    }

    @Override
    public int getLayout() {
        return R.layout.act_content;
    }


    @Override
    protected void onStart() {
        super.onStart();

        if (mNewsDetailFrag == null) {
            Intent intent = getIntent();
            if (intent == null) return;
            mNewsListBean = intent.getParcelableExtra(EXTRA_CONTENT);
            mNewsDetailFrag = NewsDetailFrag.newInstance(mNewsListBean);
            loadRootFragment(R.id.fl_content, mNewsDetailFrag);
        } else {
            mNewsDetailFrag = findFragment(NewsDetailFrag.class);
        }
    }
}
