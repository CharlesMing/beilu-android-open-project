package com.mx.pro.lib.mvp.delegate;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.BackstackAccessor;
import android.support.v4.app.Fragment;
import android.view.View;

import com.mx.pro.lib.BuildConfig;
import com.mx.pro.lib.mvp.MvpPresenter;
import com.mx.pro.lib.mvp.MvpView;
import com.mx.pro.lib.mvp.manager.PresenterManager;
import com.orhanobut.logger.Logger;

import java.util.UUID;



public class FragmentMvpDelegateImpl<V extends MvpView, P extends MvpPresenter<V>>
        implements FragmentMvpDelegate<V, P> {

    protected static final String KEY_PRO_VIEW_ID = "app.beilu.scj.com.mvp.delegate.mvp.id";
    private MvpDelegateCallback<V, P> mVPMvpDelegateCallback;
    protected Fragment fragment;
    protected final boolean keepPresenterInstanceDuringScreenOrientationChanges;
    protected final boolean keepPresenterOnBackstack;
    private boolean onViewCreatedCalled = false;
    protected String mProId;

    public FragmentMvpDelegateImpl(@NonNull Fragment fragment,
                                   @NonNull MvpDelegateCallback<V, P> delegateCallback,
                                   boolean keepPresenterDuringScreenOrientationChange, boolean keepPresenterOnBackstack) {
        if (delegateCallback == null) {
            throw new NullPointerException("MvpDelegateCallback is null!");
        }

        if (fragment == null) {
            throw new NullPointerException("Fragment is null!");
        }

        if (!keepPresenterDuringScreenOrientationChange && keepPresenterOnBackstack) {
            throw new IllegalArgumentException("It is not possible to keep the presenter on backstack, "
                    + "but NOT keep presenter through screen orientation changes. Keep presenter on backstack also "
                    + "requires keep presenter through screen orientation changes to be enabled");
        }
        this.fragment = fragment;
        this.mVPMvpDelegateCallback = delegateCallback;
        this.keepPresenterInstanceDuringScreenOrientationChanges =
                keepPresenterDuringScreenOrientationChange;
        this.keepPresenterOnBackstack = keepPresenterOnBackstack;
    }

    /**
     * Generates the unique ( internal) view id and calls {@link
     * MvpDelegateCallback#createPresenter()}
     * to create a new presenter instance
     *
     * @return The new created presenter instance
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private P createViewIdAndCreatePresenter() {

        P presenter = mVPMvpDelegateCallback.createPresenter();
        if (presenter == null) {
            throw new NullPointerException(
                    "Presenter returned from createPresenter() is null. Activity is " + getActivity());
        }
        if (keepPresenterInstanceDuringScreenOrientationChanges) {
            mProId = UUID.randomUUID().toString();
            PresenterManager.putPresenter(getActivity(), mProId, presenter);
        }
        return presenter;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        P presenter = getPresenter();
        presenter.attachView(getMvpView());
        onViewCreatedCalled = true;
    }

    static boolean retainPresenterInstance(Activity activity, Fragment fragment,
                                           boolean keepPresenterInstanceDuringScreenOrientationChanges,
                                           boolean keepPresenterOnBackstack) {

        if (activity.isChangingConfigurations()) {
            return keepPresenterInstanceDuringScreenOrientationChanges;
        }

        if (activity.isFinishing()) {
            return false;
        }
        if (keepPresenterOnBackstack && BackstackAccessor.isFragmentOnBackStack(fragment)) {
            return true;
        }

        return !fragment.isRemoving();
    }

    @Override
    public void onDestroyView() {

        onViewCreatedCalled = false;

        getPresenter().detachView();

        if (BuildConfig.LOG_DEBUG) {
            Logger.d("detached MvpView from Presenter. MvpView "
                    + mVPMvpDelegateCallback.getMvpView()
                    + "   Presenter: "
                    + getPresenter());
        }
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onStart() {

        if (!onViewCreatedCalled) {
            throw new IllegalStateException("It seems that you are using "
                    + mVPMvpDelegateCallback.getClass().getCanonicalName()
                    + " as headless (UI less) fragment (because onViewCreated() has not been called or maybe delegation misses that part). Having a Presenter without a View (UI) doesn't make sense. Simply use an usual fragment instead of an MvpFragment if you want to use a UI less Fragment");
        }
    }

    @Override
    public void onStop() {

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

    }

    @Override
    public void onAttach(Activity activity) {

    }

    @Override
    public void onDetach() {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if ((keepPresenterInstanceDuringScreenOrientationChanges || keepPresenterOnBackstack)
                && outState != null) {
            outState.putString(KEY_PRO_VIEW_ID, mProId);

            if (BuildConfig.LOG_DEBUG) {
                Logger.d("Saving ViewId into Bundle. ViewId: " + mProId);
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onCreate(Bundle bundle) {

        P presenter = null;

        if (bundle != null && keepPresenterInstanceDuringScreenOrientationChanges) {

            mProId = bundle.getString(KEY_PRO_VIEW_ID);

            if (BuildConfig.LOG_DEBUG) {
                Logger.d(
                        "View ID = " + mProId + " for MvpView: " + mVPMvpDelegateCallback.getMvpView());
            }

            if (mProId != null
                    && (presenter = PresenterManager.getPresenter(getActivity(), mProId)) != null) {
                // Presenter restored from cache
                if (BuildConfig.LOG_DEBUG) {
                    Logger.d("Reused presenter " + presenter + " for view " + mVPMvpDelegateCallback.getMvpView());
                }
            } else {
                //
                // No presenter found in cache, most likely caused by process death
                //
                presenter = createViewIdAndCreatePresenter();
                if (BuildConfig.LOG_DEBUG) {
                    Logger.d("No presenter found although view Id was here: "
                            + mProId
                            + ". Most likely this was caused by a process death. New Presenter created"
                            + presenter
                            + " for view "
                            + getMvpView());
                }
            }
        } else {
            //
            // Activity starting first time, so create a new presenter
            //
            presenter = createViewIdAndCreatePresenter();
            if (BuildConfig.LOG_DEBUG) {
                Logger.d("New presenter " + presenter + " for view " + getMvpView());
            }
        }

        if (presenter == null) {
            throw new IllegalStateException(
                    "Oops, Presenter is null. This seems to be a  internal bug. Please report this issue here: https://github.com/sockeqwe//issues");
        }

        mVPMvpDelegateCallback.setPresenter(presenter);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onDestroy() {

        Activity activity = getActivity();
        boolean retainPresenterInstance = retainPresenterInstance(activity, fragment,
                keepPresenterInstanceDuringScreenOrientationChanges, keepPresenterOnBackstack);

        P presenter = getPresenter();
        if (!retainPresenterInstance) {
            presenter.destroy();
            if (BuildConfig.LOG_DEBUG) {
                Logger.d("Presenter destroyed. MvpView "
                        + mVPMvpDelegateCallback.getMvpView()
                        + "   Presenter: "
                        + presenter);
            }
        }

        if (!retainPresenterInstance && mProId != null) {
            // ViewId is null if keepPresenterInstanceDuringScreenOrientationChanges  == false
            PresenterManager.remove(activity, mProId);
        }
    }

    @NonNull
    private Activity getActivity() {
        Activity activity = fragment.getActivity();
        if (activity == null) {
            throw new NullPointerException(
                    "Activity returned by Fragment.getActivity() is null. Fragment is " + fragment);
        }

        return activity;
    }

    private P getPresenter() {
        P presenter = mVPMvpDelegateCallback.getPresenter();
        if (presenter == null) {
            throw new NullPointerException("Presenter returned from getPresenter() is null");
        }
        return presenter;
    }

    private V getMvpView() {
        V view = mVPMvpDelegateCallback.getMvpView();
        if (view == null) {
            throw new NullPointerException("View returned from getMvpView() is null");
        }
        return view;
    }
}
