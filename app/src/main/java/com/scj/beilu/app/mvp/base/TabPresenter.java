package com.scj.beilu.app.mvp.base;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.mx.pro.lib.mvp.network.config.BaseMvpPresenter;
import com.scj.beilu.app.R;

/**
 * @author Mingxun
 * @time on 2019/1/15 21:22
 */
public class TabPresenter<V extends TabView> extends BaseMvpPresenter<V> {

    private int mLineWidth;

    public TabPresenter(Context context) {
        super(context);
        mLineWidth = context.getResources().getDimensionPixelOffset(R.dimen.tab_indicator_width);
    }

    public View getTabLayout(CharSequence title) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_news_tab_item, null);
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
            int width = tvTab.getWidth();
            if (width == 0) {
                tvTab.measure(0, 0);
                width = tvTab.getMeasuredWidth();
            }
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) ivLine.getLayoutParams();
            params.width = width + mLineWidth;
            ivLine.setLayoutParams(params);
            ivLine.invalidate();
            tvTab.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        } else {
            tvTab.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        }
    }
}
