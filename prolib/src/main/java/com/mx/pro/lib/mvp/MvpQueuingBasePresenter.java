package com.mx.pro.lib.mvp;

import android.support.annotation.MainThread;
import android.support.annotation.NonNull;

import java.lang.ref.WeakReference;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @param <V>
 * @author Charlie
 */
public class MvpQueuingBasePresenter<V extends MvpView> implements MvpPresenter<V> {

    public interface ViewAction<V> {
        void run(@NonNull V mvpView);
    }


    private Queue<ViewAction<V>> mViewActionQueue = new ConcurrentLinkedQueue<>();
    private WeakReference<V> mViewRef;
    private boolean mPresenterDestroyed;

    private void runQueueActions() {
        V view = mViewRef == null ? null : mViewRef.get();
        if (view != null) {
            while (!mViewActionQueue.isEmpty()) {
                ViewAction<V> action = mViewActionQueue.poll();
                action.run(view);
            }
        }

    }

    @Override
    public void attachView(@NonNull V view) {
        mPresenterDestroyed = false;
        mViewRef = new WeakReference<>(view);
        runQueueActions();
    }

    @Override
    public void detachView() {
        mViewRef.clear();
    }

    @Override
    public void destroy() {
        mViewActionQueue.clear();
        mPresenterDestroyed = false;
    }


    @MainThread
    protected void onceViewAttached(ViewAction<V> action) {
        mViewActionQueue.add(action);
        runQueueActions();
    }

    @MainThread
    private final void ifViewAttached(boolean exceptionIfViewNotAttached, ViewAction<V> action) {
        final V view = mViewRef == null ? null : mViewRef.get();
        if (view != null) {
            action.run(view);
        } else if (exceptionIfViewNotAttached) {
            throw new IllegalStateException("No View attached to Presenter. Presenter destroyed = " + mPresenterDestroyed);
        }
    }

    protected final void ifViewAttached(ViewAction<V> action) {
        ifViewAttached(false, action);
    }
}
