package com.rayanandishehnasr.hmi.Fragments;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieEntry;
import com.rayanandishehnasr.hmi.Activities.MainActivity;
import com.rayanandishehnasr.hmi.DataHolder.XnYDataHolder;
import com.rayanandishehnasr.hmi.R;
import com.rayanandishehnasr.hmi.Retrofit.Controllers.ChartAveragePerMonthController;
import com.rayanandishehnasr.hmi.Retrofit.Controllers.ChartAveragePerSectionController;
import com.rayanandishehnasr.hmi.Retrofit.Controllers.ChartAveragePerZoneController;
import com.rayanandishehnasr.hmi.Retrofit.Controllers.ChartTradePerMonthController;
import com.rayanandishehnasr.hmi.Retrofit.Controllers.ChartTradePerSectionController;
import com.rayanandishehnasr.hmi.Retrofit.Controllers.ChartTradePerZoneController;
import com.rayanandishehnasr.hmi.Utils.Consts;
import com.rayanandishehnasr.hmi.ViewModel.ChartsViewModel;
import com.rayanandishehnasr.hmi.ViewModel.Factory.ChartsViewModelFactory;
import com.rayanandishehnasr.hmi.ViewModel.RetrofitViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChartsFragment extends Fragment {

    MutableLiveData<List<XnYDataHolder>> TradePerZoneObserver;

    Context context;
    RetrofitViewModel retrofitViewModel;
    ChartsViewModel chartsViewModel;
    Application application;

    public ChartsFragment(Context context, RetrofitViewModel retrofitViewModel, Application application) {
        this.context = context;
        this.retrofitViewModel = retrofitViewModel;
        this.application = application;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_charts, container, false);
        ButterKnife.bind(this, view);
        initViewModels();
        initObservers();
        return view;
    }

    private void initViewModels() {
        chartsViewModel = new ViewModelProvider( this, new ChartsViewModelFactory(application)).get(ChartsViewModel.class);
    }

    private void initObservers() {
        chartsViewModel.getOpenChart().observe(this, which -> {
            switch (which){
                case Consts.TRADE_PER_SECTION:
                    setupTradePerSectionChart();
                    break;
                case Consts.AVERAGE_PER_SECTION:
                    setupAveragePerSectionChart();
                    break;
                case Consts.TRADE_PER_MONTH:
                    setupTradePerMonthChart();
                    break;
                case Consts.AVERAGE_PER_MONTH:
                    setupAveragePerMonth();
                    break;
                case Consts.TRADE_PER_ZONE:
                    setupTradePerZone();
                    break;
                case Consts.AVERAGE_PER_ZONE:
                    setupAveragePerZone();
                    break;
            }
        });

        retrofitViewModel.getRemoveObserver().observe(this, which -> {
            switch (which){
                case Consts.TRADE_PER_SECTION:
                    new Handler().postDelayed(() -> retrofitViewModel.getTradePerSectionData().removeObservers(this), 1000);
                    break;
                case Consts.AVERAGE_PER_SECTION:
                    new Handler().postDelayed(() -> retrofitViewModel.getAveragePerSectionData().removeObservers(this), 1000);
                    break;
                case Consts.TRADE_PER_MONTH:
                    new Handler().postDelayed(() -> retrofitViewModel.getTradePerMonthData().removeObservers(this), 1000);
                    break;
                case Consts.AVERAGE_PER_MONTH:
                    new Handler().postDelayed(() -> retrofitViewModel.getAveragePerMonthData().removeObservers(this), 1000);
                    break;
                case Consts.TRADE_PER_ZONE:
                    new Handler().postDelayed(() -> retrofitViewModel.getTradePerZoneData().removeObservers(this), 1000);
                    break;
                case Consts.AVERAGE_PER_ZONE:
                    new Handler().postDelayed(() -> retrofitViewModel.getAveragePerZoneData().removeObservers(this), 1000);
                    break;
            }
        });
    }

    private void setupAveragePerMonth() {
        new ChartAveragePerMonthController(context, retrofitViewModel).start(MainActivity.selectedCityId, chartsViewModel.getAveragePerMonthSelectedDate().getValue());
        retrofitViewModel.getAveragePerMonthData().observe(this, data -> {
            if (data != null){
                if (data.size() > 0){
                    FragmentTransaction ft = getChildFragmentManager().beginTransaction();
                    androidx.fragment.app.Fragment prev = getChildFragmentManager().findFragmentByTag("dialog");
                    if (prev != null) {
                        ft.remove(prev);
                    }
                    ft.addToBackStack(null);
                    BarChartDialogFragment barChartDialogFragment = new BarChartDialogFragment(context, prepareChartFloatData(data), Consts.AVERAGE_PER_MONTH);
                    barChartDialogFragment.show(ft, "dialog");
                    retrofitViewModel.setRemoveObserver(Consts.AVERAGE_PER_MONTH);
                }
            }
        });
    }

    private void setupTradePerMonthChart() {
        new ChartTradePerMonthController(context, retrofitViewModel).start(MainActivity.selectedCityId, chartsViewModel.getTradePerMonthSelectedDate().getValue());
        retrofitViewModel.getTradePerMonthData().observe(this, data -> {
            if (data != null){
                if (data.size() > 0){
                    FragmentTransaction ft = getChildFragmentManager().beginTransaction();
                    androidx.fragment.app.Fragment prev = getChildFragmentManager().findFragmentByTag("dialog");
                    if (prev != null) {
                        ft.remove(prev);
                    }
                    ft.addToBackStack(null);
                    BarChartDialogFragment barChartDialogFragment = new BarChartDialogFragment(context, prepareChartFloatData(data), Consts.TRADE_PER_MONTH);
                    barChartDialogFragment.show(ft, "dialog");
                    retrofitViewModel.setRemoveObserver(Consts.TRADE_PER_MONTH);
                }
            }
        });
    }

    private void setupAveragePerSectionChart() {
        new ChartAveragePerSectionController(context, retrofitViewModel).start(MainActivity.selectedCityId, chartsViewModel.getAveragePerSectionSelectedDate().getValue());
        retrofitViewModel.getAveragePerSectionData().observe(this, data -> {
            if (data != null){
                if(data.size() > 0){
                    FragmentTransaction ft = getChildFragmentManager().beginTransaction();
                    androidx.fragment.app.Fragment prev = getChildFragmentManager().findFragmentByTag("dialog");
                    if (prev != null) {
                        ft.remove(prev);
                    }
                    ft.addToBackStack(null);
                    BarChartDialogFragment barChartDialogFragment = new BarChartDialogFragment(context, prepareChartFloatData(data), Consts.AVERAGE_PER_SECTION);
                    barChartDialogFragment.show(ft, "dialog");
                    retrofitViewModel.setRemoveObserver(Consts.AVERAGE_PER_SECTION);
                }
            }
        });
    }



    private void setupTradePerSectionChart() {
        new ChartTradePerSectionController(context, retrofitViewModel).start(MainActivity.selectedCityId, chartsViewModel.getTradePerSectionSelectedDate().getValue());
        retrofitViewModel.getTradePerSectionData().observe(this, data -> {
            if (data != null){
                if(data.size() > 0){
                    FragmentTransaction ft = getChildFragmentManager().beginTransaction();
                    androidx.fragment.app.Fragment prev = getChildFragmentManager().findFragmentByTag("dialog");
                    if (prev != null) {
                        ft.remove(prev);
                    }
                    ft.addToBackStack(null);
                    BarChartDialogFragment barChartDialogFragment = new BarChartDialogFragment(context, prepareChartData(data), Consts.TRADE_PER_SECTION);
                    barChartDialogFragment.show(ft, "dialog");
                    retrofitViewModel.setRemoveObserver(Consts.TRADE_PER_SECTION);
                }
            }
        });
    }

    private void setupAveragePerZone() {
        new ChartAveragePerZoneController(context, retrofitViewModel).start(MainActivity.selectedCityId,
                chartsViewModel.getAveragePerZoneSelectedDate().getValue(),
                chartsViewModel.getAveragePerZoneSelectedZone().getValue(),
                chartsViewModel.getAveragePerZoneSelectedEndDate().getValue());
        retrofitViewModel.getAveragePerZoneData().observe(this, data -> {
            if (data != null){
                if (data.size() > 0){
                    FragmentTransaction ft = getChildFragmentManager().beginTransaction();
                    androidx.fragment.app.Fragment prev = getChildFragmentManager().findFragmentByTag("dialog");
                    if (prev != null) {
                        ft.remove(prev);
                    }
                    ft.addToBackStack(null);
                    ListStaticsDialogFragment listStaticsDialogFragment = new ListStaticsDialogFragment(context, data, Consts.AVERAGE_PER_ZONE);
                    listStaticsDialogFragment.show(ft, "dialog");
                    Log.e("getAveragePerZoneData", "ok");
                    retrofitViewModel.setRemoveObserver(Consts.AVERAGE_PER_ZONE);
                }
            }
        });
    }

    private void setupTradePerZone() {
        new ChartTradePerZoneController(context, retrofitViewModel).start(MainActivity.selectedCityId,
                chartsViewModel.getTradePerZoneSelectedDate().getValue(),
                chartsViewModel.getTradePerZoneSelectedZone().getValue(),
                chartsViewModel.getTradePerZoneSelectedEndDate().getValue());

        retrofitViewModel.getTradePerZoneData().observe(this, data -> {
            if (data != null){
                if(data.size() > 0){
                    FragmentTransaction ft = getChildFragmentManager().beginTransaction();
                    androidx.fragment.app.Fragment prev = getChildFragmentManager().findFragmentByTag("dialog");
                    if (prev != null) {
                        ft.remove(prev);
                    }
                    ft.addToBackStack(null);
                    ListStaticsDialogFragment listStaticsDialogFragment = new ListStaticsDialogFragment(context, data, Consts.TRADE_PER_ZONE);
                    listStaticsDialogFragment.show(ft, "dialog");
                    Log.e("getTradePerZoneData", "ok");
                    retrofitViewModel.setRemoveObserver(Consts.TRADE_PER_ZONE);
                }
            }
        });
    }

    private List<BarEntry> prepareChartData(List<XnYDataHolder> data) {
        List<BarEntry> entries = new ArrayList<>();
        for (int i = 0; i < data.size(); i++){
            entries.add(new BarEntry(Integer.parseInt(data.get(i).getX()), Integer.parseInt(data.get(i).getY())));
        }

        return entries;
    }

    private List<BarEntry> prepareChartFloatData(List<XnYDataHolder> data) {
        List<BarEntry> entries = new ArrayList<>();
        for (int i = 0; i < data.size(); i++){
            entries.add(new BarEntry(Integer.parseInt(data.get(i).getX()), Float.parseFloat(data.get(i).getY())));
        }

        return entries;
    }

    @OnClick(R.id.charts_fragment_trade_per_section_btn)void tradePerSection(){
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        androidx.fragment.app.Fragment prev = getChildFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        ChartSettingsDialogFragment chartSettingsDialogFragment = new ChartSettingsDialogFragment(context, Consts.TRADE_PER_SECTION, retrofitViewModel, chartsViewModel);
        chartSettingsDialogFragment.show(ft, "dialog");
    }

    @OnClick(R.id.charts_fragment_average_per_section_btn)void averagePerSection(){
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        androidx.fragment.app.Fragment prev = getChildFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        ChartSettingsDialogFragment chartSettingsDialogFragment = new ChartSettingsDialogFragment(context, Consts.AVERAGE_PER_SECTION, retrofitViewModel, chartsViewModel);
        chartSettingsDialogFragment.show(ft, "dialog");
    }

    @OnClick(R.id.charts_fragment_trade_per_month_btn)void tradePerMonth(){
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        androidx.fragment.app.Fragment prev = getChildFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        ChartSettingsDialogFragment chartSettingsDialogFragment = new ChartSettingsDialogFragment(context, Consts.TRADE_PER_MONTH, retrofitViewModel, chartsViewModel);
        chartSettingsDialogFragment.show(ft, "dialog");
    }

    @OnClick(R.id.charts_fragment_average_per_month_btn)void averagePerMonth(){
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        androidx.fragment.app.Fragment prev = getChildFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        ChartSettingsDialogFragment chartSettingsDialogFragment = new ChartSettingsDialogFragment(context, Consts.AVERAGE_PER_MONTH, retrofitViewModel, chartsViewModel);
        chartSettingsDialogFragment.show(ft, "dialog");
    }

    @OnClick(R.id.charts_fragment_trade_per_zone_btn)void tradePerZone(){
        retrofitViewModel.setSelectedSection(0);
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        androidx.fragment.app.Fragment prev = getChildFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        ChartSettingsDialogFragment chartSettingsDialogFragment = new ChartSettingsDialogFragment(context, Consts.TRADE_PER_ZONE, retrofitViewModel, chartsViewModel);
        chartSettingsDialogFragment.show(ft, "dialog");
    }

    @OnClick(R.id.charts_fragment_average_per_zone_btn)void averagePerZone(){
        retrofitViewModel.setSelectedSection(0);
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        androidx.fragment.app.Fragment prev = getChildFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        ChartSettingsDialogFragment chartSettingsDialogFragment = new ChartSettingsDialogFragment(context, Consts.AVERAGE_PER_ZONE, retrofitViewModel, chartsViewModel);
        chartSettingsDialogFragment.show(ft, "dialog");
    }
}