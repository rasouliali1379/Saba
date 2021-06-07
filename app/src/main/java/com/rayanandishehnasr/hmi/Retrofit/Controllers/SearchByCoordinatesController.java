package com.rayanandishehnasr.hmi.Retrofit.Controllers;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rayanandishehnasr.hmi.DataHolder.SearchResultDataHolder;
import com.rayanandishehnasr.hmi.Retrofit.APIService;
import com.rayanandishehnasr.hmi.Utils.Consts;
import com.rayanandishehnasr.hmi.ViewModel.RetrofitViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;

public class SearchByCoordinatesController implements Callback<List<SearchResultDataHolder>> {

    private RetrofitViewModel retrofitViewModel;

    Context context;

    private ProgressDialog progressDialog;

    public SearchByCoordinatesController(Context context, RetrofitViewModel retrofitViewModel) {
        this.context = context;
        this.retrofitViewModel = retrofitViewModel;
        progressDialog = new ProgressDialog(context);
    }

    public void start(double Lat, double Long, int treatyType, int stateId) {
        Gson gson = new GsonBuilder()
        .setLenient()
        .create();

        Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(Consts.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build();

        APIService apiService = retrofit.create(APIService.class);

        Call<List<SearchResultDataHolder>> call = apiService.SearchByCoordinates(Lat, Long, treatyType, stateId, 500);
        call.enqueue(this);
        progressDialog.setMessage("لطفا صبر کنید...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    public void onResponse(Call<List<SearchResultDataHolder>> call, Response<List<SearchResultDataHolder>> response) {
        if (response.isSuccessful()) {
        List<SearchResultDataHolder> results = response.body();
        retrofitViewModel.setSearchResults(results);
        } else {
        Toast.makeText(context, "خطایی رخ داد!", Toast.LENGTH_LONG).show();
        }
        retrofitViewModel.setSelectedFragment(Consts.RESULTS_PAGE);
        progressDialog.dismiss();
    }

    @Override
    public void onFailure(Call<List<SearchResultDataHolder>> call, Throwable t) {
        Log.e(TAG, "onFailure: ", t.getCause());
        Toast.makeText(context, "عدم اتصال به سرور!", Toast.LENGTH_LONG).show();
        progressDialog.dismiss();
    }
}