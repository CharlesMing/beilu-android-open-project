package com.scj.beilu.app.mvp.mine;

import android.content.Context;

import com.scj.beilu.app.mvp.base.TabPresenter;
import com.scj.beilu.app.mvp.base.TabView;


public class CollectManagerPre extends TabPresenter<CollectManagerPre.CollectManagerView> {
    public CollectManagerPre(Context context) {
        super(context);
    }

    public interface CollectManagerView extends TabView {
    }
}
