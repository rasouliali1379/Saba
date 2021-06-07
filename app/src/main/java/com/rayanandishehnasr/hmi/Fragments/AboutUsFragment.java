package com.rayanandishehnasr.hmi.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rayanandishehnasr.hmi.R;
import com.rayanandishehnasr.hmi.Utils.Consts;
import com.rayanandishehnasr.hmi.Utils.TextJustification.TextViewEx;
import com.rayanandishehnasr.hmi.ViewModel.RetrofitViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class AboutUsFragment extends Fragment {

    @BindView(R.id.about_us_content_txt) TextViewEx content;
    Context context;
    RetrofitViewModel retrofitViewModel;

    public AboutUsFragment(Context context, RetrofitViewModel retrofitViewModel) {
        this.context = context;
        this.retrofitViewModel = retrofitViewModel;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_about_us, container, false);
        ButterKnife.bind(this,view);
        content.setText(context.getString(R.string.about_us_content),true);
        return view;
    }

    @OnClick(R.id.about_us_back_btn)void backToMAinMenu(){
        retrofitViewModel.setSelectedFragment(Consts.MORE_PAGE);
    }
}