package com.scj.beilu.app.ui.action;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.mx.pro.lib.fragmentation.support.SupportFragment;
import com.scj.beilu.app.R;
import com.scj.beilu.app.mvp.action.bean.ActionInfoBean;
import com.scj.beilu.app.ui.action.adapter.ActionDetailsContentPagerAdapter;

import java.util.ArrayList;

/**
 * @author Mingxun
 * @time on 2019/3/18 21:12
 */
public class ActionDetailsFrag extends SupportFragment implements ViewPager.OnPageChangeListener, View.OnClickListener {

    private ViewPager mViewPager;
    private TextView mTvPreviousPage, mTvNextPage;
    private TextView mTvCurrentPage, mTvTotalPage;

    private static final String EXTRA_ACTION_LIST = "action_list";
    private static final String EXTRA_ACTION_POSITION = "action_position";

    private ActionDetailsContentPagerAdapter mActionDetailsContentPagerAdapter;
    private ArrayList<ActionInfoBean> mActionInfoList;
    private int mPosition;
    private int mTotalSize;

    public static ActionDetailsFrag newInstance(ArrayList<ActionInfoBean> actionList, int positoin) {

        Bundle args = new Bundle();
        args.putParcelableArrayList(EXTRA_ACTION_LIST, actionList);
        args.putInt(EXTRA_ACTION_POSITION, positoin);
        ActionDetailsFrag fragment = new ActionDetailsFrag();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mActionInfoList = arguments.getParcelableArrayList(EXTRA_ACTION_LIST);
            mTotalSize = mActionInfoList.size();
            mPosition = arguments.getInt(EXTRA_ACTION_POSITION);
        }
    }

    @Nullable
    @Override
    public int getLayout() {
        return R.layout.frag_action_details_viewpager;
    }

    @Override
    public void initView() {
        super.initView();
        mViewPager = findViewById(R.id.action_view_pager);
        mTvPreviousPage = findViewById(R.id.tv_previous_page);
        mTvNextPage = findViewById(R.id.tv_next_page);
        mTvCurrentPage = findViewById(R.id.tv_current_page);
        mTvTotalPage = findViewById(R.id.tv_total_page);

        mActionDetailsContentPagerAdapter = new ActionDetailsContentPagerAdapter(getChildFragmentManager());
        mActionDetailsContentPagerAdapter.setActionSortInfoBeans(mActionInfoList);
        mViewPager.setAdapter(mActionDetailsContentPagerAdapter);

        mViewPager.addOnPageChangeListener(this);
        mViewPager.setCurrentItem(mPosition, false);

        setPage(mPosition);
        mTvPreviousPage.setOnClickListener(this);
        mTvNextPage.setOnClickListener(this);
        mViewPager.setOffscreenPageLimit(mTotalSize);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        setPage(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void setPage(int position) {
        int positionSize = position + 1;
        mTvCurrentPage.setText(String.valueOf(positionSize));
        mTvTotalPage.setText("/" + mTotalSize);

        if (mTotalSize == 1) {//有且只有1页
            mTvPreviousPage.setVisibility(View.INVISIBLE);
            mTvNextPage.setVisibility(View.INVISIBLE);
        } else if (mTotalSize == positionSize) {//最后一页 并且显示上一页
            mTvNextPage.setVisibility(View.INVISIBLE);
            mTvPreviousPage.setVisibility(View.VISIBLE);
            mTvPreviousPage.setText(mActionInfoList.get(position).getActionName());
        } else if (position == 0) {//第一页 显示下一页
            mTvPreviousPage.setVisibility(View.INVISIBLE);
            if (positionSize < mTotalSize) {//显示下一页
                mTvNextPage.setText(mActionInfoList.get(positionSize).getActionName());
            }
        } else if (position > 0) {//显示上一页 并显示下一页
            mTvPreviousPage.setVisibility(View.VISIBLE);
            mTvNextPage.setVisibility(View.VISIBLE);
            if (positionSize < mTotalSize) {//显示下一页
                mTvNextPage.setText(mActionInfoList.get(positionSize).getActionName());
            }
            mTvPreviousPage.setText(mActionInfoList.get(positionSize - 1).getActionName());
        }
    }

    @Override
    public void onClick(View v) {
        int position = mViewPager.getCurrentItem();
        switch (v.getId()) {
            case R.id.tv_previous_page:
                if (position != 0) position = position - 1;
                mViewPager.setCurrentItem(position, true);
                break;
            case R.id.tv_next_page:
                position += 1;
                if (mTotalSize > position) mViewPager.setCurrentItem(position, true);
                break;
        }
    }
}
