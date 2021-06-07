package com.rayanandishehnasr.hmi.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class ChartsViewModel extends AndroidViewModel {

    MutableLiveData<String> TradePerSectionSelectedDate;
    MutableLiveData<String> AveragePerSectionSelectedDate;
    MutableLiveData<String> TradePerMonthSelectedDate;
    MutableLiveData<String> AveragePerMonthSelectedDate;
    MutableLiveData<String> TradePerZoneSelectedDate;
    MutableLiveData<String> TradePerZoneSelectedEndDate;
    MutableLiveData<String> AveragePerZoneSelectedDate;
    MutableLiveData<String> AveragePerZoneSelectedEndDate;

    MutableLiveData<Integer> TradePerZoneSelectedZone;
    MutableLiveData<Integer> AveragePerZoneSelectedZone;
    MutableLiveData<Integer> OpenChart;


    public ChartsViewModel(@NonNull Application application) {
        super(application);
        TradePerSectionSelectedDate = new MutableLiveData<>();
        AveragePerSectionSelectedDate = new MutableLiveData<>();
        TradePerMonthSelectedDate = new MutableLiveData<>();
        AveragePerMonthSelectedDate = new MutableLiveData<>();
        TradePerZoneSelectedDate = new MutableLiveData<>();
        TradePerZoneSelectedEndDate = new MutableLiveData<>();
        AveragePerZoneSelectedDate = new MutableLiveData<>();
        AveragePerZoneSelectedEndDate = new MutableLiveData<>();
        TradePerZoneSelectedZone = new MutableLiveData<>();
        AveragePerZoneSelectedZone =  new MutableLiveData<>();
        OpenChart = new MutableLiveData<>();
    }

    public MutableLiveData<String> getTradePerSectionSelectedDate() {
        return TradePerSectionSelectedDate;
    }

    public void setTradePerSectionSelectedDate(String tradePerSectionSelectedDate) {
        TradePerSectionSelectedDate.setValue(tradePerSectionSelectedDate);
    }

    public MutableLiveData<String> getAveragePerSectionSelectedDate() {
        return AveragePerSectionSelectedDate;
    }

    public void setAveragePerSectionSelectedDate(String averagePerSectionSelectedDate) {
        AveragePerSectionSelectedDate.setValue(averagePerSectionSelectedDate);
    }

    public MutableLiveData<String> getTradePerMonthSelectedDate() {
        return TradePerMonthSelectedDate;
    }

    public void setTradePerMonthSelectedDate(String tradePerMonthSelectedDate) {
        TradePerMonthSelectedDate.setValue(tradePerMonthSelectedDate);
    }

    public MutableLiveData<String> getAveragePerMonthSelectedDate() {
        return AveragePerMonthSelectedDate;
    }

    public void setAveragePerMonthSelectedDate(String averagePerMonthSelectedDate) {
        AveragePerMonthSelectedDate.setValue(averagePerMonthSelectedDate);
    }

    public MutableLiveData<String> getTradePerZoneSelectedDate() {
        return TradePerZoneSelectedDate;
    }

    public void setTradePerZoneSelectedDate(String tradePerZoneSelectedDate) {
        TradePerZoneSelectedDate.setValue(tradePerZoneSelectedDate);
    }

    public MutableLiveData<String> getTradePerZoneSelectedEndDate() {
        return TradePerZoneSelectedEndDate;
    }

    public void setTradePerZoneSelectedEndDate(String tradePerZoneSelectedEndDate) {
        TradePerZoneSelectedEndDate.setValue(tradePerZoneSelectedEndDate);
    }

    public MutableLiveData<String> getAveragePerZoneSelectedDate() {
        return AveragePerZoneSelectedDate;
    }

    public void setAveragePerZoneSelectedDate(String averagePerZoneSelectedDate) {
        AveragePerZoneSelectedDate.setValue(averagePerZoneSelectedDate);
    }

    public MutableLiveData<String> getAveragePerZoneSelectedEndDate() {
        return AveragePerZoneSelectedEndDate;
    }

    public void setAveragePerZoneSelectedEndDate(String averagePerZoneSelectedEndDate) {
        AveragePerZoneSelectedEndDate.setValue(averagePerZoneSelectedEndDate);
    }

    public MutableLiveData<Integer> getTradePerZoneSelectedZone() {
        return TradePerZoneSelectedZone;
    }

    public void setTradePerZoneSelectedZone(int tradePerZoneSelectedZone) {
        TradePerZoneSelectedZone.setValue(tradePerZoneSelectedZone);
    }

    public MutableLiveData<Integer> getAveragePerZoneSelectedZone() {
        return AveragePerZoneSelectedZone;
    }

    public void setAveragePerZoneSelectedZone(int averagePerZoneSelectedZone) {
        AveragePerZoneSelectedZone.setValue(averagePerZoneSelectedZone);
    }

    public MutableLiveData<Integer> getOpenChart() {
        return OpenChart;
    }

    public void setOpenChart(int openChart) {
        OpenChart.setValue(openChart);
    }
}
