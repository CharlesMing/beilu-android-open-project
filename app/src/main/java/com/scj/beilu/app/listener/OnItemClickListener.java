package com.scj.beilu.app.listener;

import android.view.View;

/**
 * @author Mingxun
 * @time on 2018/12/28 11:18
 */
public interface OnItemClickListener<T> {
    void onItemClick(int position, T entity, View view);
}
