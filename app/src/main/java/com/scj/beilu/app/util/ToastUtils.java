package com.scj.beilu.app.util;

import android.content.Context;
import android.widget.Toast;

/**
 * @author Mingxun
 * @time on 2018/12/29 01:27
 */
public class ToastUtils {
    private static Toast mToast;

    public static void showToast(Context mContext, String text) {
        if (mToast != null) {
            mToast.setText(text);
        } else {
            mToast = Toast.makeText(mContext, text, Toast.LENGTH_SHORT);
        }
        mToast.show();
    }


}
