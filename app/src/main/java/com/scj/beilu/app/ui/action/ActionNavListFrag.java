package com.scj.beilu.app.ui.action;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gyf.immersionbar.ImmersionBar;
import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseMvpFragment;
import com.scj.beilu.app.mvp.action.ActionNavPre;
import com.scj.beilu.app.mvp.action.bean.ActionFirstTypeBean;
import com.scj.beilu.app.mvp.action.bean.ActionTypeInfoBean;
import com.scj.beilu.app.ui.action.adapter.ActionListFragAdapter;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/3/16 17:38
 * 动作导航
 */
public class ActionNavListFrag extends BaseMvpFragment<ActionNavPre.ActionNavView, ActionNavPre>
        implements ActionNavPre.ActionNavView, TabLayout.OnTabSelectedListener, ActionListFrag.onErrorListener, View.OnClickListener {

    private ImageButton mBtnBack;
    private TabLayout mTabLayout;
    private RelativeLayout mRelativeLayout;
    private ViewPager mViewPagerActionContent;
    private ActionListFragAdapter mActionListFragAdapter;
    private int mLineWidth;

    @Override
    public ActionNavPre createPresenter() {
        return new ActionNavPre(mFragmentActivity);
    }

    @Nullable
    @Override
    public int getLayout() {
        return R.layout.frag_action_data;
    }

    @Override
    public void initView() {
        super.initView();

        mBtnBack = findViewById(R.id.btn_back);
        mTabLayout = findViewById(R.id.tab_action_type_manager);
        mRelativeLayout = findViewById(R.id.relativeLayout);
        mViewPagerActionContent = findViewById(R.id.view_pager_action_content);
        if (isAdded()) {
            mLineWidth = getResources().getDimensionPixelOffset(R.dimen.tab_indicator_width);
        }
        ImmersionBar.with(this)
                .titleBar(mRelativeLayout)
                .statusBarDarkFont(true, 0.2f).init();
        mTabLayout.addTab(mTabLayout.newTab());
        mTabLayout.addTab(mTabLayout.newTab());
        mTabLayout.addTab(mTabLayout.newTab());
        mTabLayout.setupWithViewPager(mViewPagerActionContent);

        mTabLayout.addOnTabSelectedListener(this);

        mActionListFragAdapter = new ActionListFragAdapter(getChildFragmentManager());
        mActionListFragAdapter.setOnErrorListener(this);
        mViewPagerActionContent.setAdapter(mActionListFragAdapter);
        mViewPagerActionContent.setOffscreenPageLimit(3);
        mBtnBack.setOnClickListener(this);

    }

    private View getTabLayout(CharSequence title) {
        View view = LayoutInflater.from(mFragmentActivity).inflate(R.layout.layout_action_tab_item, null);
        TextView tvTab = view.findViewById(R.id.tv_tab_item);
        tvTab.setText(title);
        return view;
    }

    private void setTabSelector(TabLayout.Tab tab, boolean selected) {
        View view = tab.getCustomView();
        if (view == null) return;
        TextView tvTab = view.findViewById(R.id.tv_tab_item);
        ImageView ivLine = view.findViewById(R.id.iv_tab_bottom_line);
        tvTab.setSelected(selected);
        ivLine.setSelected(selected);
        if (selected) {
            int width = tvTab.getWidth();
            if (width == 0) {
                tvTab.measure(0, 0);
                width = tvTab.getMeasuredWidth();
            }
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) ivLine.getLayoutParams();
            params.width = width + mLineWidth;
            ivLine.setLayoutParams(params);
            ivLine.invalidate();
            tvTab.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            tvTab.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        } else {
            tvTab.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            tvTab.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        }
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        setTabSelector(tab, true);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        setTabSelector(tab, false);
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        getPresenter().getAllType();
    }

    @Override
    public void onTopFirstListResult(ActionTypeInfoBean infoBean) {

        try {
            List<ActionFirstTypeBean> topFirstTypeList = infoBean.mTopListTypeList.getDes();

            mActionListFragAdapter.setFirstTypeList(topFirstTypeList);

            for (int i = 0; i < mTabLayout.getTabCount(); i++) {
                String actionDesName = topFirstTypeList.get(i).getActionDesName();
                mTabLayout.getTabAt(i).setCustomView(getTabLayout(actionDesName));
            }
            setTabSelector(mTabLayout.getTabAt(0), true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onReStartRequest() {
        getPresenter().getAllType();
    }

    @Override
    public void onClick(View v) {
        mFragmentActivity.onBackPressed();
    }
}
