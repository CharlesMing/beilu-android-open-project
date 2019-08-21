package com.scj.beilu.app.ui.order;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.mx.pro.lib.smartrefresh.layout.api.RefreshLayout;
import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseMvpFragment;
import com.scj.beilu.app.listener.OnItemClickListener;
import com.scj.beilu.app.mvp.mine.order.OrderListPre;
import com.scj.beilu.app.mvp.order.bean.OrderInfoBean;
import com.scj.beilu.app.ui.comment.SelectPaymentDialog;
import com.scj.beilu.app.ui.order.adapter.OrderStatusListAdapter;
import com.scj.beilu.app.util.ToastUtils;
import com.tencent.mm.opensdk.modelbase.BaseResp;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/1/15 21:15
 */
public class OrderListFragment extends BaseMvpFragment<OrderListPre.OrderListView, OrderListPre>
        implements OrderListPre.OrderListView, OnItemClickListener<OrderInfoBean>, SelectPaymentDialog.onStartPay {

    private RecyclerView mRvOrderList;
    private OrderStatusListAdapter mOrderStatusListAdapter;
    private static final String TAB_INDEX = "tab_index";
    private final int ORDER_LIST = 0x01;
    private int mIndex = 0;

    private SelectPaymentDialog mSelectPaymentDialog;
    private String mOrderNo;
    private OrderInfoBean mOrderInfo;

    public static OrderListFragment newInstance(int index) {

        Bundle args = new Bundle();
        args.putInt(TAB_INDEX, index);
        OrderListFragment fragment = new OrderListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mIndex = arguments.getInt(TAB_INDEX);
        }
        EventBus.getDefault().register(this);
    }

    @Override
    public OrderListPre createPresenter() {
        return new OrderListPre(mFragmentActivity);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mRefreshLayout.autoRefresh();
    }

    @Nullable
    @Override
    public int getLayout() {
        return R.layout.frag_order_list;
    }

    @Override
    public void initView() {
        super.initView();

        mRvOrderList = findViewById(R.id.rv_order_list);
        int size = 0;
        if (isAdded()) {
            size = getResources().getDimensionPixelSize(R.dimen.padding_15);
        }
        int finalSize = size;
        mRvOrderList.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(0, 0, 0, finalSize);
            }
        });
        mOrderStatusListAdapter = new OrderStatusListAdapter(this);
        mOrderStatusListAdapter.setOnItemClickListener(this);
        mRvOrderList.setAdapter(mOrderStatusListAdapter);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        super.onRefresh(refreshLayout);
        getPresenter().getOrderStatusList(mIndex, mCurrentPage);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        super.onLoadMore(refreshLayout);
        getPresenter().getOrderStatusList(mIndex, mCurrentPage);
    }

    @Override
    public void onCheckLoadMore(List<?> list) {
        checkIsLoadMore(list);
    }

    @Override
    public void onOrderList(List<OrderInfoBean> orderInfoBeanList) {
        mOrderStatusListAdapter.setOrderInfoList(orderInfoBeanList);
    }

    @Override
    public void onAliPaySuccess() {
        ToastUtils.showToast(mFragmentActivity, "支付成功");
        if (mOrderInfo == null) return;
        Intent intent = new Intent(mFragmentActivity, OrderInfoAct.class);
        intent.putExtra(OrderInfoAct.PAY_PRICE, String.valueOf(mOrderInfo.getTotalPrice()));
        intent.putExtra(OrderInfoAct.ORDER_NO, mOrderNo);
        intent.putExtra(OrderInfoAct.LOAD_PAGE, OrderInfoAct.PAY_SUCCESS);
        startActivity(intent);
    }

    @Override
    public void onOrderNoResult(String orderNo) {
        mOrderNo = orderNo;
    }

    @Override
    public void onItemClick(int position, OrderInfoBean entity, View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.ll_order_info_content:
                intent = new Intent(mFragmentActivity, OrderInfoAct.class);
                intent.putExtra(OrderInfoAct.ORDER_NO, entity.getOrderNo());
                intent.putExtra(OrderInfoAct.LOAD_PAGE, OrderInfoAct.ORDER_STATUS);
                startActivityForResult(intent, ORDER_LIST);
                break;
            case R.id.btn_order_info_click:

                switch (entity.getOrderStatusId()) {
                    case 1:
                        if (mSelectPaymentDialog == null) {
                            mSelectPaymentDialog = SelectPaymentDialog.newInstance(String.valueOf(entity.getTotalPrice()));
                            mSelectPaymentDialog.setOnStartPay(this);
                        }
                        mOrderInfo = entity;
                        mSelectPaymentDialog.show(getChildFragmentManager(), mSelectPaymentDialog.getClass().getName());
                        break;
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 7:
                    case 8:
                        intent = new Intent(mFragmentActivity, OrderInfoAct.class);
                        intent.putExtra(OrderInfoAct.ORDER_NO, entity.getOrderNo());
                        intent.putExtra(OrderInfoAct.LOAD_PAGE, OrderInfoAct.ORDER_LOGISTICS);
                        startActivityForResult(intent, ORDER_LIST);
                        break;
                    case 6:
                    case 9:
                        intent = new Intent(mFragmentActivity, OrderInfoAct.class);
                        intent.putExtra(OrderInfoAct.ORDER_NO, entity.getOrderNo());
                        intent.putExtra(OrderInfoAct.LOAD_PAGE, OrderInfoAct.ORDER_STATUS);
                        startActivityForResult(intent, ORDER_LIST);
                        break;

                }

                break;
        }
    }

    @Subscribe
    public void payResult(BaseResp resp) {
        if (resp.errCode == 0) {
            onAliPaySuccess();
        } else {
            ToastUtils.showToast(mFragmentActivity, "支付失败");
        }
    }

    @Override
    public void startPay(boolean mSelectPayment) {
        if (mOrderInfo == null) return;
        getPresenter().startPay(mOrderInfo.getOrderNo(), mOrderInfo.getProductId(), mOrderInfo.getProductCount(),
                mSelectPayment ? 1 : 2, mOrderInfo.getProductName(),
                mOrderInfo.getAddrId(), mOrderInfo.getDescription());
        if (mSelectPaymentDialog != null) {
            mSelectPaymentDialog.dismiss();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mRefreshLayout.autoRefresh();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
