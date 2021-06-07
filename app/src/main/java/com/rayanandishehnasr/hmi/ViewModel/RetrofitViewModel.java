package com.rayanandishehnasr.hmi.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.rayanandishehnasr.hmi.DataHolder.CitiesDataHolder;
import com.rayanandishehnasr.hmi.DataHolder.DashBoardRootDataHolder;
import com.rayanandishehnasr.hmi.DataHolder.LocationRootDataHolder;
import com.rayanandishehnasr.hmi.DataHolder.SearchResultDataHolder;
import com.rayanandishehnasr.hmi.DataHolder.XnYDataHolder;

import java.util.List;

public class RetrofitViewModel extends AndroidViewModel {

    MutableLiveData<LocationRootDataHolder> locations;
    MutableLiveData<Integer> selectedZone;
    MutableLiveData<Integer> selectedSection;
    MutableLiveData<Integer> selectedCity;
    MutableLiveData<Integer> selectedFragment;
    MutableLiveData<List<SearchResultDataHolder>> searchResults;
    MutableLiveData<Integer> selectedSearchResult;
    MutableLiveData<Integer> connectionCheckResult;
    MutableLiveData<DashBoardRootDataHolder> dashBoardData;
    MutableLiveData<List<CitiesDataHolder>> cities;
    MutableLiveData<List<XnYDataHolder>> tradePerSectionData;
    MutableLiveData<List<XnYDataHolder>> averagePerSectionData;
    MutableLiveData<List<XnYDataHolder>> tradePerMonthData;
    MutableLiveData<List<XnYDataHolder>> averagePerMonthData;
    MutableLiveData<List<XnYDataHolder>> tradePerZoneData;
    MutableLiveData<List<XnYDataHolder>> averagePerZoneData;
    MutableLiveData<Integer> removeObserver;
    MutableLiveData<Integer> scrollPosition;



    public RetrofitViewModel(@NonNull Application application) {
        super(application);
        locations = new MutableLiveData<>();
        selectedZone = new MutableLiveData<>();
        selectedSection = new MutableLiveData<>();
        selectedCity = new MutableLiveData<>();
        selectedFragment = new MutableLiveData<>();
        searchResults = new MutableLiveData<>();
        selectedSearchResult = new MutableLiveData<>();
        connectionCheckResult = new MutableLiveData<>();
        dashBoardData = new MutableLiveData<>();
        cities = new MutableLiveData<>();
        tradePerSectionData = new MutableLiveData<>();
        averagePerSectionData = new MutableLiveData<>();
        tradePerMonthData = new MutableLiveData<>();
        averagePerMonthData = new MutableLiveData<>();
        tradePerZoneData = new MutableLiveData<>();
        averagePerZoneData = new MutableLiveData<>();
        removeObserver = new MutableLiveData<>();
        scrollPosition = new MutableLiveData<>();
        scrollPosition.setValue(-1);
    }

    public MutableLiveData<LocationRootDataHolder> getLocations() {
        return locations;
    }

    public void setLocations(LocationRootDataHolder locations) {
        this.locations.setValue(locations);
    }

    public MutableLiveData<Integer> getSelectedZone() {
        return selectedZone;
    }

    public void setSelectedZone(int selectedZone) {
        this.selectedZone.setValue(selectedZone);
    }

    public MutableLiveData<Integer> getSelectedSection() {
        return selectedSection;
    }

    public void setSelectedSection(int selectedSection) {
        this.selectedSection.setValue(selectedSection);
    }

    public MutableLiveData<Integer> getSelectedCity() {
        return selectedCity;
    }

    public void setSelectedCity(int selectedCity) {
        this.selectedCity.setValue(selectedCity);
    }

    public MutableLiveData<Integer> getSelectedFragment() {
        return selectedFragment;
    }

    public void setSelectedFragment(int selectedFragment) {
        this.selectedFragment.setValue(selectedFragment);
    }

    public MutableLiveData<List<SearchResultDataHolder>> getSearchResults() {
        return searchResults;
    }

    public void setSearchResults(List<SearchResultDataHolder> searchResults) {
        this.searchResults.setValue(searchResults);
    }

    public MutableLiveData<Integer> getSelectedSearchResult() {
        return selectedSearchResult;
    }

    public void setSelectedSearchResult(int selectedSearchResult) {
        this.selectedSearchResult.setValue(selectedSearchResult);
    }

    public void setConnectionCheckResult(int results) {
        this.connectionCheckResult.setValue(results);
    }

    public MutableLiveData<Integer> getConnectionCheckResult() {
        return connectionCheckResult;
    }

    public MutableLiveData<DashBoardRootDataHolder> getDashBoardData() {
        return dashBoardData;
    }

    public void setDashBoardData(DashBoardRootDataHolder dashBoardData) {
        this.dashBoardData.setValue(dashBoardData);
    }

    public MutableLiveData<List<CitiesDataHolder>> getCities() {
        return cities;
    }

    public void setCities(List<CitiesDataHolder> cities) {
        this.cities.setValue(cities);
    }

    public MutableLiveData<List<XnYDataHolder>> getTradePerSectionData() {
        return tradePerSectionData;
    }

    public void setTradePerSectionData(List<XnYDataHolder> tradePerSectionData) {
        this.tradePerSectionData.setValue(tradePerSectionData);
    }

    public MutableLiveData<List<XnYDataHolder>> getAveragePerSectionData() {
        return averagePerSectionData;
    }

    public void setAveragePerSectionData(List<XnYDataHolder> averagePerSectionData) {
        this.averagePerSectionData.setValue(averagePerSectionData);
    }

    public MutableLiveData<List<XnYDataHolder>> getTradePerMonthData() {
        return tradePerMonthData;
    }

    public void setTradePerMonthData(List<XnYDataHolder> tradePerMonthData) {
        this.tradePerMonthData.setValue(tradePerMonthData);
    }

    public MutableLiveData<List<XnYDataHolder>> getAveragePerMonthData() {
        return averagePerMonthData;
    }

    public void setAveragePerMonthData(List<XnYDataHolder> averagePerMonthData) {
        this.averagePerMonthData.setValue(averagePerMonthData);
    }

    public MutableLiveData<List<XnYDataHolder>> getTradePerZoneData() {
        return tradePerZoneData;
    }

    public void setTradePerZoneData(List<XnYDataHolder> tradePerZoneData) {
        this.tradePerZoneData.setValue(tradePerZoneData);
    }

    public MutableLiveData<List<XnYDataHolder>> getAveragePerZoneData() {
        return averagePerZoneData;
    }

    public void setAveragePerZoneData(List<XnYDataHolder> averagePerZoneData) {
        this.averagePerZoneData.setValue(averagePerZoneData);
    }

    public MutableLiveData<Integer> getRemoveObserver() {
        return removeObserver;
    }

    public void setRemoveObserver(int removeObserver) {
        this.removeObserver.setValue(removeObserver);
    }

    public MutableLiveData<Integer> getScrollPosition() {
        return scrollPosition;
    }

    public void setScrollPosition(int scrollPosition) {
        this.scrollPosition.setValue(scrollPosition);
    }
}
