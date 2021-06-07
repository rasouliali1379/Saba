package com.rayanandishehnasr.hmi.Fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

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
import com.google.android.gms.maps.model.LatLng;
import com.rayanandishehnasr.hmi.Activities.MainActivity;
import com.rayanandishehnasr.hmi.Activities.MapsActivity;
import com.rayanandishehnasr.hmi.DataHolder.CitiesDataHolder;
import com.rayanandishehnasr.hmi.DataHolder.DashBoardRootDataHolder;
import com.rayanandishehnasr.hmi.DataHolder.RentIntercourseBaseMuncItem;
import com.rayanandishehnasr.hmi.DataHolder.SalesIntercourseBaseMuncItem;
import com.rayanandishehnasr.hmi.Activities.SearchByFilterActivity;
import com.rayanandishehnasr.hmi.R;
import com.rayanandishehnasr.hmi.Retrofit.Controllers.DashBoardController;
import com.rayanandishehnasr.hmi.Utils.Consts;
import com.rayanandishehnasr.hmi.Utils.SectionValueFormmater;
import com.rayanandishehnasr.hmi.ViewModel.RetrofitViewModel;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeFragment extends Fragment {

    @BindView(R.id.main_activity_search_by_filter_btn)CardView searchByFilterBtn;
    @BindView(R.id.main_activity_sell_barchart)BarChart sellBarChart;
    @BindView(R.id.main_activity_rent_barchart)BarChart rentBarChart;
    @BindView(R.id.home_fragment_sell_number_txt)TextView sellNumberTxt;
    @BindView(R.id.home_fragment_rent_number_txt)TextView rentNumberTxt;
    @BindView(R.id.home_fragment_sell_average_price_txt)TextView sellAveragePriceTxt;
    @BindView(R.id.home_fragment_rent_average_price_txt)TextView rentAveragePriceTxt;

    Context context;
    RetrofitViewModel retrofitViewModel;


    public HomeFragment(Context context, RetrofitViewModel retrofitViewModel) {
        this.context = context;
        this.retrofitViewModel = retrofitViewModel;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        setupDashBoard();
        return view;
    }


    private void setupCharts(List<BarEntry> rentEntries, List<BarEntry> saleEntries) {

        //Options
        sellBarChart.getDescription().setEnabled(false);
        sellBarChart.getLegend().setEnabled(false);
        sellBarChart.setPinchZoom(false);
        sellBarChart.setDoubleTapToZoomEnabled(false);

        rentBarChart.getDescription().setEnabled(false);
        rentBarChart.getLegend().setEnabled(false);
        rentBarChart.setPinchZoom(false);
        rentBarChart.setDoubleTapToZoomEnabled(false);

        //XAxis
        ValueFormatter sectionValueFormatter =new SectionValueFormmater(" منطقه ");
        XAxis xAxis = sellBarChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(saleEntries.size());
        xAxis.setValueFormatter(sectionValueFormatter);

        xAxis = rentBarChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(rentEntries.size());
        xAxis.setValueFormatter(sectionValueFormatter);


        //YAxis
        YAxis yAxis = sellBarChart.getAxisLeft();
        yAxis.setDrawGridLines(false);

        yAxis = rentBarChart.getAxisLeft();
        yAxis.setDrawGridLines(false);

        yAxis = sellBarChart.getAxisRight();
        yAxis.setDrawLabels(false);
        yAxis.setDrawGridLines(false);

        yAxis = rentBarChart.getAxisRight();
        yAxis.setDrawLabels(false);
        yAxis.setDrawGridLines(false);

        BarDataSet saleBarDataSet = new BarDataSet(saleEntries, "Cells");
        BarData saleData = new BarData(saleBarDataSet);
        sellBarChart.setData(saleData);
        saleBarDataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
        if (!MainActivity.launched){
            sellBarChart.animateY(2000);
        }

        BarDataSet rentBardataset = new BarDataSet(rentEntries, "Cells");
        BarData rentData = new BarData(rentBardataset);
        rentBarChart.setData(rentData);
        rentBardataset.setColors(ColorTemplate.VORDIPLOM_COLORS);
        if (!MainActivity.launched) {
            rentBarChart.animateY(2000);
        }
        if (saleEntries.size() > 6){
            sellBarChart.setVisibleXRange(0,6);
        } else {
            sellBarChart.setVisibleXRange(0,saleEntries.size());
        }
        sellBarChart.invalidate();

        if (rentEntries.size() > 6){
            rentBarChart.setVisibleXRange(0,6);
        } else {
            rentBarChart.setVisibleXRange(0,rentEntries.size());
        }
        rentBarChart.invalidate();
        MainActivity.launched = true;
    }

    private void checkMapPermissions() {
        LatLng latLng = getSelectedCityLatLng();
        Intent intent = new Intent(context,  MapsActivity.class);
        intent.putExtra("Lat", latLng.latitude);
        intent.putExtra("Long", latLng.longitude);
        intent.putExtra("noMarker", true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                    (context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            } else {
                startActivity(intent);
            }
        } else {
            startActivity(intent);
        }
    }

    private LatLng getSelectedCityLatLng() {
        List<CitiesDataHolder> cities = retrofitViewModel.getCities().getValue();
        for (int i = 0; i < cities.size(); i++){
            if (cities.get(i).getTiState() == MainActivity.selectedCityId){
                return new LatLng(cities.get(i).getFLat(), cities.get(i).getFLon());
            }
        }
        return new LatLng(Consts.TEHRAN_LOCATION_LAT, Consts.TEHRAN_LOCATION_LONG);
    }

    @OnClick(R.id.main_activity_search_by_location_btn)void openMap() {
        checkMapPermissions();
    }

    @OnClick(R.id.main_activity_search_by_filter_btn)void openSearchPage(){
        Intent intent = new Intent(context, SearchByFilterActivity.class);
        intent.putExtra("stateId", retrofitViewModel.getSelectedCity().getValue());
        startActivity(intent);

    }

    private void setupDashBoard() {
        if(!MainActivity.launched){
            retrofitViewModel.getDashBoardData().setValue(null);
            new DashBoardController(context, retrofitViewModel).start(MainActivity.selectedCityId);
        }

        retrofitViewModel.getDashBoardData().observe(this, dashBoardRootData -> {
            if (dashBoardRootData != null){
                setupCharts(prepareRentDataForChart(dashBoardRootData.getRentIntercourseBaseMunc()),
                        prepareSaleDataForChart(dashBoardRootData.getSalesIntercourseBaseMunc()));
                fillIndexMenu(dashBoardRootData);
            }
        });
    }

    private void fillIndexMenu(DashBoardRootDataHolder data) {
        sellNumberTxt.setText(String.valueOf(data.getSellCount()));
        rentNumberTxt.setText(String.valueOf(data.getRentCount()));
        sellAveragePriceTxt.setText(NumberFormat.getInstance(Locale.US).format(data.getAveragehousingprices()) + " تومان");
        rentAveragePriceTxt.setText(NumberFormat.getInstance(Locale.US).format(data.getAverageRentingprices()) + " تومان");
    }

    private List<BarEntry> prepareSaleDataForChart(List<SalesIntercourseBaseMuncItem> sales) {
        List<BarEntry> salesPerSection = new ArrayList<>();

        for (int i = 0; i < sales.size(); i++){
            salesPerSection.add(new BarEntry(i + 1 , sales.get(i).getCount()));
        }
        return salesPerSection;
    }

    private List<BarEntry> prepareRentDataForChart(List<RentIntercourseBaseMuncItem> sales) {
        List<BarEntry> salesPerSection = new ArrayList<>();

        for (int i = 0; i < sales.size(); i++){
            salesPerSection.add(new BarEntry(i + 1 , sales.get(i).getCount()));
        }
        return salesPerSection;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                LatLng latLng = getSelectedCityLatLng();
                Intent intent = new Intent(context, MapsActivity.class);
                intent.putExtra("Lat", latLng.latitude);
                intent.putExtra("Long", latLng.longitude);
                intent.putExtra("noMarker", true);
                startActivity(intent);
            } else {

            }
        }
    }

}