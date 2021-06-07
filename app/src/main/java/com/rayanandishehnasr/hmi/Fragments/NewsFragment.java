package com.rayanandishehnasr.hmi.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rayanandishehnasr.hmi.R;
import com.rayanandishehnasr.hmi.ViewModel.RetrofitViewModel;


public class NewsFragment extends Fragment {

    public NewsFragment(Context context, RetrofitViewModel retrofitViewModel) {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_news, container, false);
    }
}