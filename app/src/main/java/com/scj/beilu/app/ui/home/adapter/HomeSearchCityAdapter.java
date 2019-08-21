package com.scj.beilu.app.ui.home.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseViewHolder;

/**
 * author: SunGuiLan
 * date:2019/2/14
 * descriptin:
 */
public class HomeSearchCityAdapter extends RecyclerView.Adapter<HomeSearchCityAdapter.SearchHotHolder> {


    @NonNull
    @Override
    public SearchHotHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_city, parent, false);
        return new HomeSearchCityAdapter.SearchHotHolder(inflate);
    }


    @Override
    public void onBindViewHolder(@NonNull SearchHotHolder holder, int position) {

    }


    @Override
    public int getItemCount() {
        return 7;
    }

    class SearchHotHolder extends BaseViewHolder {

        public SearchHotHolder(View itemView) {
            super(itemView);
        }
    }
}

