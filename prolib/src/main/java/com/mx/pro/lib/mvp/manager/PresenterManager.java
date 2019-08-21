package com.mx.pro.lib.mvp.manager;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;

import com.mx.pro.lib.BuildConfig;
import com.mx.pro.lib.mvp.MvpPresenter;
import com.mx.pro.lib.mvp.MvpView;
import com.orhanobut.logger.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


/**
 * this is presenter manager .used to put/remove/clear
 */
public class PresenterManager {

    final static String KEY_ACTIVITY_ID = "app.beilu.scj.com.mvp.presenterMangerId";
    private final static Map<Activity, String> ACTIVITY_ID_MAP = new HashMap<>();
    private final static Map<String, ActivityScopedCache> ACTIVITY_SCOPED_CACHE_MAP = new HashMap<>();

    static final Application.ActivityLifecycleCallbacks ACTIVITY_LIFECYCLE_CALLBACKS = new Application.ActivityLifecycleCallbacks() {
        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            if (savedInstanceState != null) {
                String activityId = savedInstanceState.getString(KEY_ACTIVITY_ID);
                if (activityId != null) {
                    ACTIVITY_ID_MAP.put(activity, activityId);
                }
            }
        }

        @Override
        public void onActivityStarted(Activity activity) {

        }

        @Override
        public void onActivityResumed(Activity activity) {

        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {

        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            String activityId = ACTIVITY_ID_MAP.get(activity);
            if (activityId != null) {
                outState.putString(KEY_ACTIVITY_ID, activityId);
            }
        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            if (!activity.isChangingConfigurations()) {
                //Activity will be destroyed permanently,so reset the cache
                String activityId = ACTIVITY_ID_MAP.get(activity);
                if (activityId != null) {
                    ActivityScopedCache scopedCache = ACTIVITY_SCOPED_CACHE_MAP.get(activityId);
                    if (scopedCache != null) {
                        scopedCache.clear();
                        ACTIVITY_ID_MAP.remove(activityId);
                    }

                    // No Activity Scoped cache available, so unregister
                    if (ACTIVITY_SCOPED_CACHE_MAP.isEmpty()) {
                        activity.getApplication().unregisterActivityLifecycleCallbacks(ACTIVITY_LIFECYCLE_CALLBACKS);
                    }
                    if (BuildConfig.LOG_DEBUG) {
                        Logger.d("Unregistering ActivityLifecycleCallbacks");
                    }
                }
            }
            ACTIVITY_ID_MAP.remove(activity);
        }
    };

    private PresenterManager() {
        throw new RuntimeException(" Not instantiatable");
    }

    /**
     * Get an already existing {@link ActivityScopedCache} or creates a new one if not existing yet
     *
     * @param activity The Activitiy for which you want to get the activity scope for
     * @return The {@link ActivityScopedCache} for the given Activity
     */
    @NonNull
    @MainThread
    static ActivityScopedCache getOrCreateActivityScopedCache(@NonNull Activity activity) {
        if (activity == null) {
            throw new NullPointerException("activity is null");
        }
        String activityId = ACTIVITY_ID_MAP.get(activity);
        if (activityId == null) {
            activityId = UUID.randomUUID().toString();
            ACTIVITY_ID_MAP.put(activity, activityId);
            if (ACTIVITY_ID_MAP.size() == 1) {
                activity.getApplication().registerActivityLifecycleCallbacks(ACTIVITY_LIFECYCLE_CALLBACKS);
                if (BuildConfig.LOG_DEBUG) {
                    Logger.d("Registering ActivityLifecycleCallbacks");
                }
            }
        }
        ActivityScopedCache scopedCache = ACTIVITY_SCOPED_CACHE_MAP.get(activityId);
        if (scopedCache == null) {
            scopedCache = new ActivityScopedCache();
            ACTIVITY_SCOPED_CACHE_MAP.put(activityId, scopedCache);
        }
        return scopedCache;
    }

