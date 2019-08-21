package com.scj.beilu.app.mvp.find;

import android.content.Context;
import android.content.pm.ActivityInfo;

import com.mx.pro.lib.album.AlbumManager;
import com.mx.pro.lib.album.GlideEngine;
import com.mx.pro.lib.album.MimeType;
import com.mx.pro.lib.album.filter.Filter;
import com.mx.pro.lib.album.internal.entity.CaptureStrategy;
import com.mx.pro.lib.fragmentation.support.SupportFragment;
import com.mx.pro.lib.mvp.MvpView;
import com.mx.pro.lib.mvp.network.config.BaseMvpPresenter;
import com.scj.beilu.app.R;

import io.reactivex.disposables.CompositeDisposable;

import static com.scj.beilu.app.api.Constants.DIRECTORY;
import static com.scj.beilu.app.api.Constants.REQUEST_CODE_CHOOSE;

/**
 * @author Mingxun
 * @time on 2019/2/19 15:42
 */
public class FindPublishTypePre extends BaseMvpPresenter<FindPublishTypePre.FindPublishTypeView> {
    private CompositeDisposable mDisposable;

    public FindPublishTypePre(Context context) {
        super(context);
        mDisposable = new CompositeDisposable();
    }

    public void startSelectImg(SupportFragment fragment, int publishType) {

        int maxSize;
        boolean ofImage;
        if (publishType == 1) {
            maxSize = 9;
            ofImage = true;
        } else {
            maxSize = 1;
            ofImage = false;
        }
        AlbumManager.from(fragment)
                .choose(ofImage ? MimeType.ofImage() : MimeType.ofVideo(), false)
                .showSingleMediaType(true)
                .countable(true)
                .capture(true)
                .captureStrategy(
                        new CaptureStrategy(true, mContext.getPackageName() + ".fileprovider", DIRECTORY))
                .maxSelectable(maxSize)
                .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                .gridExpectedSize(
                        mContext.getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                .thumbnailScale(0.85f)
                .imageEngine(new GlideEngine())
                .maxOriginalSize(10)
                .autoHideToolbarOnSingleTap(true)
                .forResult(REQUEST_CODE_CHOOSE);
    }

    @Override
    public void destroy() {
        super.destroy();
        mDisposable.clear();
    }

    public interface FindPublishTypeView extends MvpView {
    }

}
