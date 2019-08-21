package com.scj.beilu.app.ui.store;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseMvpFragment;
import com.scj.beilu.app.listener.OnItemClickListener;
import com.scj.beilu.app.mvp.store.ShoppingCartInfoPre;
import com.scj.beilu.app.mvp.store.entity.GoodsShoppingCarInfoBean;
import com.scj.beilu.app.ui.store.adapter.ShoppingCartListAdapter;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/4/1 13:24
 */
public class ShoppingCarFrag extends BaseMvpFragment<ShoppingCartInfoPre.ShoppingCartInfoView, ShoppingCartInfoPre>
        implements ShoppingCartInfoPre.ShoppingCartInfoView, OnItemClickListener<GoodsShoppingCarInfoBean>, View.OnClickListener {

    private TextView mTvSelect;
    private TextView mTvTotalPrice;
    private TextView mTvStartPay;
    private TextView mTvStartDel;

    private ShoppingCartListAdapter mShoppingCartListAdapter;

    private boolean mSelectAll = true;

    @Override
    public ShoppingCartInfoPre createPresenter() {
        return new ShoppingCartInfoPre(mFragmentActivity);
    }

    @Nullable
    @Override
    public int getLayout() {
        return R.layout.frag_shopping_cart;
    }

    @Override
    public void initView() {
        super.initView();

        mTvSelect = findViewById(R.id.tv_cart_select);
        mTvTotalPrice = findViewById(R.id.tv_cart_total_price);
        mTvStartPay = findViewById(R.id.tv_cart_start_pay);
        mTvStartDel = findViewById(R.id.tv_cart_start_del);

        mShoppingCartListAdapter = new ShoppingCartListAdapter(this);
        mShoppingCartListAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mShoppingCartListAdapter);

        mRecyclerView.addItemDecoration(new DividerItemDecoration(mFragmentActivity, DividerItemDecoration.VERTICAL));

        mTvSelect.setOnClickListener(this);
        mTvStartDel.setOnClickListener(this);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        getPresenter().getCartList();
    }

    @Override
    public void onCartCount(int count) {

    }

    @Override
    public void onCartListResult(List<GoodsShoppingCarInfoBean> cartList) {
        mShoppingCartListAdapter.setCarInfoBeanList(cartList);
    }

    @Override
    public void modifyResult(int position) {
        mShoppingCartListAdapter.notifyItemChanged(position);
    }

    @Override
    public void onSelectCountResult(Integer count) {
        count = count == -1 ? 0 : count;
        mTvSelect.setSelected(count != 0);
        mTvSelect.setText(String.format(getString(R.string.txt_cart_select), count));
        mShoppingCartListAdapter.notifyDataSetChanged();
        mFragmentActivity.setResult(RESULT_OK);
    }

    @Override
    public void onItemClick(int position, GoodsShoppingCarInfoBean entity, View view) {
        switch (view.getId()) {
            case R.id.btn_cart_minus:
                setGoodsNum(false, entity.getGoodsNum(), position);
                break;
            case R.id.btn_cart_add:
                setGoodsNum(true, entity.getGoodsNum(), position);
                break;
            case R.id.iv_cart_selector:
                getPresenter().setSelectSingle(position);
                break;
        }
    }

    private void setGoodsNum(boolean isAdd, int goodsNum, int position) {
        if (isAdd) {
            goodsNum++;
        } else {
            if (goodsNum > 1) {
                goodsNum--;
            } else {
                goodsNum = 1;
            }
        }
        getPresenter().modifyGoodsNum(goodsNum, position);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_shopping_edit:
                mAppToolbar.getMenu().clear();
                mAppToolbar.inflateMenu(R.menu.menu_done);
                mTvStartPay.setVisibility(View.GONE);
                mTvStartDel.setVisibility(View.VISIBLE);

                getPresenter().setSelectAll(false);

                break;
            case R.id.menu_done:
                mAppToolbar.getMenu().clear();
                mAppToolbar.inflateMenu(R.menu.menu_shopping_cart_edit);
                mTvStartDel.setVisibility(View.GONE);
                mTvStartPay.setVisibility(View.VISIBLE);
                break;
        }
        return super.onMenuItemClick(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cart_start_del:
                getPresenter().delCart();
                break;
            case R.id.tv_cart_select:
                getPresenter().setSelectAll(mSelectAll);
                mSelectAll = !mSelectAll;
                break;
        }
    }
}
