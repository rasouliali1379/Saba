package com.rayanandishehnasr.hmi.Fragments;

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

public class MoreFragment extends Fragment {
    RetrofitViewModel retrofitViewModel;

    public MoreFragment(RetrofitViewModel retrofitViewModel) {
        this.retrofitViewModel = retrofitViewModel;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_more, container, false);
        ButterKnife.bind(this,view);

        return view;
    }

    @OnClick(R.id.about_us_btn)void aboutUs(){
        retrofitViewModel.setSelectedFragment(Consts.ABOUT_US_PAGE);

    }

    @OnClick(R.id.guide_btn)void guidePage(){
        retrofitViewModel.setSelectedFragment(Consts.GUIDE_PAGE);

    }

    @OnClick(R.id.contact_us_btn)void contactUs(){
        retrofitViewModel.setSelectedFragment(Consts.CONTACT_US_PAGE);

    }

    @OnClick(R.id.about_developer_btn)void aboutDeveloper(){
        retrofitViewModel.setSelectedFragment(Consts.ABOUT_DEV_PAGE);
    }
}