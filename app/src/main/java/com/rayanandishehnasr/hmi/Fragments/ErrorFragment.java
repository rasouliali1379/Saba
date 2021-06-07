package com.rayanandishehnasr.hmi.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.rayanandishehnasr.hmi.R;
import com.rayanandishehnasr.hmi.Utils.Consts;
import com.rayanandishehnasr.hmi.Utils.Utility;
import com.rayanandishehnasr.hmi.ViewModel.RetrofitViewModel;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class ErrorFragment extends Fragment {
    Context context;
    RetrofitViewModel retrofitViewModel;

    public ErrorFragment(Context context, RetrofitViewModel retrofitViewModel) {
        this.context = context;
        this.retrofitViewModel = retrofitViewModel;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_error, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.error_fragment_try_again_btn) void tryAgain() {
        if (Utility.isInternetAvailable(getContext())){
            retrofitViewModel.setSelectedFragment(Consts.SPLASH_PAGE);
        } else {
            Toast.makeText(context,"همچنان به اینترنت متصل نیستید :(", Toast.LENGTH_LONG).show();
        }
    }
}