package com.rayanandishehnasr.hmi.Fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.rayanandishehnasr.hmi.Activities.MapsActivity;
import com.rayanandishehnasr.hmi.DataHolder.SearchResultDataHolder;
import com.rayanandishehnasr.hmi.R;
import com.rayanandishehnasr.hmi.Utils.Consts;
import com.rayanandishehnasr.hmi.Utils.JalaliConverter;

import java.text.NumberFormat;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailsFragment extends Fragment implements OnMapReadyCallback {

    private Context context;
    private SearchResultDataHolder result;
    private int treatyType;
//    @BindView(R.id.details_fragment_floors_number)TextView floorsNumberTxt;
//    @BindView(R.id.details_fragment_property_direction)TextView propertyDirectionTxt;
//    @BindView(R.id.details_fragment_property_type)TextView propertyTypeTxt;
//    @BindView(R.id.details_fragment_property_use)TextView propertyUseTxt;
//    @BindView(R.id.details_fragment_rooms_number)TextView propertyRoomsNumberTxt;
//    @BindView(R.id.details_fragment_recyclerview)RecyclerView recyclerView;
//    @BindView(R.id.details_fragment_no_facilities)TextView noFacilities;
//    @BindView(R.id.details_fragment_struc_type)TextView structType;

    @BindView(R.id.details_fragment_address)TextView addressTxt;
    @BindView(R.id.details_fragment_const_year)TextView constYearTxt;
    @BindView(R.id.details_fragment_contract_date)TextView contractdateTxt;
    @BindView(R.id.details_fragment_property_age)TextView propertyAgeTxt;
    @BindView(R.id.details_fragment_property_area)TextView propertyArea;
    @BindView(R.id.details_fragment_property_price)TextView propertyPriceTxt;
    @BindView(R.id.details_fragment_property_price_per_meter)TextView propertyPricePerMeterTxt;
    @BindView(R.id.details_fragment_no_coordinates)TextView noCoordinatesTxt;
    @BindView(R.id.details_fragment_section)TextView propertySectionTxt;
    @BindView(R.id.details_fragment_zone)TextView zoneTxt;
    @BindView(R.id.details_fragment_price_per_meter_label)TextView pricePerMeterLabel;
    @BindView(R.id.details_fragment_price_label)TextView priceLabel;
    @BindView(R.id.details_fragment_map_click_layout)LinearLayout mapClickLayout;
    @BindView(R.id.details_fragment_map_root_layout) CardView mapRootLayout;
    @BindView(R.id.details_fragment_map_label)TextView mapLabel;
    @BindView(R.id.details_fragment_final_rent)TextView finalRentTxt;
    @BindView(R.id.details_fragment_final_rent_layout)LinearLayout finalRentLayout;
    @BindView(R.id.details_fragment_final_rent_separator)LinearLayout finalRentSeparatorLayout;


    private SupportMapFragment map;

    public DetailsFragment(Context context, SearchResultDataHolder result, int treatyType) {
        this.context = context;
        this.result = result;
        this.treatyType = treatyType;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_details, container, false);
        ButterKnife.bind(this,view);
        //recyclerView();
        return view;
    }

    private void checkMapPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                    (context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            } else {

            }
        } else {
            mapSetup();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        fillData();
        mapSetup();
//        checkMapPermissions();
    }

    private void mapSetup(){
        LatLng location = new LatLng(result.getFlat(), result.getFlon());
        if (location.longitude == 0 && location.latitude == 0){
            mapRootLayout.setVisibility(View.GONE);
            mapLabel.setVisibility(View.GONE);
        } else {
            map = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.details_fragment_map);
            map.getMapAsync(this);
        }
    }

//    private void recyclerView() {
//        List<String> facilities = getFacilities();
//        if (facilities.size() > 0){
//            FacilitiesListAdapter facilitiesListAdapter = new FacilitiesListAdapter(context, facilities);
//            FlowLayoutManager flowLayoutManager = new FlowLayoutManager().setAlignment(Alignment.RIGHT);
//            flowLayoutManager.setAutoMeasureEnabled(true);
//            recyclerView.setLayoutManager(flowLayoutManager);
//            recyclerView.setAdapter(facilitiesListAdapter);
//        } else {
//            noFacilities.setVisibility(View.VISIBLE);
//        }
//    }

