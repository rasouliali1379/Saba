package com.rayanandishehnasr.hmi.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rayanandishehnasr.hmi.R;
import com.rayanandishehnasr.hmi.Utils.Consts;
import com.rayanandishehnasr.hmi.ViewModel.RetrofitViewModel;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ContactUsFragment extends Fragment {
    Context context;
    RetrofitViewModel retrofitViewModel;

    public ContactUsFragment(Context context, RetrofitViewModel retrofitViewModel) {
        this.context = context;
        this.retrofitViewModel = retrofitViewModel;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_contact_us, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    @OnClick(R.id.contact_us_back_btn)void backToMainMenu(){
        retrofitViewModel.setSelectedFragment(Consts.MORE_PAGE);
    }
}