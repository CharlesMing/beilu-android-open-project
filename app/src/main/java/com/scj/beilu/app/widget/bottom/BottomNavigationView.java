package com.scj.beilu.app.widget.bottom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.AbsSavedState;
import android.support.v4.view.ViewCompat;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.view.SupportMenuInflater;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.TintTypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.orhanobut.logger.Logger;
import com.scj.beilu.app.R;
import com.scj.beilu.app.widget.bottom.internal.BottomNavigationMenu;

/**
 * @author Mingxun
 * @time on 2018/12/21 16:08
 */
public class BottomNavigationView extends FrameLayout {

    private static final int[] CHECKED_STATE_SET = {android.R.attr.state_checked};
    private static final int[] DISABLED_STATE_SET = {-android.R.attr.state_enabled};

    private static final int MENU_PRESENTER_ID = 1;

    private final MenuBuilder mMenu;
    private final BottomNavigationMenuView mMenuView;
    private final BottomNavigationPresenter mPresenter = new BottomNavigationPresenter();
    private MenuInflater mMenuInflater;

    private BottomNavigationView.OnNavigationItemSelectedListener mSelectedListener;
    private BottomNavigationView.OnNavigationItemReselectedListener mReselectedListener;
    private int mCurPosition = 0;

    public BottomNavigationView(Context context) {
        this(context, null);
    }

    public BottomNavigationView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @SuppressLint("RestrictedApi")
    public BottomNavigationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        ThemeUtils.checkAppCompatTheme(context);

        // Create the menu
        mMenu = new BottomNavigationMenu(context);

        mMenuView = new BottomNavigationMenuView(context);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        mMenuView.setLayoutParams(params);

        mPresenter.setBottomNavigationMenuView(mMenuView);
        mPresenter.setId(MENU_PRESENTER_ID);
        mMenuView.setPresenter(mPresenter);
        mMenu.addMenuPresenter(mPresenter);
        mPresenter.initForMenu(getContext(), mMenu);

        // Custom attributes
        TintTypedArray a = TintTypedArray.obtainStyledAttributes(context, attrs,
                R.styleable.BottomNavigationView, defStyleAttr,
                R.style.Widget_Design_BottomNavigationView);

        if (a.hasValue(R.styleable.BottomNavigationView_itemIconTint)) {
            mMenuView.setIconTintList(
                    a.getColorStateList(R.styleable.BottomNavigationView_itemIconTint));
        }
        if (a.hasValue(R.styleable.BottomNavigationView_itemTextColor)) {
            mMenuView.setItemTextColor(
                    a.getColorStateList(R.styleable.BottomNavigationView_itemTextColor));
        } else {
            mMenuView.setItemTextColor(
                    createDefaultColorStateList(android.R.attr.textColorSecondary));
        }
        if (a.hasValue(R.styleable.BottomNavigationView_elevation)) {
            ViewCompat.setElevation(this, a.getDimensionPixelSize(
                    R.styleable.BottomNavigationView_elevation, 0));
        }

        int itemBackground = a.getResourceId(R.styleable.BottomNavigationView_itemBackground, 0);
        mMenuView.setItemBackgroundRes(itemBackground);

        if (a.hasValue(R.styleable.BottomNavigationView_menu)) {
            inflateMenu(a.getResourceId(R.styleable.BottomNavigationView_menu, 0));
        }
        a.recycle();

        addView(mMenuView, params);
        if (Build.VERSION.SDK_INT < 21) {
            addCompatibilityTopDivider(context);
        }

