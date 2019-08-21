package com.scj.beilu.app.ui.order;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.scj.beilu.app.GlideApp;
import com.scj.beilu.app.GlideRequest;
import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseMvpFragment;
import com.scj.beilu.app.mvp.mine.address.bean.AddressInfoBean;
import com.scj.beilu.app.mvp.mine.address.bean.AddressInfoResultBean;
import com.scj.beilu.app.mvp.order.OrderInfoConfirmationPre;
import com.scj.beilu.app.mvp.store.bean.GoodsInfoBean;
import com.scj.beilu.app.ui.comment.SelectPaymentDialog;
import com.scj.beilu.app.ui.mine.address.AddressAddAct;
import com.scj.beilu.app.ui.mine.address.AddressManagerAct;
import com.scj.beilu.app.util.ToastUtils;
import com.tencent.mm.opensdk.modelbase.BaseResp;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * @author Mingxun
 * @time on 2019/4/2 22:58
 * 订单确认
 */
public class OrderInfoConfirmationFrag extends BaseMvpFragment<OrderInfoConfirmationPre.OrderInfoConfirmationView, OrderInfoConfirmationPre>
        implements OrderInfoConfirmationPre.OrderInfoConfirmationView,
        View.OnClickListener,
        SelectPaymentDialog.onStartPay {

    private FrameLayout mFlAddAddress;
    private ConstraintLayout mClAddressInfo;
    private TextView mTvConsignee, mTvConsigneePhone, mTvConsigneeAddress;
    private ImageView mIvGoodsImg;
    private TextView mTvGoodsName, mTvGoodsSpecification, mTvGoodsPrice, mTvPayPrice, mTvGoodsNum;
    private TextView mTvConfirmation;

    private static final String GOODS_INFO = "goods_info";
    private final int ADD_REQUEST_CODE = 0x100;
    private final int ADDRESS_SELECT_CODE = 0x101;
    private GoodsInfoBean mGoodsInfoBean;
    private int mAddressId = -1;
    private String mRMB;
    private String mOrderNo;
    private SelectPaymentDialog mSelectPaymentDialog;


    public static OrderInfoConfirmationFrag newInstance(GoodsInfoBean goodsInfo) {

        Bundle args = new Bundle();
        args.putParcelable(GOODS_INFO, goodsInfo);
        OrderInfoConfirmationFrag fragment = new OrderInfoConfirmationFrag();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mGoodsInfoBean = arguments.getParcelable(GOODS_INFO);
        }
        EventBus.getDefault().register(this);
    }

    @Override
    public OrderInfoConfirmationPre createPresenter() {
        return new OrderInfoConfirmationPre(mFragmentActivity);
    }

    @Nullable
    @Override
    public int getLayout() {
        return R.layout.frag_order_confirmation;
    }

    @Override
    public void initView() {
        super.initView();
        mFlAddAddress = findViewById(R.id.fl_order_info_add_address);
        mClAddressInfo = findViewById(R.id.cl_order_info_address_info);
        mTvConsignee = findViewById(R.id.tv_order_info_consignee);
        mTvConsigneePhone = findViewById(R.id.tv_order_info_consignee_phone);
        mTvConsigneeAddress = findViewById(R.id.tv_order_info_consignee_address);

        mIvGoodsImg = findViewById(R.id.iv_order_info_goods_img);
        mTvGoodsName = findViewById(R.id.tv_order_info_goods_name);
        mTvGoodsSpecification = findViewById(R.id.tv_order_info_goods_specification);
        mTvGoodsPrice = findViewById(R.id.tv_order_info_goods_price);
        mTvPayPrice = findViewById(R.id.tv_order_info_price);
        mTvGoodsNum = findViewById(R.id.tv_goods_num);
        mTvConfirmation = findViewById(R.id.tv_order_info_confirmation);

        mFlAddAddress.setOnClickListener(this);
        mClAddressInfo.setOnClickListener(this);
        mTvConfirmation.setOnClickListener(this);

        if (isAdded()) {
            mRMB = getString(R.string.txt_rmb);
        }

        initData();
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        getPresenter().getAddressInfo();
    }

    @Override
    public void onAddressInfoResult(AddressInfoResultBean addressInfo) {

        try {
            if (addressInfo == null) {
                throw new Exception();
            }
            AddressInfoBean info = addressInfo.getUserAddr();

            mAddressId = info.getAddrId();

            mFlAddAddress.setVisibility(View.GONE);
            mClAddressInfo.setVisibility(View.VISIBLE);

            mTvConsignee.setText(String.format(getString(R.string.txt_consignee), info.getUserAddrName()));
            mTvConsigneePhone.setText(info.getUserAddrTel());
            StringBuilder address = new StringBuilder();
            address.append(info.getUserAddrProvince())
                    .append("\t")
                    .append(info.getUserAddrCity())
                    .append("\t")
                    .append(info.getUserAddrDetail());
            mTvConsigneeAddress.setText(String.format(getString(R.string.txt_consignee_address), address));
        } catch (Exception e) {
            mClAddressInfo.setVisibility(View.GONE);
            mFlAddAddress.setVisibility(View.VISIBLE);
        }
    }

    private void initData() {
        if (mGoodsInfoBean == null) return;
        try {
            GlideRequest<Drawable> OriginalGlideRequest = GlideApp.with(this).asDrawable().optionalFitCenter();
            GlideRequest<Drawable> ThumbnailGlideRequest = GlideApp.with(this).asDrawable().optionalFitCenter();

            OriginalGlideRequest.load(mGoodsInfoBean.getProductPicOriginalAddr())
                    .thumbnail(ThumbnailGlideRequest.load(mGoodsInfoBean.getProductPicCompressionAddr()))
                    .into(mIvGoodsImg);

            double price = mGoodsInfoBean.getProductDiscountPrice();
            int count = mGoodsInfoBean.getGoodsCount();
            mTvGoodsName.setText(mGoodsInfoBean.getProductName());
            mTvGoodsSpecification.setText(mGoodsInfoBean.getGoodsSpecification());
            mTvGoodsPrice.setText(String.format(mRMB, price));
            mTvGoodsNum.setText(String.format(getString(R.string.txt_goods_num), count));

            DecimalFormat df2 = new DecimalFormat("#.##");
            BigDecimal unitPrice = new BigDecimal(price);
            BigDecimal num = new BigDecimal(count);
            BigDecimal resultPrice = unitPrice.multiply(num);
            String format = df2.format(resultPrice);
            mTvPayPrice.setText(String.format(mRMB, format));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.fl_order_info_add_address:
                intent = new Intent(mFragmentActivity, AddressAddAct.class);
                startActivityForResult(intent, ADD_REQUEST_CODE);
                break;
            case R.id.cl_order_info_address_info:
                intent = new Intent(mFragmentActivity, AddressManagerAct.class);
                intent.putExtra("address", "address");
                startActivityForResult(intent, ADDRESS_SELECT_CODE);
                break;
            case R.id.tv_order_info_confirmation:
                if (mAddressId == -1) {
                    ToastUtils.showToast(mFragmentActivity, "请填写收货信息");
                    return;
                }
                if (mSelectPaymentDialog == null) {
                    mSelectPaymentDialog = SelectPaymentDialog.newInstance(mTvPayPrice.getText().toString());
                    mSelectPaymentDialog.setOnStartPay(this);
                }
                mSelectPaymentDialog.show(getChildFragmentManager(), mSelectPaymentDialog.getClass().getName());
                break;
        }
    }

    @Override
    public void startPay(boolean mSelectPayment) {
        getPresenter().startPay(mGoodsInfoBean.getProductId(), mGoodsInfoBean.getGoodsCount(),
                mSelectPayment ? 1 : 2, mGoodsInfoBean.getProductName(),
                mAddressId, mGoodsInfoBean.getGoodsSpecification());
        if (mSelectPaymentDialog != null) {
            mSelectPaymentDialog.dismiss();
        }

    }


    @Override
    public void onAliPaySuccess() {
        ToastUtils.showToast(mFragmentActivity, "支付成功");
        Intent intent = new Intent(mFragmentActivity, OrderInfoAct.class);
        intent.putExtra(OrderInfoAct.PAY_PRICE, mTvPayPrice.getText().toString());
        intent.putExtra(OrderInfoAct.ORDER_NO, mOrderNo);
        intent.putExtra(OrderInfoAct.LOAD_PAGE, OrderInfoAct.PAY_SUCCESS);
        startActivity(intent);
        mFragmentActivity.finish();
    }

    @Override
    public void onOrderNoResult(String orderNo) {
        mOrderNo = orderNo;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_REQUEST_CODE && resultCode == RESULT_OK) {
            getPresenter().getAddressInfo();
        } else if (requestCode == ADDRESS_SELECT_CODE && resultCode == RESULT_OK && data != null) {
            try {
                AddressInfoBean info = (AddressInfoBean) data.getSerializableExtra(AddressManagerAct.EXTRA_ADDRESS_INFO);
                mAddressId = info.getAddrId();
                StringBuilder address = new StringBuilder();
                address.append(info.getUserAddrProvince())
                        .append("\t")
                        .append(info.getUserAddrCity())
                        .append("\t")
                        .append(info.getUserAddrDetail());
                mTvConsignee.setText(String.format(getString(R.string.txt_consignee), info.getUserAddrName()));
                mTvConsigneePhone.setText(info.getUserAddrTel());
                mTvConsigneeAddress.setText(String.format(getString(R.string.txt_consignee_address), address));
            } catch (Exception e) {
                e.printStackTrace();
            }
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
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


}
