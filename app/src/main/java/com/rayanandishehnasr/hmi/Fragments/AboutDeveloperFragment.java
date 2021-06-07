package com.rayanandishehnasr.hmi.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rayanandishehnasr.hmi.Activities.MainActivity;
import com.rayanandishehnasr.hmi.R;
import com.rayanandishehnasr.hmi.Utils.Consts;
import com.rayanandishehnasr.hmi.Utils.TextJustification.TextViewEx;
import com.rayanandishehnasr.hmi.ViewModel.RetrofitViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AboutDeveloperFragment extends Fragment {

    @BindView(R.id.about_developer_content_txt)TextViewEx content;

    Context context;
    RetrofitViewModel retrofitViewModel;


    public AboutDeveloperFragment(Context context, RetrofitViewModel retrofitViewModel) {
        this.context = context;
        this.retrofitViewModel = retrofitViewModel;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about_developer, container, false);
        ButterKnife.bind(this, view);
        content.setText(context.getResources().getString(R.string.about_developer_content), true);
        return view;
    }

    @OnClick(R.id.about_devloper_site_btn)void openSite (){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://rayanandisheh.com"));
        startActivity(browserIntent);
    }

    @OnClick(R.id.about_developer_back_btn)void backToMorePage (){
        retrofitViewModel.setSelectedFragment(Consts.MORE_PAGE);
    }
}