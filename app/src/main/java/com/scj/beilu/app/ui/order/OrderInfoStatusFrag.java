package com.scj.beilu.app.ui.order;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.scj.beilu.app.GlideApp;
import com.scj.beilu.app.GlideRequest;
import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseMvpFragment;
import com.scj.beilu.app.mvp.common.bean.ResultMsgBean;
import com.scj.beilu.app.mvp.order.OrderInfoStatusPre;
import com.scj.beilu.app.mvp.order.bean.OrderInfoBean;
import com.scj.beilu.app.mvp.order.bean.OrderLogisticsInfoBean;
import com.scj.beilu.app.mvp.order.bean.OrderLogisticsInfoResultBean;
import com.scj.beilu.app.ui.comment.SelectPaymentDialog;
import com.scj.beilu.app.util.ToastUtils;
import com.tencent.mm.opensdk.modelbase.BaseResp;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/4/2 22:59"
 * 订单状态
 */
public class OrderInfoStatusFrag extends BaseMvpFragment<OrderInfoStatusPre.OrderInfoStatusView, OrderInfoStatusPre>
        implements OrderInfoStatusPre.OrderInfoStatusView, View.OnClickListener, SelectPaymentDialog.onStartPay, ApplyRefundDialog.onRefundContent {


    private TextView tv_order_info_status_name, tv_order_info_count_down, tv_order_info_tip;
    private ConstraintLayout cl_order_info_address_info;
    private TextView tv_order_info_logistics_val, tv_order_logistics_time;
    private TextView tv_order_info_consignee, tv_order_info_consignee_phone, tv_order_info_consignee_address;
    private ImageView iv_order_info_goods_img;
    private TextView tv_order_info_goods_name, tv_goods_num, tv_order_info_goods_specification, tv_order_info_goods_price;
    private TextView tv_order_info_price, tv_order_no, tv_order_create_date, tv_order_payment_type;
    private TextView tv_order_info_app_refund;
    private TextView tv_order_info_not_buy, tv_order_info_start_pay;
    private LinearLayout ll_pay_status;
    private TextView tv_order_info_status_not_send;

    private String mOrderNo;
    private static final String ORDER_NO = "order_no";

    private SelectPaymentDialog mSelectPaymentDialog;
    private OrderInfoBean mOrderInfo;

    public static OrderInfoStatusFrag newInstance(String orderNo) {

        Bundle args = new Bundle();
        args.putString(ORDER_NO, orderNo);
        OrderInfoStatusFrag fragment = new OrderInfoStatusFrag();
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
        EventBus.getDefault().register(this);
    }

    @Override
    public OrderInfoStatusPre createPresenter() {
        return new OrderInfoStatusPre(mFragmentActivity, mOrderNo);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        getPresenter().getOrderInfo();
    }

    @Nullable
    @Override
    public int getLayout() {
        return R.layout.frag_order_status;
    }

    @Override
    public void initView() {
        super.initView();

        tv_order_info_status_name = findViewById(R.id.tv_order_info_status_name);
        tv_order_info_count_down = findViewById(R.id.tv_order_info_count_down);
        tv_order_info_tip = findViewById(R.id.tv_order_info_tip);

        tv_order_info_logistics_val = findViewById(R.id.tv_order_info_logistics_val);
        tv_order_logistics_time = findViewById(R.id.tv_order_logistics_time);

        cl_order_info_address_info = findViewById(R.id.cl_order_info_address_info);
        tv_order_info_consignee = findViewById(R.id.tv_order_info_consignee);
        tv_order_info_consignee_phone = findViewById(R.id.tv_order_info_consignee_phone);
        tv_order_info_consignee_address = findViewById(R.id.tv_order_info_consignee_address);

        iv_order_info_goods_img = findViewById(R.id.iv_order_info_goods_img);
        tv_order_info_goods_name = findViewById(R.id.tv_order_info_goods_name);
        tv_goods_num = findViewById(R.id.tv_goods_num);
        tv_order_info_goods_specification = findViewById(R.id.tv_order_info_goods_specification);
        tv_order_info_goods_price = findViewById(R.id.tv_order_info_goods_price);

        tv_order_info_price = findViewById(R.id.tv_order_info_price);
        tv_order_no = findViewById(R.id.tv_order_no);
        tv_order_create_date = findViewById(R.id.tv_order_create_date);
        tv_order_payment_type = findViewById(R.id.tv_order_payment_type);

        tv_order_info_app_refund = findViewById(R.id.tv_order_info_app_refund);
        tv_order_info_status_not_send = findViewById(R.id.tv_order_info_status_not_send);

        ll_pay_status = findViewById(R.id.ll_pay_status);
        tv_order_info_not_buy = findViewById(R.id.tv_order_info_not_buy);
        tv_order_info_start_pay = findViewById(R.id.tv_order_info_start_pay);

        cl_order_info_address_info.setOnClickListener(this);
        tv_order_info_app_refund.setOnClickListener(this);
        tv_order_info_not_buy.setOnClickListener(this);
        tv_order_info_start_pay.setOnClickListener(this);
    }

    @Override
    public void onOrderInfoResult(OrderInfoBean orderInfo) {
        try {
            mOrderInfo = orderInfo;

            int payStatus = orderInfo.getOrderStatusId();
            tv_order_info_count_down.setVisibility(View.GONE);
            tv_order_info_tip.setVisibility(View.GONE);
            tv_order_info_status_name.setText(orderStatus(payStatus));

            tv_order_info_consignee.setText(String.format(getString(R.string.txt_consignee), orderInfo.getUserName()));
            tv_order_info_consignee_phone.setText(orderInfo.getUserTel());
            tv_order_info_consignee_address.setText(String.format(getString(R.string.txt_consignee_address), orderInfo.getUserDetailAddr()));

            GlideRequest<Drawable> Original = GlideApp.with(this).asDrawable().optionalCenterCrop();
            GlideRequest<Drawable> thumbnail = GlideApp.with(this).asDrawable().optionalCenterCrop();

            Original.load(orderInfo.getProductPicOriginalAddr())
                    .thumbnail(thumbnail.load(orderInfo.getProductPicCompressionAddr()))
                    .into(iv_order_info_goods_img);
            tv_order_info_goods_name.setText(orderInfo.getProductName());
            tv_goods_num.setText("x" + orderInfo.getProductCount());
            tv_order_info_goods_specification.setText(orderInfo.getDescription());
            tv_order_info_goods_price.setText(String.format(getString(R.string.txt_rmb), "" + orderInfo.getProductDiscountPrice()));
            tv_order_info_price.setText(String.valueOf(orderInfo.getTotalPrice()));
            tv_order_no.setText("订单号：" + orderInfo.getOrderNo());
            String payType = "支付方式：" + (orderInfo.getOrderPayId() == 1 ? "微信" : "支付宝");
            tv_order_payment_type.setText(payType);
            String orderDate = orderInfo.getOrderDate();
            tv_order_create_date.setText("创建时间：" + orderDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String orderStatus(int orderStatus) {
        ll_pay_status.setVisibility(View.GONE);
        tv_order_info_app_refund.setVisibility(View.GONE);
        switch (orderStatus) {
            case 1:
                ll_pay_status.setVisibility(View.VISIBLE);
                tv_order_info_app_refund.setVisibility(View.GONE);
                return "待付款";
            case 2:
                ll_pay_status.setVisibility(View.GONE);
                tv_order_info_app_refund.setVisibility(View.VISIBLE);
                return "支付成功";
            case 3:
                ll_pay_status.setVisibility(View.GONE);
                tv_order_info_app_refund.setVisibility(View.VISIBLE);
                cl_order_info_address_info.setVisibility(View.GONE);
                tv_order_info_status_not_send.setVisibility(View.VISIBLE);
                return "待发货";
            case 4:
                ll_pay_status.setVisibility(View.GONE);
                tv_order_info_app_refund.setVisibility(View.VISIBLE);
                tv_order_info_app_refund.setTag(orderStatus);
                tv_order_info_app_refund.setBackgroundColor(ContextCompat.getColor(mFragmentActivity, R.color.colorF2F2F2));
                return "已发货";
            case 6:
            case 7:
                tv_order_info_status_not_send.setVisibility(View.GONE);
                return "退款申请中";
            case 8:
                return "同意退款";
            case 9:
                return "已完成";
            default:
                return "";
        }
    }

    @Override
    public void onLogisticsInfo(OrderLogisticsInfoResultBean logisticsInfo) {
        try {
            if (logisticsInfo.getObject() == null) return;
            cl_order_info_address_info.setVisibility(View.VISIBLE);
            List<OrderLogisticsInfoBean> list = logisticsInfo.getObject().getList();
            OrderLogisticsInfoBean orderLogisticsInfo = list.get(list.size() - 1);
            tv_order_info_logistics_val.setText(orderLogisticsInfo.getRemark());
            tv_order_logistics_time.setText(orderLogisticsInfo.getDatetime());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.cl_order_info_address_info:
                intent = new Intent(mFragmentActivity, OrderInfoAct.class);
                intent.putExtra(OrderInfoAct.ORDER_NO, mOrderNo);
                intent.putExtra(OrderInfoAct.LOAD_PAGE, OrderInfoAct.ORDER_LOGISTICS);
                startActivity(intent);
                break;
            case R.id.tv_order_info_app_refund://申请退款:
                Object tag = tv_order_info_app_refund.getTag();
                if (tag != null && tag.equals(4)) {
                    ToastUtils.showToast(mFragmentActivity, "商品正在运输中，不能申请退款哦~");
                } else {
                    ApplyRefundDialog dialog = new ApplyRefundDialog();
                    dialog.setOnRefundContent(this);
                    dialog.show(getChildFragmentManager(), dialog.getClass().getName());
                }
                break;
            case R.id.tv_order_info_not_buy://取消订单
                new AlertDialog.Builder(mFragmentActivity)
                        .setPositiveButton(R.string.button_sure, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(@NonNull DialogInterface dialog, int which) {
                                getPresenter().setCancelOrder();
                            }
                        })
                        .setNegativeButton(R.string.button_cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(@NonNull DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .setCancelable(false)
                        .setMessage("是否确认不支付此订单？")
                        .show();
                break;
            case R.id.tv_order_info_start_pay:
                if (mOrderInfo == null) return;
                if (mSelectPaymentDialog == null) {
                    mSelectPaymentDialog = SelectPaymentDialog.newInstance(String.valueOf(mOrderInfo.getTotalPrice()));
                    mSelectPaymentDialog.setOnStartPay(this);
                }
                mSelectPaymentDialog.show(getChildFragmentManager(), mSelectPaymentDialog.getClass().getName());
                break;
        }
    }

    @Override
    public void startPay(boolean mSelectPayment) {
        try {
            if (mOrderInfo == null) return;
            getPresenter().startPay(mOrderInfo.getOrderNo(),
                    mOrderInfo.getProductId(), mOrderInfo.getProductCount(),
                    mSelectPayment ? 1 : 2, mOrderInfo.getProductName(),
                    mOrderInfo.getAddrId(), mOrderInfo.getDescription());
            if (mSelectPaymentDialog != null) {
                mSelectPaymentDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onAliPaySuccess() {
        ToastUtils.showToast(mFragmentActivity, "支付成功");
        if (mOrderInfo == null) return;
        Intent intent = new Intent(mFragmentActivity, OrderInfoAct.class);
        intent.putExtra(OrderInfoAct.PAY_PRICE, String.valueOf(mOrderInfo.getTotalPrice()));
        intent.putExtra(OrderInfoAct.ORDER_NO, mOrderNo);
        intent.putExtra(OrderInfoAct.LOAD_PAGE, OrderInfoAct.PAY_SUCCESS);
        startActivityForResult(intent, 0x01);
        mFragmentActivity.finish();
    }

    @Override
    public void onOrderNoResult(String orderNo) {
        mOrderNo = orderNo;
    }

    @Override
    public void onCancelResult(ResultMsgBean result) {
        ToastUtils.showToast(mFragmentActivity, result.getResult());
        ll_pay_status.setVisibility(View.GONE);
        tv_order_info_status_not_send.setVisibility(View.VISIBLE);
        tv_order_info_status_not_send.setEnabled(false);
        tv_order_info_status_not_send.setText("订单已失效");
        mFragmentActivity.setResult(RESULT_OK);
        mFragmentActivity.finish();
    }

    @Override
    public void onRefundResult(ResultMsgBean result) {
        ToastUtils.showToast(mFragmentActivity, result.getResult());
        mFragmentActivity.setResult(RESULT_OK);
        getPresenter().getOrderInfo();
    }

    @Override
    public void onContent(String content) {
        getPresenter().applyRefund(content);
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
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
