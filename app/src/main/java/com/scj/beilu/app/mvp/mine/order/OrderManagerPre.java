package com.scj.beilu.app.mvp.mine.order;

import android.content.Context;

import com.scj.beilu.app.mvp.base.TabPresenter;
import com.scj.beilu.app.mvp.base.TabView;

/**
 * @author Mingxun
 * @time on 2019/1/15 21:12
 */
public class OrderManagerPre extends TabPresenter<OrderManagerPre.OrderManagerView> {
       public OrderManagerPre(Context context) {
        super(context);
    }

    public interface OrderManagerView extends TabView {

    }
}
