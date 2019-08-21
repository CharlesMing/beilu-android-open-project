package com.scj.beilu.app.mvp.mine.message;

import android.content.Context;

import com.scj.beilu.app.mvp.base.TabPresenter;
import com.scj.beilu.app.mvp.base.TabView;

/**
 * @author Mingxun
 * @time on 2019/1/14 20:01
 */
public class MsgManagerActPre extends TabPresenter<MsgManagerActPre.MsgManagerView> {
    public MsgManagerActPre(Context context) {
        super(context);
    }
    public interface MsgManagerView extends TabView {
    }
}
