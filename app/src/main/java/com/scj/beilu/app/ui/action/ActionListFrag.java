package com.scj.beilu.app.ui.action;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.mx.pro.lib.smartrefresh.layout.api.RefreshLayout;
import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseMvpFragment;
import com.scj.beilu.app.listener.OnItemClickListener;
import com.scj.beilu.app.mvp.action.ActionListPre;
import com.scj.beilu.app.mvp.action.bean.ActionInfoBean;
import com.scj.beilu.app.mvp.action.bean.ActionSecondTypeBean;
import com.scj.beilu.app.mvp.action.bean.ActionThirdTypeInfoBean;
import com.scj.beilu.app.mvp.action.model.ActionInfoImpl;
import com.scj.beilu.app.ui.action.adapter.ActionContentListAdapter;
import com.scj.beilu.app.ui.action.adapter.ActionSecondTypeListAdapter;
import com.scj.beilu.app.ui.action.adapter.ActionThirdTypeListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/3/16 16:24
 */
public class ActionListFrag extends BaseMvpFragment<ActionListPre.ActionListView, ActionListPre>
        implements ActionListPre.ActionListView, OnItemClickListener {

    private RecyclerView mRvSecondList;
    private RecyclerView mRvThirdLIst;
    private RecyclerView mRvActionList;
    private LinearLayout ll_show_pay;


    private static final String ACTION_FIRST_INDEX = "first_index";
    private static final String ACTION_FIRST_DES_ID = "des_id";
    private int mIndex;

    private ActionSecondTypeListAdapter mSecondTypeListAdapter;//二级分类
    private ActionThirdTypeListAdapter mActionThirdTypeListAdapter;//三级分类
    //    private ActionSortListAdapter mActionListAdapter;//动作列表
    private ActionContentListAdapter mActionListAdapter;

    private int mDesId;//一级分类Id
    private int mCateId;//二级分类Id
    private int mPartId;//三级分类Id

    private onErrorListener mOnErrorListener;

    private int mSecondPosition = -1;
    private int mThirdPosition = -1;
    private final ArrayList<ActionInfoBean> mActionSortInfoBeans = new ArrayList<>();
    private int mRectSize;
    private int mPosition;
    private double mPrice;

    /**
     * 用于监听一级分类和二级分类没有数据时，重试请求
     */
    public interface onErrorListener {
        void onReStartRequest();
    }

    public static ActionListFrag newInstance(int index, int desId) {

        Bundle args = new Bundle();
        args.putInt(ACTION_FIRST_INDEX, index);
        args.putInt(ACTION_FIRST_DES_ID, desId);
        ActionListFrag fragment = new ActionListFrag();
        fragment.setArguments(args);
        return fragment;
    }

    public void setOnErrorListener(onErrorListener onErrorListener) {
        mOnErrorListener = onErrorListener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mIndex = arguments.getInt(ACTION_FIRST_INDEX);
            mDesId = arguments.getInt(ACTION_FIRST_DES_ID);
        }
    }

    @Override
    public ActionListPre createPresenter() {
        return new ActionListPre(mFragmentActivity);
    }

    @Nullable
    @Override
    public int getLayout() {
        return R.layout.frag_action_list;
    }

    @Override
    public void initView() {
        super.initView();

        mRvSecondList = findViewById(R.id.rv_second_type_lis);
        mRvThirdLIst = findViewById(R.id.rv_third_type_list);
        mRvActionList = findViewById(R.id.rv_action_list);
        ll_show_pay = findViewById(R.id.ll_show_pay);

        mSecondTypeListAdapter = new ActionSecondTypeListAdapter();
        mSecondTypeListAdapter.setOnItemClickListener(this);
        mRvSecondList.setAdapter(mSecondTypeListAdapter);

        final int secondSize = getResources().getDimensionPixelSize(R.dimen.hor_divider_size_5);
        mRvSecondList.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(secondSize, secondSize, secondSize, secondSize);
            }
        });

        mActionThirdTypeListAdapter = new ActionThirdTypeListAdapter(this);
        mActionThirdTypeListAdapter.setOnItemClickListener(this);
        mRvThirdLIst.setAdapter(mActionThirdTypeListAdapter);
        mRvThirdLIst.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(secondSize, secondSize, secondSize, secondSize);
            }
        });

        mRectSize = getResources().getDimensionPixelSize(R.dimen.hor_divider_size_5);
        mRectSize += mRectSize;
        mRvActionList.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(mRectSize, mRectSize, mRectSize, mRectSize);
            }
        });

        mActionListAdapter = new ActionContentListAdapter(this);
        mActionListAdapter.setOnItemClickListener(this);
        mRvActionList.setAdapter(mActionListAdapter);
        ll_show_pay.setOnClickListener(view -> {
                    Intent intent = new Intent(mFragmentActivity, ActionInfoDetailsAct.class);
                    intent.putExtra(ActionInfoDetailsAct.EXTRA_ACTION_PRICE, mPrice);
                    startActivityForResult(intent, 1020);
                }
        );

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mRefreshLayout.autoRefresh();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        super.onRefresh(refreshLayout);
        initType();
    }

    private void initType() {
        try {
            if (ActionInfoImpl.sActionTopListTypeBean == null || ActionInfoImpl.sActionThirdTypeInfoList == null) {
                //分类无数据时重新请求分类接口
                if (mOnErrorListener != null) {
                    mOnErrorListener.onReStartRequest();
                }
                return;
            } else {
                getPresenter().getTypeList(mIndex);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCheckLoadMore(List<?> list) {
        checkIsLoadMore(list);
    }

    @Override
    public void onSecondListResult(List<ActionSecondTypeBean> secondTypeList) {
        if (mSecondPosition >= 0) {
            mSecondTypeListAdapter.notifyDataSetChanged();
            //默认二级分类Id
            mPartId = secondTypeList.get(mSecondPosition).getActionDesPartId();

            //通过个级分类Id 获取动作库列表
            getPresenter().getActionListById(mDesId, mCateId, mPartId);
        } else {
            //默认二级分类Id
            mPartId = secondTypeList.get(0).getActionDesPartId();
            //二级分类adapter
            mSecondTypeListAdapter.setSecondTypeBeans(secondTypeList);
        }
    }

    @Override
    public void onThirdListResult(List<ActionThirdTypeInfoBean> thirdTypeList) {

        if (mThirdPosition >= 0) {
            mActionThirdTypeListAdapter.notifyDataSetChanged();
            //默认三级分类Id
            mCateId = thirdTypeList.get(mThirdPosition).getActionCateId();
        } else {
            //默认三级分类Id
            mCateId = thirdTypeList.get(0).getActionCateId();
            //三级分类adapter
            mActionThirdTypeListAdapter.setThirdTypeInfoList(thirdTypeList);
        }

        //通过个级分类Id 获取动作库列表
        getPresenter().getActionListById(mDesId, mCateId, mPartId);
    }

    @Override
    public void onActionListResult(List<ActionInfoBean> actionInfoList) {
        mActionSortInfoBeans.clear();
        mActionSortInfoBeans.addAll(actionInfoList);
        mActionListAdapter.setActionInfoList(mActionSortInfoBeans);
    }


    @Override
    public void onTotalPrice(double price) {
        mPrice = price;
        ll_show_pay.setVisibility(price > 0 ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onItemClick(int position, Object entity, View view) {

        if (entity instanceof ActionSecondTypeBean) {
            mSecondPosition = position;
            getPresenter().setSecondSelector(mSecondPosition);
        } else if (entity instanceof ActionThirdTypeInfoBean) {
            mThirdPosition = position;
            getPresenter().setThirdSelector(mThirdPosition);
        } else if (entity instanceof ActionInfoBean) {
            mPosition = position;
            getPresenter().checkUserIsLogin(view.getId());
        }
    }

    @Override
    protected void userStartAction(int viewId) {
        if (viewId == R.id.iv_action_lock_transparent) {

        } else {
            Intent intent = new Intent(mFragmentActivity, ActionInfoDetailsAct.class);
            intent.putParcelableArrayListExtra(ActionInfoDetailsAct.EXTRA_ACTION_LIST, mActionSortInfoBeans);
            intent.putExtra(ActionInfoDetailsAct.EXTRA_ACTION_POSITION, mPosition);
            startActivity(intent);
        }
    }
}