//    private List<String> getFacilities() {
//        List<String> facilities = new ArrayList<>();
//        //if (result.isBJagozi())
//            facilities.add("جکوزی");
//      //  if (result.isBParking())
//            facilities.add("پارکینگ");
//       // if (result.isBPool())
//            facilities.add("استخر");
//        //if (result.isBStoreArea())
//            facilities.add("انباری");
//        facilities.add("جکوزی");
//        //  if (result.isBParking())
//        facilities.add("پارکینگ");
//        // if (result.isBPool())
//        facilities.add("استخر");
//        //if (result.isBStoreArea())
//        facilities.add("انباری");
//        facilities.add("جکوزی");
//        //  if (result.isBParking())
//        facilities.add("پارکینگ");
//        // if (result.isBPool())
//        facilities.add("استخر");
//        //if (result.isBStoreArea())
//        facilities.add("انباری");
//        return facilities;
//    }

    private void fillData() {
        addressTxt.setText(result.getStrPostAddress());
        constYearTxt.setText(String.valueOf(result.getSiYearBuild()));
        contractdateTxt.setText(result.getStrTreatyDate());
        int currentYear = Integer.parseInt(JalaliConverter.getCurrentShamsidate().split("/")[0]);
        int calculateAge = currentYear - result.getSiYearBuild();
        propertyAgeTxt.setText(calculateAge + " سال");
        propertyArea.setText((int) result.getFArea() + " متر");

        if(treatyType == 1){
            propertyPriceTxt.setText(NumberFormat.getInstance(Locale.US).format(result.getICost()) + " تومان");
            propertyPricePerMeterTxt.setText(NumberFormat.getInstance(Locale.US).format(result.getICostPerMeter()) + " تومان");
        } else {
            propertyPriceTxt.setText(NumberFormat.getInstance(Locale.US).format(result.getiTrust()) + " تومان");
            propertyPricePerMeterTxt.setText(NumberFormat.getInstance(Locale.US).format(result.getIRentPerMounth()) + " تومان");
            pricePerMeterLabel.setText("مبلغ اجاره");
            priceLabel.setText("مبلغ رهن");
            finalRentLayout.setVisibility(View.VISIBLE);
            finalRentSeparatorLayout.setVisibility(View.VISIBLE);
            finalRentTxt.setText(NumberFormat.getInstance(Locale.US).format(result.getiFinalRent()) + " تومان");
        }


        propertySectionTxt.setText(String.valueOf(result.getStrMunZoneComment()));
        zoneTxt.setText(String.valueOf(result.getStrZoneName()));

//        floorsNumberTxt.setText(String.valueOf(result.getTiFloor()));
//        structType.setText(result.getStrFrameTypeComment());
//        propertyTypeTxt.setText(result.getStrEstateTypeComment());
//        propertyUseTxt.setText(result.getStrEstateUsageComment());
//        propertyRoomsNumberTxt.setText(result.getTiBedrooms() + " عدد");
//        propertyDirectionTxt.setText(String.valueOf(result.getTiDirection()));

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng location = new LatLng(result.getFlat(), result.getFlon());
        if (location.longitude == 0 && location.latitude == 0){
            noCoordinatesTxt.setVisibility(View.VISIBLE);
        } else {
            googleMap.addMarker(new MarkerOptions().position(location));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, Consts.DEFAULT_ZOOM));
            googleMap.getUiSettings().setAllGesturesEnabled(false);
            noCoordinatesTxt.setVisibility(View.GONE);
            mapClickLayout.setOnClickListener(v-> openMap());
        }
    }

    private void openMap(){
        Intent intent = new Intent(context, MapsActivity.class);
        intent.putExtra("Lat", result.getFlat());
        intent.putExtra("Long", result.getFlon());
        intent.putExtra("noMarker", false);
        startActivity(intent);
    }
}