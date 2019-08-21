package com.scj.beilu.app.mvp.common.entity;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Mingxun
 * @time on 2019/2/28 23:04
 */
public class SearchType {

    public static final int SEARCH_NEWS = 0;
    public static final int SEARCH_FIND = 1;

    @IntDef({SEARCH_NEWS, SEARCH_FIND})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Search {
    }
}
