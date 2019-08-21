package com.scj.beilu.app.ui.action;

import android.content.Intent;

import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseSupportAct;
import com.scj.beilu.app.mvp.action.bean.ActionInfoBean;

import java.util.ArrayList;

/**
 * @author Mingxun
 * @time on 2019/3/18 21:10
 */
public class ActionInfoDetailsAct extends BaseSupportAct {
    public static final String EXTRA_ACTION_LIST = "action_list";
    public static final String EXTRA_ACTION_POSITION = "action_position";
    public static final String EXTRA_ACTION_PRICE = "action_price";

    private ActionDetailsFrag mActionDetailsFrag;
    private ActionPayDescriptionFrag mActionPayDescriptionFrag;

    @Override
    public int getLayout() {
        return R.layout.act_content;
    }

    @Override
    protected void initView() {
        super.initView();

    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        if (intent == null) return;
        ArrayList<ActionInfoBean> actionInfoBeans = intent.getParcelableArrayListExtra(EXTRA_ACTION_LIST);
        if (actionInfoBeans != null) {
            mActionDetailsFrag = findFragment(ActionDetailsFrag.class);
            if (mActionDetailsFrag == null) {
                int position = intent.getIntExtra(EXTRA_ACTION_POSITION, 0);
                mActionDetailsFrag = ActionDetailsFrag.newInstance(actionInfoBeans, position);
                loadRootFragment(R.id.fl_content, mActionDetailsFrag);
            }
        } else {
            double price = intent.getDoubleExtra(EXTRA_ACTION_PRICE, -1);
            mActionPayDescriptionFrag = findFragment(ActionPayDescriptionFrag.class);
            if (mActionPayDescriptionFrag == null) {
                mActionPayDescriptionFrag = ActionPayDescriptionFrag.newInstance(price);
                loadRootFragment(R.id.fl_content, mActionPayDescriptionFrag);
            }
        }
    }
}
