package com.mx.pro.lib.mvp.manager;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.mx.pro.lib.BuildConfig;
import com.mx.pro.lib.mvp.MvpPresenter;
import com.mx.pro.lib.mvp.MvpView;
import com.orhanobut.logger.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * This class basically represents a Map for View Id to the Presenter / ViewState.
 * One instance of this class is also associated by {@link PresenterManager} to one Activity (kept
 * across screen orientation changes)
 */
class ActivityScopedCache {

    private final Map<String, PresenterHolder> mPresenterHolderMap = new HashMap<>();

    public ActivityScopedCache() {
    }

    public void clear() {
        for (PresenterHolder holder : mPresenterHolderMap.values()) {
            if (holder.mPresenter != null) {
                holder.mPresenter.detachView();
            }

            if (BuildConfig.LOG_DEBUG) {
                Logger.d("Found a Presenter that is still alive. This should never happen." +
                        " It seems that a MvpDelegate / MviDelegate didn't work correctly because this" +
                        " Delegate should have removed the presenter. The Presenter was "
                        + holder.mPresenter);
            }
        }
        mPresenterHolderMap.clear();
    }

    /**
     * Get the Presenter for a given {@link MvpView} if exists or <code>null</code>
     *
     * @param viewId The mosby internal view id
     * @param <P>    The type tof the {@link MvpPresenter}
     * @return The Presenter for the given view id or <code>null</code>
     */
    @Nullable
    public <P> P getPresenter(@NonNull String viewId) {
        PresenterHolder holder = mPresenterHolderMap.get(viewId);
        return holder == null ? null : (P) holder.mPresenter;
    }

    /**
     * Get the ViewState for a given {@link MvpView} if exists or <code>null</code>
     *
     * @param viewId The mosby internal view id
     * @param <VS>   The type tof the {@link MvpPresenter}
     * @return The ViewState for the given view id or <code>null</code>
     */
    @Nullable
    public <VS> VS getViewState(@NonNull String viewId) {
        PresenterHolder holder = mPresenterHolderMap.get(viewId);
        return holder == null ? null : (VS) holder.mViewState;
    }

    /**
     * Put the presenter in the internal cache
     *
     * @param viewId    The mosby internal View id of the {@link MvpView} which the presenter is
     *                  associated to.
     * @param presenter The Presenter
     */
    public void putPresenter(@NonNull String viewId,
                             @NonNull MvpPresenter<? extends MvpView> presenter) {
        if (viewId == null) {
            throw new NullPointerException("viewId is null");
        }
        if (presenter == null) {
            throw new NullPointerException("presenter is null");
        }

        PresenterHolder holder = mPresenterHolderMap.get(viewId);
        if (holder == null) {
            holder = new PresenterHolder();
            holder.mPresenter = presenter;
            mPresenterHolderMap.put(viewId, holder);
        } else {
            holder.mPresenter = presenter;
        }

        if (BuildConfig.LOG_DEBUG) {
            Logger.d("presenter Map save success");
        }
    }

    /**
     * Removes the Presenter (and ViewState) from the internal storage
     *
     * @param viewId The msoby internal view id
     */
    public void remove(@NonNull String viewId) {
        if (viewId == null) {
            throw new NullPointerException("viewId is null");
        }
        mPresenterHolderMap.remove(viewId);

        if (BuildConfig.LOG_DEBUG) {
            Logger.d("presenterMap remove success");
        }
    }

    /**
     * Put the viewstate in the internal cache
     *
     * @param viewId    The mosby internal View id of the {@link MvpView} which the presenter is
     *                  associated to.
     * @param viewState The Viewstate
     */
    public void putViewState(@NonNull String viewId, @NonNull Object viewState) {
        if (viewId == null) {
            throw new NullPointerException("viewId is null");
        }
        if (viewState == null) {
            throw new NullPointerException("viewState is null");
        }

        PresenterHolder holder = mPresenterHolderMap.get(viewId);
        if (holder == null) {
            holder = new PresenterHolder();
            holder.mViewState = viewState;
            mPresenterHolderMap.put(viewId, holder);
        } else {
            holder.mViewState = viewState;
        }
        if (BuildConfig.LOG_DEBUG) {
            Logger.w("putViewState is save success");
        }
    }

    static final class PresenterHolder {
        private MvpPresenter<?> mPresenter;
        private Object mViewState;
    }
}