    /**
     * Get the  {@link ActivityScopedCache} for the given Activity or <code>null</code> if no {@link
     * ActivityScopedCache} exists for the given Activity
     *
     * @param activity The activity
     * @return The {@link ActivityScopedCache} or null
     * @see #getOrCreateActivityScopedCache(Activity)
     */
    @NonNull
    @MainThread
    static ActivityScopedCache getActivityScope(@NonNull Activity activity) {
        if (activity == null) {
            throw new NullPointerException("Activity is null");
        }

        String activityId = ACTIVITY_ID_MAP.get(activity);
        if (activityId == null) {
            return null;
        }
        return ACTIVITY_SCOPED_CACHE_MAP.get(activityId);
    }

    /**
     * Get the presenter for the View with the given (Mosby - internal) view Id or <code>null</code>
     * if no presenter for the given view (via view id) exists.
     *
     * @param activity The Activity (used for scoping)
     * @param viewId   The mosby internal View Id (unique among all {@link MvpView}
     * @param <P>      The Presenter type
     * @return The Presenter or <code>null</code>
     */
    @NonNull
    public static <P> P getPresenter(@NonNull Activity activity, @NonNull String viewId) {
        if (activity == null) {
            throw new NullPointerException("activity is null");
        }
        if (viewId == null) {
            throw new NullPointerException("viewId is null");
        }
        ActivityScopedCache activityScoped = getActivityScope(activity);
        return activityScoped == null ? null : (P) activityScoped.getPresenter(viewId);
    }

    /**
     * Get the ViewState (see mosby viestate modlue) for the View with the given (Mosby - internal)
     * view Id or <code>null</code>
     * if no viewstate for the given view exists.
     *
     * @param activity The Activity (used for scoping)
     * @param viewId   The mosby internal View Id (unique among all {@link MvpView}
     * @param <VS>     The type of the ViewState type
     * @return The Presenter or <code>null</code>
     */
    @NonNull
    public static <VS> VS getViewState(@NonNull Activity activity, @NonNull String viewId) {
        if (activity == null) {
            throw new NullPointerException("activity is null");
        }
        if (viewId == null) {
            throw new NullPointerException("viewId is null");
        }
        ActivityScopedCache activityScoped = getActivityScope(activity);
        return activityScoped == null ? null : (VS) activityScoped.getViewState(viewId);
    }

    /**
     * Get the Activity of a context. This is typically used to determine the hosting activity of a
     * {@link android.view.View}
     *
     * @param context The context
     * @return The Activity or throws an Exception if Activity couldnt be determined
     */
    @NonNull
    public static Activity getActivity(@NonNull Context context) {
        if (context == null) {
            throw new NullPointerException("context == null");
        }
        if (context instanceof Activity) {
            return (Activity) context;
        }

        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        throw new IllegalStateException("Could not find the surrounding Activity");
    }

    /**
     * Puts the presenter into the internal cache
     *
     * @param activity  The parent activity
     * @param viewId    the view id (mosby internal)
     * @param presenter the presenter
     */
    public static void putPresenter(@NonNull Activity activity, @NonNull String viewId,
                                    @NonNull MvpPresenter<? extends MvpView> presenter) {
        if (activity == null) {
            throw new NullPointerException("Activity is null");
        }

        ActivityScopedCache scopedCache = getOrCreateActivityScopedCache(activity);
        scopedCache.putPresenter(viewId, presenter);
    }

    /**
     * Puts the presenter into the internal cache
     *
     * @param activity  The parent activity
     * @param viewId    the view id (mosby internal)
     * @param viewState the presenter
     */
    public static void putViewState(@NonNull Activity activity, @NonNull String viewId,
                                    @NonNull Object viewState) {
        if (activity == null) {
            throw new NullPointerException("Activity is null");
        }

        ActivityScopedCache scopedCache = getOrCreateActivityScopedCache(activity);
        scopedCache.putViewState(viewId, viewState);
    }


    /**
     * Removes the Presenter (and ViewState) for the given View. Does nothing if no Presenter is
     * stored internally with the given viewId
     *
     * @param activity The activity
     * @param viewId   The mosby internal view id
     */
    public static void remove(@NonNull Activity activity, @NonNull String viewId) {
        if (activity == null) {
            throw new NullPointerException("Activity is null");
        }

        ActivityScopedCache activityScope = getActivityScope(activity);
        if (activityScope != null) {
            activityScope.remove(viewId);
        }
    }

}
