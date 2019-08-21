package com.scj.beilu.app.ui.store;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.mx.pro.lib.mvp.MvpDialogFragment;
import com.scj.beilu.app.GlideApp;
import com.scj.beilu.app.GlideRequest;
import com.scj.beilu.app.R;
import com.scj.beilu.app.listener.OnGroupItemClickListener;
import com.scj.beilu.app.mvp.store.GoodsSpecificationPre;
import com.scj.beilu.app.mvp.store.bean.GoodsSpecificationInfoBean;
import com.scj.beilu.app.mvp.store.bean.GoodsSpecificationListBean;
import com.scj.beilu.app.ui.dialog.MyBottomSheetDialog;
import com.scj.beilu.app.ui.store.adapter.GoodsSpecificationGroupListAdapter;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/3/28 21:02
 */
public class GoodsSpecificationDialog extends MvpDialogFragment<GoodsSpecificationPre.GoodsSpecificationView, GoodsSpecificationPre>
        implements GoodsSpecificationPre.GoodsSpecificationView, OnGroupItemClickListener<GoodsSpecificationInfoBean>, View.OnClickListener {

    private RecyclerView mRvSpecificationGroupList;
    private TextView mTvSpecificationVal;
    private TextView mTvGoodsPrice;
    private TextView mTvGoodsMinus, mTvGoodsNum, mTvGoodsAdd;
    private ImageButton mBtnClose;
    private TextView mTvAddToCart;
    private ImageView mIvGoodsImg;

    private int mProductId;
    private int mGoodsNum = 1;
    private String mGoodsPrice;
    private String mRMB;
    private String mSpecificationVal;
    private String mPic1, mPic2;

    private static final String PRODUCT_ID = "product_id";
    private static final String GOODS_PRICE = "price";
    private static final String GOODS_NUM = "goods_num";
    private static final String GOODS_IMG_OR = "goods_original";
    private static final String GOODS_IMG_TH = "goods_thumbnail";
    private GoodsSpecificationGroupListAdapter mGoodsSpecificationGroupListAdapter;
    private onAddCartListener mOnAddCartListener;

    public interface onAddCartListener {
        void onEditCart(int goodsNum, String specification);
    }

    public void setOnAddCartListener(onAddCartListener onAddCartListener) {
        mOnAddCartListener = onAddCartListener;
    }

    public static GoodsSpecificationDialog newInstance(int productId, String price, int goodsNum, String pic1, String pic2) {
        Bundle args = new Bundle();
        args.putInt(PRODUCT_ID, productId);
        args.putString(GOODS_PRICE, price);
        args.putInt(GOODS_NUM, goodsNum);
        args.putString(GOODS_IMG_OR, pic1);
        args.putString(GOODS_IMG_TH, pic2);
        GoodsSpecificationDialog fragment = new GoodsSpecificationDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new MyBottomSheetDialog(getContext());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mProductId = arguments.getInt(PRODUCT_ID);
            mGoodsPrice = arguments.getString(GOODS_PRICE);
            mGoodsNum = arguments.getInt(GOODS_NUM, mGoodsNum);
            mPic1 = arguments.getString(GOODS_IMG_OR);
            mPic2 = arguments.getString(GOODS_IMG_TH);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_goods_specification, container);
        initView(view);
        return view;
    }

    @SuppressLint("CheckResult")
    private void initView(View view) {
        mIvGoodsImg = view.findViewById(R.id.iv_goods_img);
        mRvSpecificationGroupList = view.findViewById(R.id.rv_goods_specification_group_list);
        mTvSpecificationVal = view.findViewById(R.id.tv_goods_specification_val);
        mTvGoodsMinus = view.findViewById(R.id.tv_goods_minus);
        mTvGoodsNum = view.findViewById(R.id.tv_goods_num);
        mTvGoodsAdd = view.findViewById(R.id.tv_goods_add);
        mBtnClose = view.findViewById(R.id.btn_goods_close);
        mTvGoodsPrice = view.findViewById(R.id.tv_goods_price);
        mTvAddToCart = view.findViewById(R.id.tv_goods_add_to_car);

        mGoodsSpecificationGroupListAdapter = new GoodsSpecificationGroupListAdapter();
        mGoodsSpecificationGroupListAdapter.setGroupItemClickListener(this);
        mRvSpecificationGroupList.setAdapter(mGoodsSpecificationGroupListAdapter);

        mBtnClose.setOnClickListener(this);
        mTvGoodsMinus.setOnClickListener(this);
        mTvGoodsAdd.setOnClickListener(this);
        mTvAddToCart.setOnClickListener(this);

        if (isAdded()) {
            mRMB = getResources().getString(R.string.txt_rmb);
        }

        mTvGoodsPrice.setText(String.format(mRMB, mGoodsPrice));
        mTvGoodsNum.setText(String.valueOf(mGoodsNum));

        GlideRequest<Drawable> original =
                GlideApp.with(this).asDrawable().optionalFitCenter();
        GlideRequest<Drawable> thumbnail =
                GlideApp.with(this).asDrawable().optionalFitCenter();

        original.load(mPic1)
                .thumbnail(thumbnail.load(mPic2))
                .into(mIvGoodsImg);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getPresenter().getGoodsSpecification(mProductId);
    }

    @Override
    public GoodsSpecificationPre createPresenter() {
        return new GoodsSpecificationPre(mContext);
    }

    @Override
    public void showLoading(int loading, boolean isShow) {

    }

    @Override
    public void showError(int errorCode, String throwableContent) {

    }

    @Override
    public void onSpecificationList(List<GoodsSpecificationListBean> specificationListBeans) {
        mGoodsSpecificationGroupListAdapter.setGoodsSpecificationListList(specificationListBeans);

    }

    @Override
    public void onSelectResult(int groupPosition) {
        mGoodsSpecificationGroupListAdapter.notifyItemChanged(groupPosition);
    }

    @Override
    public void onSelectValResult(StringBuilder result) {

        if (result.length() == 0) {
            mSpecificationVal = "无";
        } else {
            mSpecificationVal = result.toString();
        }
        mTvSpecificationVal.setText("已选择 " + mSpecificationVal);
    }

    @Override
    public void onItemClick(int groupPosition, int childPosition, GoodsSpecificationInfoBean object, View view) {
        getPresenter().setSpecificationSelector(groupPosition, childPosition);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_goods_minus:
                setGoodsNum(false);
                break;
            case R.id.tv_goods_add:
                setGoodsNum(true);
                break;
            case R.id.btn_goods_close:
                dismiss();
                break;
            case R.id.tv_goods_add_to_car:
                if (mOnAddCartListener != null) {
                    mOnAddCartListener.onEditCart(mGoodsNum, mSpecificationVal);
                }
                break;
        }
    }

    private void setGoodsNum(boolean isAdd) {
        if (isAdd) {
            mGoodsNum++;
        } else {
            if (mGoodsNum > 1) {
                mGoodsNum--;
            } else {
                mGoodsNum = 1;
            }
        }
        mTvGoodsNum.setText(String.valueOf(mGoodsNum));
    }

}
