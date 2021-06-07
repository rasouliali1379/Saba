package com.rayanandishehnasr.hmi.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rayanandishehnasr.hmi.Activities.MainActivity;
import com.rayanandishehnasr.hmi.DataHolder.CitiesDataHolder;
import com.rayanandishehnasr.hmi.DataHolder.SelectorDialogDataHolder;
import com.rayanandishehnasr.hmi.R;
import com.rayanandishehnasr.hmi.Retrofit.Controllers.CitiesController;
import com.rayanandishehnasr.hmi.Utils.Consts;
import com.rayanandishehnasr.hmi.ViewModel.RetrofitViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SplashFragment extends Fragment {

    Context context;
    RetrofitViewModel retrofitViewModel;

    @BindView(R.id.main_activity_toolbar_city_name_txt) TextView cityNameTxt;

    public SplashFragment(Context context, RetrofitViewModel retrofitViewModel) {
        this.context = context;
        this.retrofitViewModel = retrofitViewModel;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_splash, container, false);
        ButterKnife.bind(this, view);
        initObservers();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        checkConnection();
    }

    private void initObservers() {
        retrofitViewModel.getSelectedCity().observe(this, id -> {
            if(id > 0){
                MainActivity.selectedCityId = id;
                cityNameTxt.setText(getSelectedItem(id).getStrStateName());
            }
        });
    }

    private void checkConnection() {
//        new CheckConnectionController(context, retrofitViewModel).start(Consts.CONNECTION_REQUEST_CODE);
//        retrofitViewModel.getConnectionCheckResult().observe(this, result -> {
//                if(result == Consts.CONNECTION_REQUEST_CODE){
                    new CitiesController(context, retrofitViewModel).start();
//                }
//                else if (result == Consts.CONNECTION_FAIL_CODE) {
//                    retrofitViewModel.setSelectedFragment(Consts.ERROR_PAGE);
//                }
//        });
    }

    private List<SelectorDialogDataHolder> prepareList(List<CitiesDataHolder> cities) {
        List<SelectorDialogDataHolder> states = new ArrayList<>();
        for (int i = 0; i < cities.size(); i++){
            states.add(new SelectorDialogDataHolder(cities.get(i).getStrStateName(), cities.get(i).getTiState()));
        }
        return states;
    }

    private CitiesDataHolder getSelectedItem(int id) {
        List<CitiesDataHolder> cities = retrofitViewModel.getCities().getValue();
        if (cities != null) {
            for (int i = 0 ; i < cities.size(); i++){
                if (id == cities.get(i).getTiState()){
                    return cities.get(i);
                }
            }
        }
        return null;
    }

    @OnClick(R.id.main_activity_toolbar_city_btn)void openCityDialog() {
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        androidx.fragment.app.Fragment prev = getChildFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        SelectorDialogFragment selectorDialogFragment = new SelectorDialogFragment(context, "انتخاب شهر", prepareList(retrofitViewModel.getCities().getValue()), retrofitViewModel, Consts.SELECT_CITY);
        selectorDialogFragment.show(ft, "dialog");
    }

    @OnClick(R.id.splash_fragment_enter_btn)void enterApp(){
        retrofitViewModel.setSelectedFragment(Consts.HOME_PAGE);
    }
}