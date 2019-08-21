package com.scj.beilu.app.mvp.base;

import com.mx.pro.lib.mvp.MvpView;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/2/17 19:03
 */
public interface BaseCheckArrayView extends MvpView {
    void onCheckLoadMore(List<?> list);
}
