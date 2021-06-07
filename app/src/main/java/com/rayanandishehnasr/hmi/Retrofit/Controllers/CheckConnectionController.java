package com.rayanandishehnasr.hmi.Retrofit.Controllers;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rayanandishehnasr.hmi.Retrofit.APIService;
import com.rayanandishehnasr.hmi.Utils.Consts;
import com.rayanandishehnasr.hmi.ViewModel.RetrofitViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;

public class CheckConnectionController implements Callback<Integer> {


    Context context;
    private RetrofitViewModel retrofitViewModel;

    private ProgressDialog progressDialog;

    public CheckConnectionController(Context context, RetrofitViewModel retrofitViewModel) {
        this.context = context;
        this.retrofitViewModel = retrofitViewModel;
        progressDialog = new ProgressDialog(context);
    }

    public void start(int requestCode) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Consts.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        APIService apiService = retrofit.create(APIService.class);

        Call<Integer> call = apiService.CheckConnection(requestCode);
        call.enqueue(this);
        progressDialog.setMessage("لطفا صبر کنید...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    public void onResponse(Call<Integer> call, Response<Integer> response) {
        if (response.isSuccessful()) {
            retrofitViewModel.setConnectionCheckResult(response.body());
        } else {
            retrofitViewModel.setConnectionCheckResult(Consts.CONNECTION_FAIL_CODE);
            Toast.makeText(context, "خطایی رخ داد!", Toast.LENGTH_LONG).show();
        }
        progressDialog.dismiss();
    }

    @Override
    public void onFailure(Call<Integer> call, Throwable t) {
        Log.e(TAG, "onFailure: ", t.getCause());
        Toast.makeText(context, "عدم اتصال به سرور!", Toast.LENGTH_LONG).show();
        retrofitViewModel.setConnectionCheckResult(Consts.CONNECTION_FAIL_CODE);
        progressDialog.dismiss();
    }
}