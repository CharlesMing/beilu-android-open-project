package com.scj.beilu.app.ui.act;

import android.os.Bundle;
import android.support.annotation.IdRes;

import com.mx.pro.lib.fragmentation.support.SupportActivity;
import com.mx.pro.lib.fragmentation.support.SupportFragment;
import com.scj.beilu.app.R;
import com.scj.beilu.app.ui.course.CourseFrag;
import com.scj.beilu.app.ui.find.FindInfoFrag;
import com.scj.beilu.app.ui.home.HomeFrag;
import com.scj.beilu.app.ui.mine.MineFrag;
import com.scj.beilu.app.ui.news.NewsFrag;
import com.scj.beilu.app.widget.bottom.BottomNavigationView;


public class MainActivity extends SupportActivity implements HomeFrag.onChangeBottomListener {
    private BottomNavigationView mNavigationView;

    private final int NEWS = 0;
    private final int COURSE = 1;
    private final int HOME = 2;
    private final int FIND = 3;
    private final int ME = 4;
    private SupportFragment[] mFragments = new SupportFragment[5];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mNavigationView = findViewById(R.id.bottomView);
        SupportFragment homeFrag = findFragment(HomeFrag.class);
        if (homeFrag == null) {
            mFragments[HOME] = new HomeFrag();
            mFragments[FIND] = new FindInfoFrag();
            mFragments[NEWS] = new NewsFrag();
            mFragments[COURSE] = new CourseFrag();
            mFragments[ME] = new MineFrag();
            loadMultipleRootFragment(R.id.fl_content, HOME,
                    mFragments[HOME],
                    mFragments[FIND],
                    mFragments[NEWS],
                    mFragments[COURSE],
                    mFragments[ME]);
        } else {
            mFragments[HOME] = homeFrag;
            mFragments[FIND] = findFragment(FindInfoFrag.class);
            mFragments[NEWS] = findFragment(NewsFrag.class);
            mFragments[COURSE] = findFragment(CourseFrag.class);
            mFragments[ME] = findFragment(MineFrag.class);
        }
        mNavigationView.setOnNavigationItemSelectedListener((position, prePosition) -> showHideFragment(mFragments[position], mFragments[prePosition]));
        mNavigationView.setOnNavigationItemReselectedListener(curPos -> {


        });
        mNavigationView.setSelectedItemId(R.id.nav_home);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void onBottomNavIndex(@IdRes int itemId) {
        mNavigationView.setSelectedItemId(itemId);
    }

}
