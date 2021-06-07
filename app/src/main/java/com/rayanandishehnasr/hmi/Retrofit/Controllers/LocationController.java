package com.rayanandishehnasr.hmi.Retrofit.Controllers;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rayanandishehnasr.hmi.DataHolder.LocationRootDataHolder;
import com.rayanandishehnasr.hmi.Retrofit.APIService;
import com.rayanandishehnasr.hmi.Utils.Consts;
import com.rayanandishehnasr.hmi.ViewModel.RetrofitViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LocationController implements Callback<LocationRootDataHolder> {


    Context context;
    private RetrofitViewModel retrofitViewModel;

    private ProgressDialog progressDialog;

    public LocationController(Context context, RetrofitViewModel retrofitViewModel) {
        this.context = context;
        this.retrofitViewModel = retrofitViewModel;
        progressDialog = new ProgressDialog(context);
    }

    public void start(int stateId) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Consts.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        APIService apiService = retrofit.create(APIService.class);

        Call<LocationRootDataHolder> call = apiService.ModelMobile(stateId);
        call.enqueue(this);
        progressDialog.setMessage("لطفا صبر کنید...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    public void onResponse(Call<LocationRootDataHolder> call, Response<LocationRootDataHolder> response) {
        if(response.isSuccessful()) {
            LocationRootDataHolder locationsList = response.body();
            retrofitViewModel.setLocations(locationsList);
        } else {
            Toast.makeText(context,"اطلاعاتی دریافت نشد! در حال بررسی ارتباط با سرور!", Toast.LENGTH_LONG).show();
            new CheckConnectionController(context, retrofitViewModel).start(Consts.CONNECTION_REQUEST_CODE);
        }

        progressDialog.dismiss();
    }

    @Override
    public void onFailure(Call<LocationRootDataHolder> call, Throwable t) {
        Toast.makeText(context,"اطلاعاتی دریافت نشد! در حال بررسی ارتباط با سرور", Toast.LENGTH_LONG).show();
        new CheckConnectionController(context, retrofitViewModel).start(Consts.CONNECTION_REQUEST_CODE);
        progressDialog.dismiss();
    }
}