package com.scj.beilu.app.ui.order;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseMvpFragment;
import com.scj.beilu.app.mvp.order.OrderInfoLogisticsPre;
import com.scj.beilu.app.mvp.order.bean.OrderLogisticsInfoResultBean;
import com.scj.beilu.app.ui.order.adapter.OrderLogisticsListAdapter;
import com.scj.beilu.app.util.ToastUtils;


/**
 * @author Mingxun
 * @time on 2019/4/2 23:02
 */
public class OrderInfoLogisticsFrag extends BaseMvpFragment<OrderInfoLogisticsPre.OrderInfoLogisticsView, OrderInfoLogisticsPre>
        implements OrderInfoLogisticsPre.OrderInfoLogisticsView, View.OnClickListener {

    private TextView mTvLogisticsName, mTvLogisticsNo, mTvLogisticsCopy;
    private RecyclerView mRvLogisticsList;
    private static final String ORDER_NO = "order_no";

    private String mOrderNo;
    private ClipboardManager mClipboardManager;
    private OrderLogisticsListAdapter mOrderLogisticsListAdapter;

    public static OrderInfoLogisticsFrag newInstance(String orderNo) {

        Bundle args = new Bundle();
        args.putString(ORDER_NO, orderNo);
        OrderInfoLogisticsFrag fragment = new OrderInfoLogisticsFrag();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        if (arguments != null) {
            mOrderNo = arguments.getString(ORDER_NO);
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    public OrderInfoLogisticsPre createPresenter() {
        return new OrderInfoLogisticsPre(mFragmentActivity, mOrderNo);
    }

    @Nullable
    @Override
    public int getLayout() {
        return R.layout.frag_logistics_info;
    }

    @Override
    public void initView() {
        super.initView();
        mTvLogisticsNo = findViewById(R.id.tv_logistics_no);
        mTvLogisticsName = findViewById(R.id.tv_logistics_name);
        mTvLogisticsCopy = findViewById(R.id.tv_logistics_copy);
        mRvLogisticsList = findViewById(R.id.rv_logistics_list);

        mTvLogisticsCopy.setOnClickListener(this);

        mOrderLogisticsListAdapter = new OrderLogisticsListAdapter();
        mRvLogisticsList.setAdapter(mOrderLogisticsListAdapter);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        getPresenter().getLogisticsList();
    }

    @Override
    public void onLogisticsList(OrderLogisticsInfoResultBean logisticsInfo) {
        try {
            OrderLogisticsInfoResultBean.ObjectBean info = logisticsInfo.getObject();
            mTvLogisticsName.setText(info.getCompany());
            mTvLogisticsNo.setText(info.getNo());
            mOrderLogisticsListAdapter.setLogisticsInfoBeans(info.getList());
        } catch (Exception e) {
            findViewById(R.id.card_logistics_list).setVisibility(View.GONE);
            mTvLogisticsName.setText("暂无物流信息");
            mTvLogisticsNo.setText(mTvLogisticsName.getText());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_logistics_copy:
                if (mClipboardManager == null) {
                    mClipboardManager = (ClipboardManager) mFragmentActivity.getSystemService(Context.CLIPBOARD_SERVICE);
                }
                ClipData clipData = ClipData.newPlainText(mOrderNo, mOrderNo);
                mClipboardManager.setPrimaryClip(clipData);
                ToastUtils.showToast(mFragmentActivity, "已复制到粘贴板上");
                break;
        }
    }
}
