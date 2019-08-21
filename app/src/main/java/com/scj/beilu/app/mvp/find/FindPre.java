package com.scj.beilu.app.mvp.find;

import android.content.Context;
import android.graphics.Typeface;
import android.support.design.widget.TabLayout;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.mx.pro.lib.mvp.MvpView;
import com.mx.pro.lib.mvp.network.config.BaseMvpPresenter;
import com.scj.beilu.app.R;


/**
 * date:2019/2/11
 */
public class FindPre extends BaseMvpPresenter<FindPre.FindView> {

    public FindPre(Context context) {
        super(context);
    }

    public View getTabLayout(CharSequence title) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_findtab_item, null);
        TextView tvTab = view.findViewById(R.id.tv_tab_item);
        tvTab.setText(title);
        return view;
    }

    public void setTabSelector(TabLayout.Tab tab, boolean selected) {
        View view = tab.getCustomView();
        TextView tvTab = view.findViewById(R.id.tv_tab_item);
        tvTab.setSelected(selected);
        if (selected) {
            tvTab.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            tvTab.setTextSize(TypedValue.COMPLEX_UNIT_SP, 26);
        } else {
            tvTab.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            tvTab.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        }
    }

    public interface FindView extends MvpView {
    }
}
