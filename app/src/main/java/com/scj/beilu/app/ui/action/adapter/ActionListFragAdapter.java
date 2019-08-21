package com.scj.beilu.app.ui.action.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.scj.beilu.app.mvp.action.bean.ActionFirstTypeBean;
import com.scj.beilu.app.ui.action.ActionListFrag;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/3/16 17:44
 */
public class ActionListFragAdapter extends FragmentPagerAdapter {
    private List<ActionFirstTypeBean> mFirstTypeList;
    private ActionListFrag.onErrorListener mOnErrorListener;

    public ActionListFragAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setFirstTypeList(List<ActionFirstTypeBean> firstTypeList) {
        mFirstTypeList = firstTypeList;
        notifyDataSetChanged();
    }

    public void setOnErrorListener(ActionListFrag.onErrorListener onErrorListener) {
        mOnErrorListener = onErrorListener;
    }

    @Override
    public Fragment getItem(int position) {
        int actionDesId = mFirstTypeList.get(position).getActionDesId();
        ActionListFrag actionListFrag = ActionListFrag.newInstance(position,actionDesId);
        actionListFrag.setOnErrorListener(mOnErrorListener);
        return actionListFrag;
    }

    @Override
    public int getCount() {
        return mFirstTypeList == null ? 0 : mFirstTypeList.size();
    }
}
