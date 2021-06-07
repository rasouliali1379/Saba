package com.rayanandishehnasr.hmi.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;
import com.rayanandishehnasr.hmi.R;
import com.rayanandishehnasr.hmi.Utils.Consts;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    @BindView(R.id.maps_activity_rg)RadioGroup tradeTypeRg;
    @BindView(R.id.map_activity_search_btn)Button searchCoordinates;
    @BindView(R.id.map_activity_toolbar_title_txt)TextView toolbarTitle;
    @BindView(R.id.map_activity_my_location_btn)ImageView myLocationBtn;

    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private Location lastKnownLocation;
    private double Lat,Long;
    private LatLng selectedLatLong;
    private Intent intent;
    private boolean marked = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        intent = getIntent();
        ButterKnife.bind(this);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        if(intent.hasExtra("Lat") && intent.hasExtra("Long")){

            selectedLatLong = new LatLng(intent.getDoubleExtra("Lat", 0), intent.getDoubleExtra("Long", 0));

            if (intent.getBooleanExtra("noMarker", true)){
                initListeners();
                tradeTypeRg.check(R.id.maps_activity_sell_rb);
            } else {
                searchCoordinates.setVisibility(View.GONE);
                tradeTypeRg.setVisibility(View.GONE);
                toolbarTitle.setVisibility(View.VISIBLE);
                myLocationBtn.setVisibility(View.GONE);
            }
        }

    }

    private void initListeners() {

    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        if(intent.getBooleanExtra("noMarker", true)){
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(selectedLatLong, Consts.WIDE_ZOOM));
            mMap.setMyLocationEnabled(true);
            mMap.setOnMapClickListener(latLng -> {
                marked = true;
                mMap.clear();
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, Consts.DEFAULT_ZOOM));
                mMap.addMarker(new MarkerOptions().position(latLng));
                selectedLatLong = latLng;
            });
        } else {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(selectedLatLong, Consts.DEFAULT_ZOOM));
            mMap.addMarker(new MarkerOptions().position(selectedLatLong));
        }
    }

    private void getDeviceLocation() {
        try {
                Task<Location> locationResult = fusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        lastKnownLocation = task.getResult();
                        if (lastKnownLocation != null) {
                            Lat = lastKnownLocation.getLatitude();
                            Long = lastKnownLocation.getLongitude();
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Lat, Long), Consts.DEFAULT_ZOOM));
                        }
                    } else {
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(selectedLatLong, Consts.WIDE_ZOOM));
                    }
                });
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage(), e);
        }
    }

    @OnClick(R.id.map_activity_close_btn)void CloseMap(){
        finish();
    }

    @OnClick(R.id.map_activity_search_btn)void searchCoordinates(){
        if (selectedLatLong != null && marked){
            Intent intent = new Intent(this, SearchResultsActivity.class);
            intent.putExtra("search_by_coordinates", true);
            intent.putExtra("lat", selectedLatLong.latitude);
            intent.putExtra("long", selectedLatLong.longitude);
            switch (tradeTypeRg.getCheckedRadioButtonId()){
                case R.id.maps_activity_sell_rb :
                    intent.putExtra("TreatyType", 1);
                    break;
                case R.id.maps_activity_rent_rb :
                    intent.putExtra("TreatyType", 2);
                    break;
            }
            startActivity(intent);
        } else {
            Toast.makeText(this, "یک نقطه روی نقشه مشخص کنید", Toast.LENGTH_LONG).show();
        }
    }

    @OnClick(R.id.map_activity_my_location_btn) void myLocation(){
        getDeviceLocation();
    }

}