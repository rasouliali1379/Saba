package com.rayanandishehnasr.hmi.Fragments;

import android.app.ActionBar;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.rayanandishehnasr.hmi.R;
import com.rayanandishehnasr.hmi.Utils.Consts;
import com.rayanandishehnasr.hmi.Utils.SectionValueFormmater;
import com.rayanandishehnasr.hmi.ViewModel.ChartsViewModel;
import com.rayanandishehnasr.hmi.ViewModel.Factory.ChartsViewModelFactory;
import com.rayanandishehnasr.hmi.ViewModel.Factory.RetrofitViewModelFactory;
import com.rayanandishehnasr.hmi.ViewModel.RetrofitViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BarChartDialogFragment extends DialogFragment {

    @BindView(R.id.bar_chart)BarChart barChart;
    @BindView(R.id.bar_chart_title_txt)TextView title;

    Context context;
    List<BarEntry> barEntries;
    int whichChart;

    public BarChartDialogFragment(Context context, List<BarEntry> barEntries, int whichChart) {
        this.context = context;
        this.barEntries = barEntries;
        this.whichChart = whichChart;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bar_chart, container, false);
        ButterKnife.bind(this, view);
        defineType();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
    }

    private void defineType() {
        String titleContent = null;
        switch (whichChart){
            case Consts.TRADE_PER_SECTION:
                titleContent = "نمودار تعداد معاملات به تفکیک منطقه";
                break;
            case Consts.AVERAGE_PER_SECTION:
                titleContent = "نمودار متوسط قیمت به تفکیک منطقه(میلیون تومان)";

                break;
            case Consts.TRADE_PER_MONTH:
                titleContent = "نمودار تعداد معاملات به تفکیک ماه";

                break;
            case Consts.AVERAGE_PER_MONTH:
                titleContent = "نمودار متوسط قیمت به تفکیک ماه(میلیون تومان)";

                break;
//            case Consts.TRADE_PER_ZONE:
//                titleContent = "نمودار تعداد معاملات به تفکیک محله";
//
//                break;
//            case Consts.AVERAGE_PER_ZONE:
//                titleContent = "نمودار متوسط قیمت به تفکیک محله ";
//
//                break;
        }
        title.setText(titleContent);
        setupCharts();
    }

    private void setupCharts() {


        //Options
        barChart.getDescription().setEnabled(false);
        barChart.getLegend().setEnabled(false);
        barChart.setPinchZoom(false);
        barChart.setDoubleTapToZoomEnabled(false);

        //XAxis
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(barEntries.size());
//        switch (whichChart){
//            case Consts.TRADE_PER_SECTION:
//            case Consts.AVERAGE_PER_SECTION:
//                ValueFormatter sectionValueFormatter =new SectionValueFormmater(" منطقه ");
//                xAxis.setValueFormatter(sectionValueFormatter);
//                break;
//            case Consts.TRADE_PER_MONTH:
//            case Consts.AVERAGE_PER_MONTH:
//
//                break;
//        }


        //YAxis
        YAxis yAxis = barChart.getAxisLeft();
        yAxis.setDrawGridLines(false);

        yAxis = barChart.getAxisRight();
        yAxis.setDrawLabels(false);
        yAxis.setDrawGridLines(false);

        BarDataSet saleBarDataSet = new BarDataSet(barEntries, "");
        BarData saleData = new BarData(saleBarDataSet);
        barChart.setData(saleData);
        saleBarDataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
        barChart.animateY(2000);
        if(barEntries.size() > 10){
            barChart.setVisibleXRange(0,10);
        } else {
            barChart.setVisibleXRange(0,barEntries.size());
        }
        barChart.invalidate();
    }
}