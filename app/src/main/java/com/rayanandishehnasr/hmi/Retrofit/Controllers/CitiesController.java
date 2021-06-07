package com.rayanandishehnasr.hmi.Retrofit.Controllers;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rayanandishehnasr.hmi.DataHolder.CitiesDataHolder;
import com.rayanandishehnasr.hmi.Retrofit.APIService;
import com.rayanandishehnasr.hmi.Utils.Consts;
import com.rayanandishehnasr.hmi.ViewModel.RetrofitViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CitiesController implements Callback<List<CitiesDataHolder>> {

    Context context;
    private RetrofitViewModel retrofitViewModel;
    private ProgressDialog progressDialog;

    public CitiesController(Context context, RetrofitViewModel retrofitViewModel) {
        this.context = context;
        this.retrofitViewModel = retrofitViewModel;
        progressDialog = new ProgressDialog(context);
    }

    public void start() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Consts.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        APIService apiService = retrofit.create(APIService.class);

        Call<List<CitiesDataHolder>> call = apiService.GetAllState();
        call.enqueue(this);
        progressDialog.setMessage("لطفا صبر کنید...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    public void onResponse(Call<List<CitiesDataHolder>> call, Response<List<CitiesDataHolder>> response) {
        if(response.isSuccessful()) {
            List<CitiesDataHolder> cities = response.body();
           
            retrofitViewModel.setCities(cities);
        } else {
            Toast.makeText(context,"خطایی رخ داد!", Toast.LENGTH_LONG).show();
            retrofitViewModel.setSelectedFragment(Consts.ERROR_PAGE);
        }
        progressDialog.dismiss();
    }

    @Override
    public void onFailure(Call<List<CitiesDataHolder>> call, Throwable t) {
        Toast.makeText(context,"عدم امکان ارتباط با سرور", Toast.LENGTH_LONG).show();
        retrofitViewModel.setSelectedFragment(Consts.ERROR_PAGE);
        progressDialog.dismiss();
    }
}