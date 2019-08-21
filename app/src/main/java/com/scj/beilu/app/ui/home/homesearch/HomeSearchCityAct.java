package com.scj.beilu.app.ui.home.homesearch;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseMvpActivity;
import com.scj.beilu.app.mvp.home.HomeSearchCityPre;
import com.scj.beilu.app.ui.home.adapter.HomeSearchCityAdapter;

/**
 * author: SunGuiLan
 * date:2019/2/14
 * descriptin:定位城市
 */
public class HomeSearchCityAct extends BaseMvpActivity<HomeSearchCityPre.HomeSearchCityView, HomeSearchCityPre> implements HomeSearchCityPre.HomeSearchCityView {
    private RecyclerView m_city;

    public static void startHomeSearchCityAct(Activity activity) {
        Intent intent = new Intent(activity, HomeSearchCityAct.class);
        activity.startActivity(intent);
    }

    @Override
    public void initView() {
        super.initView();
        m_city = findViewById(R.id.mRecyclerView);
        m_city.setAdapter(new HomeSearchCityAdapter());
        m_city.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(0, 15, 0, 15);
            }
        });
    }

    @NonNull
    @Override
    public HomeSearchCityPre createPresenter() {
        return new HomeSearchCityPre();
    }

    @Override
    public int getLayout() {
        return R.layout.act_home_search_city;
    }
}
