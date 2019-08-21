package com.scj.beilu.app.ui.act;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseMvpActivity;
import com.scj.beilu.app.mvp.mine.message.MsgManagerActPre;
import com.scj.beilu.app.ui.mine.adapter.MsgManagerPagerAdapter;

/**
 * @author Mingxun
 * @time on 2019/1/14 19:50
 */
public class MsgManagerAct extends BaseMvpActivity<MsgManagerActPre.MsgManagerView, MsgManagerActPre>
        implements MsgManagerActPre.MsgManagerView {

    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private MsgManagerPagerAdapter mMsgManagerPagerAdapter;
    public static final String EXTRA_VAL = "type";
    private final String[] mTitles = {"回复", "喜欢", "评论"};

    public static void startMsgManagerAct(Activity activity) {
        Intent intent = new Intent(activity, MsgManagerAct.class);

        activity.startActivity(intent);
    }


    @Override
    public int getLayout() {
        return R.layout.act_msg_manager;
    }

    @Override
    public void initView() {
        super.initView();
        mViewPager = findViewById(R.id.view_pager_msg_manager);
        mTabLayout = findViewById(R.id.tab_msg_manager);
        mTabLayout.setupWithViewPager(mViewPager);

        mMsgManagerPagerAdapter = new MsgManagerPagerAdapter(getSupportFragmentManager());
        mMsgManagerPagerAdapter.setTitles(mTitles);
        mViewPager.setAdapter(mMsgManagerPagerAdapter);

        int tabCount = mTabLayout.getTabCount();
        for (int i = 0; i < tabCount; i++) {
            mTabLayout.getTabAt(i).setCustomView(getTabLayout(mTitles[i]));
        }

        setTabSelector(mTabLayout.getTabAt(0), true);

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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
        });

        mViewPager.setOffscreenPageLimit(3);

    }

    @NonNull
    @Override
    public MsgManagerActPre createPresenter() {
        return new MsgManagerActPre(this);
    }


    public View getTabLayout(CharSequence title) {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_msg_tab_item, null);
        TextView tvTab = view.findViewById(R.id.tv_tab_item);
        tvTab.setText(title);
        return view;
    }

    public void setTabSelector(TabLayout.Tab tab, boolean selected) {
        View view = tab.getCustomView();
        if (view == null) return;
        TextView tvTab = view.findViewById(R.id.tv_tab_item);
        ImageView ivLine = view.findViewById(R.id.iv_tab_bottom_line);
        tvTab.setSelected(selected);
        ivLine.setSelected(selected);
        if (selected) {
            tvTab.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            tvTab.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        } else {
            tvTab.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            tvTab.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        }
    }

}
