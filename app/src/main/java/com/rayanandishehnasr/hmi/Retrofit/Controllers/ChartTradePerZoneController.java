package com.rayanandishehnasr.hmi.Retrofit.Controllers;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rayanandishehnasr.hmi.DataHolder.XnYDataHolder;
import com.rayanandishehnasr.hmi.Retrofit.APIService;
import com.rayanandishehnasr.hmi.Utils.Consts;
import com.rayanandishehnasr.hmi.ViewModel.RetrofitViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChartTradePerZoneController implements Callback<List<XnYDataHolder>> {


    Context context;
    private RetrofitViewModel retrofitViewModel;

    private ProgressDialog progressDialog;

    public ChartTradePerZoneController(Context context, RetrofitViewModel retrofitViewModel) {
        this.context = context;
        this.retrofitViewModel = retrofitViewModel;
        progressDialog = new ProgressDialog(context);
    }

    public void start(int stateId, String date, int section, String endDate) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Consts.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        APIService apiService = retrofit.create(APIService.class);

        Call<List<XnYDataHolder>> call = apiService.GetTradePerZone(stateId, date, section, endDate);
        call.enqueue(this);
        progressDialog.setMessage("لطفا صبر کنید...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    public void onResponse(Call<List<XnYDataHolder>> call, Response<List<XnYDataHolder>> response) {
        if(response.isSuccessful()) {
            List<XnYDataHolder> data = response.body();
            if(data.size() > 0){
                retrofitViewModel.setTradePerZoneData(data);
            } else {
                Toast.makeText(context, "داده ای برای نمودار دریافت نشد!", Toast.LENGTH_SHORT).show();
                retrofitViewModel.setRemoveObserver(Consts.TRADE_PER_ZONE);
            }
        } else {
            Toast.makeText(context,"خطایی رخ داد!", Toast.LENGTH_LONG).show();
            retrofitViewModel.setSelectedFragment(Consts.ERROR_PAGE);
        }

        progressDialog.dismiss();
    }

    @Override
    public void onFailure(Call<List<XnYDataHolder>> call, Throwable t) {
        Toast.makeText(context,"عدم امکان ارتباط با سرور!", Toast.LENGTH_LONG).show();
        retrofitViewModel.setSelectedFragment(Consts.ERROR_PAGE);
        progressDialog.dismiss();
    }
}