package com.scj.beilu.app.ui.fit.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.scj.beilu.app.R;
import com.scj.beilu.app.base.BaseViewHolder;
import com.scj.beilu.app.listener.OnItemClickListener;
import com.scj.beilu.app.mvp.fit.bean.FitRecordGirthInfoBean;
import com.scj.beilu.app.mvp.fit.bean.FitRecordGirthValBean;

import java.util.List;

/**
 * @author Mingxun
 * @time on 2019/3/13 17:32
 */
public class FitRecordGirthListAdapter extends RecyclerView.Adapter<FitRecordGirthListAdapter.GirthViewHolder> {

    private List<FitRecordGirthInfoBean> mFitRecordGirthInfoBeanList;
    private OnItemClickListener<FitRecordGirthInfoBean> mOnItemClickListener;
    private Context mContext;

    public FitRecordGirthListAdapter(Context context) {
        mContext = context;
    }

    public void setOnItemClickListener(OnItemClickListener<FitRecordGirthInfoBean> onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void setFitRecordGirthInfoBeanList(List<FitRecordGirthInfoBean> fitRecordGirthInfoBeanList) {
        mFitRecordGirthInfoBeanList = fitRecordGirthInfoBeanList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public GirthViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fit_girth_content, parent, false);
        return new GirthViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GirthViewHolder holder, int position) {
        try {
            FitRecordGirthInfoBean girthInfoBean = mFitRecordGirthInfoBeanList.get(position);
            holder.mTvName.setText(girthInfoBean.getRecordName());
            holder.mTvUnit.setText("单位：" + girthInfoBean.getUnit());
            holder.mTvBtnVal.setText("记录" + girthInfoBean.getRecordName());

            XAxis xAxis = holder.mLineChart.getXAxis();
            final List<FitRecordGirthValBean> recordData = girthInfoBean.getRecordData();
            final int size = recordData.size();
            xAxis.setValueFormatter(new ValueFormatter() {
                @Override
                public String getFormattedValue(float value) {
                    int index = (int) value;
                    if (value != -1 && index < size) {
                        return recordData.get(index).getRecordDate();
                    } else return "";
                }
            });

            YAxis leftAxis = holder.mLineChart.getAxisLeft();
            leftAxis.setLabelCount(size);
            holder.mLineChart.setData(girthInfoBean.getLineData());
            holder.mLineChart.invalidate();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return mFitRecordGirthInfoBeanList == null ? 0 : mFitRecordGirthInfoBeanList.size();
    }


    class GirthViewHolder extends BaseViewHolder {
        private TextView mTvName;
        private TextView mTvUnit;
        private TextView mTvBtnVal;
        private LineChart mLineChart;

        public GirthViewHolder(View itemView) {
            super(itemView);
            mTvName = findViewById(R.id.tv_fit_unit_name);
            mTvUnit = findViewById(R.id.tv_fit_unit);
            mTvBtnVal = findViewById(R.id.tv_btn_txt);
            mLineChart = findViewById(R.id.chart_data);
            initChart(mLineChart);
            mTvBtnVal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    FitRecordGirthInfoBean girthInfoBean = mFitRecordGirthInfoBeanList.get(position);
                    if (mOnItemClickListener != null && girthInfoBean != null) {
                        mOnItemClickListener.onItemClick(position, girthInfoBean, v);
                    }
                }
            });
        }
    }


    private void initChart(LineChart lineChart) {
//        final int size = girthValBeanList.size();
        lineChart.getDescription().setEnabled(false);
        lineChart.setTouchEnabled(true);
        // enable scaling and dragging
        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(true);
        lineChart.setDrawGridBackground(false);
        // if disabled, scaling can be done on x- and y-axis separately
        lineChart.setPinchZoom(true);
        // set an alternative background color
        lineChart.setBackgroundColor(Color.WHITE);
        lineChart.setExtraBottomOffset(30);
        // add empty data
        lineChart.setNoDataTextColor(ContextCompat.getColor(mContext, R.color.GRAY));
        lineChart.setNoDataText("暂无数据");
        lineChart.setDragOffsetX(30f);
        //关闭图例
        Legend l = lineChart.getLegend();
        l.setEnabled(false);
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setEnabled(true);
        xAxis.setTextSize(12);
        xAxis.setTextColor(ContextCompat.getColor(mContext, R.color.colorGray));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setCenterAxisLabels(false);
        xAxis.setAvoidFirstLastClipping(false);

        xAxis.setGranularity(1f);
//        xAxis.setValueFormatter(new ValueFormatter() {
//            @Override
//            public String getFormattedValue(float value) {
//                int index = (int) value;
//                if (value != -1 && index != size) {
//                    return girthValBeanList.get(index).getRecordDate();
//                } else return "";
//            }
//        });

        YAxis leftAxis = lineChart.getAxisLeft();
        leftAxis.setTextColor(ContextCompat.getColor(mContext, R.color.colorGray));

        leftAxis.setZeroLineColor(Color.GREEN);
        leftAxis.setAxisLineColor(Color.TRANSPARENT);
//        leftAxis.setLabelCount(size);
        leftAxis.setDrawGridLines(true);
        YAxis rightAxis = lineChart.getAxisRight();
        rightAxis.setEnabled(false);
        lineChart.animateXY(1500, 1500);
    }

}
