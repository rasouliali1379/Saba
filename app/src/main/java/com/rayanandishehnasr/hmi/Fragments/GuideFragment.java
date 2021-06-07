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

public class GuideFragment extends Fragment {

    Context context;
    RetrofitViewModel retrofitViewModel;

    @BindView(R.id.guide_cotent1)TextViewEx guideContent1;
    @BindView(R.id.guide_cotent2)TextViewEx guideContent2;
    @BindView(R.id.guide_cotent3)TextViewEx guideContent3;
    @BindView(R.id.guide_cotent5)TextViewEx guideContent5;


    public GuideFragment(Context context, RetrofitViewModel retrofitViewModel) {
        this.context = context;
        this.retrofitViewModel = retrofitViewModel;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_guide, container, false);
        ButterKnife.bind(this, view);
        guideContent1.setText(context.getResources().getString(R.string.guide_cotent1), true);
        guideContent2.setText(context.getResources().getString(R.string.guide_cotent2), true);
        guideContent3.setText(context.getResources().getString(R.string.guide_cotent3), true);
        guideContent5.setText(context.getResources().getString(R.string.guide_cotent5), true);

        return view;
    }

    @OnClick(R.id.guide_back_btn)void backToMainMenu() {
        retrofitViewModel.setSelectedFragment(Consts.MORE_PAGE);
    }


}