        mMenu.setCallback(new MenuBuilder.Callback() {
            @Override
            public boolean onMenuItemSelected(MenuBuilder menu, MenuItem item) {
                BottomNavigationItemView itemView = findViewById(item.getItemId());
                int position = itemView.getItemPosition();
                if (mReselectedListener != null && item.getItemId() == getSelectedItemId()) {
                    mReselectedListener.onNavigationItemReselected(position);
                    return true; // item is already selected
                }

                if (mSelectedListener != null && position != mCurPosition) {
                    mSelectedListener.onNavigationItemSelected(position, mCurPosition);
                    mCurPosition = position;
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public void onMenuModeChange(MenuBuilder menu) {
            }
        });
    }

    /**
     * Set a listener that will be notified when a bottom navigation item is selected. This listener
     * will also be notified when the currently selected item is reselected, unless an
     * {@link BottomNavigationView.OnNavigationItemReselectedListener} has also been set.
     *
     * @param listener The listener to notify
     * @see #setOnNavigationItemReselectedListener(BottomNavigationView.OnNavigationItemReselectedListener)
     */
    public void setOnNavigationItemSelectedListener(
            @Nullable BottomNavigationView.OnNavigationItemSelectedListener listener) {
        mSelectedListener = listener;
    }

    /**
     * Set a listener that will be notified when the currently selected bottom navigation item is
     * reselected. This does not require an {@link android.support.design.widget.BottomNavigationView.OnNavigationItemSelectedListener} to be set.
     *
     * @param listener The listener to notify
     * @see #setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener)
     */
    public void setOnNavigationItemReselectedListener(
            @Nullable BottomNavigationView.OnNavigationItemReselectedListener listener) {
        mReselectedListener = listener;
    }

    /**
     * Returns the {@link Menu} instance associated with this bottom navigation bar.
     */
    @NonNull
    public Menu getMenu() {
        return mMenu;
    }

    /**
     * Inflate a menu resource into this navigation view.
     *
     * <p>Existing items in the menu will not be modified or removed.</p>
     *
     * @param resId ID of a menu resource to inflate
     */
    public void inflateMenu(int resId) {
        mPresenter.setUpdateSuspended(true);
        getMenuInflater().inflate(resId, mMenu);
        mPresenter.setUpdateSuspended(false);
        mPresenter.updateMenuView(true);
    }

    /**
     * @return The maximum number of items that can be shown in BottomNavigationView.
     */
    public int getMaxItemCount() {
        return BottomNavigationMenu.MAX_ITEM_COUNT;
    }

    /**
     * Returns the tint which is applied to our menu items' icons.
     *
     * @attr ref R.styleable#BottomNavigationView_itemIconTint
     * @see #setItemIconTintList(ColorStateList)
     */
    @Nullable
    public ColorStateList getItemIconTintList() {
        return mMenuView.getIconTintList();
    }

    /**
     * Set the tint which is applied to our menu items' icons.
     *
     * @param tint the tint to apply.
     * @attr ref R.styleable#BottomNavigationView_itemIconTint
     */
    public void setItemIconTintList(@Nullable ColorStateList tint) {
        mMenuView.setIconTintList(tint);
    }

    /**
     * Returns colors used for the different states (normal, selected, focused, etc.) of the menu
     * item text.
     *
     * @return the ColorStateList of colors used for the different states of the menu items text.
     * @attr ref R.styleable#BottomNavigationView_itemTextColor
     * @see #setItemTextColor(ColorStateList)
     */
    @Nullable
    public ColorStateList getItemTextColor() {
        return mMenuView.getItemTextColor();
    }

    /**
     * Set the colors to use for the different states (normal, selected, focused, etc.) of the menu
     * item text.
     *
     * @attr ref R.styleable#BottomNavigationView_itemTextColor
     * @see #getItemTextColor()
     */
    public void setItemTextColor(@Nullable ColorStateList textColor) {
        mMenuView.setItemTextColor(textColor);
    }

    /**
     * Returns the background resource of the menu items.
     *
     * @attr ref R.styleable#BottomNavigationView_itemBackground
     * @see #setItemBackgroundResource(int)
     */
    @DrawableRes
    public int getItemBackgroundResource() {
        return mMenuView.getItemBackgroundRes();
    }

    /**
     * Set the background of our menu items to the given resource.
     *
     * @param resId The identifier of the resource.
     * @attr ref R.styleable#BottomNavigationView_itemBackground
     */
    public void setItemBackgroundResource(@DrawableRes int resId) {
        mMenuView.setItemBackgroundRes(resId);
    }

    /**
     * Returns the currently selected menu item ID, or zero if there is no menu.
     *
     * @see #setSelectedItemId(int)
     */
    @IdRes
    public int getSelectedItemId() {
        return mMenuView.getSelectedItemId();
    }

    /**
     * Set the selected menu item ID. This behaves the same as tapping on an item.
     *
     * @param itemId The menu item ID. If no item has this ID, the current selection is unchanged.
     * @see #getSelectedItemId()
     */
    @SuppressLint("RestrictedApi")
    public void setSelectedItemId(@IdRes int itemId) {
        MenuItem item = mMenu.findItem(itemId);
        if (item != null) {
            if (!mMenu.performItemAction(item, mPresenter, 0)) {
                item.setChecked(true);
            }
        }
    }

    /**
     * Listener for handling selection events on bottom navigation items.
     */
    public interface OnNavigationItemSelectedListener {

        /**
         * Called when an item in the bottom navigation menu is selected.
         *
         * @param item The selected item
         * @return true to display the item as the selected item and false if the item should not
         * be selected. Consider setting non-selectable items as disabled preemptively to
         * make them appear non-interactive.
         */
        void onNavigationItemSelected(int position, int prePosition);
    }

    /**
     * Listener for handling reselection events on bottom navigation items.
     */
    public interface OnNavigationItemReselectedListener {

        /**
         * Called when the currently selected item in the bottom navigation menu is selected again.
         *
         * @param item The selected item
         */
        void onNavigationItemReselected(int curPos);
    }

    private void addCompatibilityTopDivider(Context context) {
        View divider = new View(context);
        divider.setBackgroundColor(
                ContextCompat.getColor(context, R.color.design_bottom_navigation_shadow_color));
        FrameLayout.LayoutParams dividerParams = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                getResources().getDimensionPixelSize(
                        R.dimen.design_bottom_navigation_shadow_height));
        divider.setLayoutParams(dividerParams);
        addView(divider);
    }

