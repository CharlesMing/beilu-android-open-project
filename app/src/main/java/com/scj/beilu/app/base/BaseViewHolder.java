package com.scj.beilu.app.base;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import static android.view.View.NO_ID;

/**
 * @author Mingxun
 * @time on 2018/12/27 18:46
 */
public abstract class BaseViewHolder extends RecyclerView.ViewHolder {
    private View mItemView;

    public BaseViewHolder(View itemView) {
        super(itemView);
        mItemView = itemView;
    }

    @NonNull
    public final <T extends View> T findViewById(@IdRes int id) {
        if (mItemView == null || id == NO_ID) {
            return null;
        }
        return mItemView.findViewById(id);
    }
}
