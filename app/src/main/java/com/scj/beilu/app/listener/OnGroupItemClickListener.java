package com.scj.beilu.app.listener;

import android.view.View;

/**
 * @author Mingxun
 * @time on 2019/3/18 21:31
 */
public interface OnGroupItemClickListener<T> {
    void onItemClick(int groupPosition, int childPosition, T object, View view);
}