    @SuppressLint("RestrictedApi")
    private MenuInflater getMenuInflater() {
        if (mMenuInflater == null) {
            mMenuInflater = new SupportMenuInflater(getContext());
        }
        return mMenuInflater;
    }

    private ColorStateList createDefaultColorStateList(int baseColorThemeAttr) {
        final TypedValue value = new TypedValue();
        if (!getContext().getTheme().resolveAttribute(baseColorThemeAttr, value, true)) {
            return null;
        }
        ColorStateList baseColor = AppCompatResources.getColorStateList(
                getContext(), value.resourceId);
        if (!getContext().getTheme().resolveAttribute(
                android.support.v7.appcompat.R.attr.colorPrimary, value, true)) {
            return null;
        }
        int colorPrimary = value.data;
        int defaultColor = baseColor.getDefaultColor();
        return new ColorStateList(new int[][]{
                DISABLED_STATE_SET,
                CHECKED_STATE_SET,
                EMPTY_STATE_SET
        }, new int[]{
                baseColor.getColorForState(DISABLED_STATE_SET, defaultColor),
                colorPrimary,
                defaultColor
        });
    }

    @SuppressLint("RestrictedApi")
    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        BottomNavigationView.SavedState savedState = new SavedState(superState);
        savedState.menuPresenterState = new Bundle();
        mMenu.savePresenterStates(savedState.menuPresenterState);
        return savedState;
    }

    @SuppressLint("RestrictedApi")
    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof BottomNavigationView.SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }
        BottomNavigationView.SavedState savedState = (BottomNavigationView.SavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());
        mMenu.restorePresenterStates(savedState.menuPresenterState);
    }

    static class SavedState extends AbsSavedState {
        Bundle menuPresenterState;

        public SavedState(Parcelable superState) {
            super(superState);
        }

        public SavedState(Parcel source, ClassLoader loader) {
            super(source, loader);
            readFromParcel(source, loader);
        }

        @Override
        public void writeToParcel(@NonNull Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeBundle(menuPresenterState);
        }

        private void readFromParcel(Parcel in, ClassLoader loader) {
            menuPresenterState = in.readBundle(loader);
        }

        public static final Creator<BottomNavigationView.SavedState> CREATOR = new ClassLoaderCreator<BottomNavigationView.SavedState>() {
            @Override
            public BottomNavigationView.SavedState createFromParcel(Parcel in, ClassLoader loader) {
                return new BottomNavigationView.SavedState(in, loader);
            }

            @Override
            public BottomNavigationView.SavedState createFromParcel(Parcel in) {
                return new BottomNavigationView.SavedState(in, null);
            }

            @Override
            public BottomNavigationView.SavedState[] newArray(int size) {
                return new BottomNavigationView.SavedState[size];
            }
        };
    }
}
