package com.scj.beilu.app.ui.news;

import android.content.Intent;

import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseSupportAct;

/**
 * @author Mingxun
 * @time on 2019/5/13 12:45
 */
public class NewsAuthorInfoAct extends BaseSupportAct {
    public static final String AUTHOR_ID = "author_id";
    public static final int LOAD_ATTENTION_AUTHOR = -100;
    public static final int LOAD_RECOMMEND_AUTHOR = -99;

    @Override
    public int getLayout() {
        return R.layout.act_content;
    }

    @Override
    protected void initView() {
        super.initView();
        Intent intent = getIntent();
        if (intent == null) finish();
        int authorId = intent.getIntExtra(AUTHOR_ID, -1);
        if (authorId == LOAD_ATTENTION_AUTHOR || authorId == LOAD_RECOMMEND_AUTHOR) {
            loadRootFragment(R.id.fl_content, NewsMyAttentionAuthorListFrag.newInstance(authorId));
        } else {
            loadRootFragment(R.id.fl_content, NewsAuthorInfoFrag.newInstance(authorId));
        }
    }
}
