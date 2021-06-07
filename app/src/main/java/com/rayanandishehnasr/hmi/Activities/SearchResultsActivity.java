package com.rayanandishehnasr.hmi.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.rayanandishehnasr.hmi.DataHolder.SearchQueryDataModel;
import com.rayanandishehnasr.hmi.DataHolder.SearchResultDataHolder;
import com.rayanandishehnasr.hmi.Fragments.DetailsFragment;
import com.rayanandishehnasr.hmi.Fragments.ResultsFragment;
import com.rayanandishehnasr.hmi.R;
import com.rayanandishehnasr.hmi.Retrofit.Controllers.SearchByCoordinatesController;
import com.rayanandishehnasr.hmi.Retrofit.Controllers.SearchByFilterController;
import com.rayanandishehnasr.hmi.Utils.Consts;
import com.rayanandishehnasr.hmi.ViewModel.Factory.RetrofitViewModelFactory;
import com.rayanandishehnasr.hmi.ViewModel.RetrofitViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchResultsActivity extends AppCompatActivity {



    @BindView(R.id.search_result_back_btn)ImageView backBtn;
    @BindView(R.id.search_result_toolbar_title)TextView toolbarTitle;

    RetrofitViewModel retrofitViewModel;
    List<SearchResultDataHolder> results;
    SearchResultDataHolder selectedResult;
    private int backFunction, stateId, treatyType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        stateId = intent.getIntExtra("stateId", 1);
        treatyType = intent.getIntExtra("TreatyType", 1);
        initListeners();
        initViewModels();
        receiveData();
        initObservers();
    }

    private void initObservers() {
        retrofitViewModel.getSelectedFragment().observe(this, this::switchFragment);
        retrofitViewModel.getSelectedSearchResult().observe(this, this::getSelectedSearchResult);
        retrofitViewModel.getSearchResults().observe(this, results -> {
            if(results != null){
                this.results = results;
                switchFragment(Consts.RESULTS_PAGE);
            }
        });
    }

    private void getSelectedSearchResult(int position) {
        selectedResult = results.get(position);
        switchFragment(Consts.DETAIL_PAGE);
    }

    private void initListeners() {
        backBtn.setOnClickListener( v-> {
            retrofitViewModel.setScrollPosition(-1);
            finish();
        });
    }



    private void initViewModels() {
        retrofitViewModel = new ViewModelProvider(this, new RetrofitViewModelFactory(getApplication())).get(RetrofitViewModel.class);
    }

    private void receiveData() {
        Intent intent = getIntent();

        if (intent.getBooleanExtra("search_by_coordinates", false)){
            double Lat  = intent.getDoubleExtra("lat", Consts.TEHRAN_LOCATION_LAT);
            double Long = intent.getDoubleExtra("long", Consts.TEHRAN_LOCATION_LONG);
            new SearchByCoordinatesController(this, retrofitViewModel).start(Lat, Long, treatyType, stateId);
        } else {
            SearchQueryDataModel query = new SearchQueryDataModel();
            query.setTreatyType(treatyType);
            query.setMunZone(intent.getIntExtra("MunZone",1));
            query.setZone(intent.getIntExtra("Zone", -1));
            query.setStartPrice(intent.getIntExtra("StartPrice",-1));
            query.setEndPrice(intent.getIntExtra("EndPrice", -1));
            query.setStartTotalPrice(intent.getIntExtra("StartTotalPrice",-1));
            query.setEndTotalPrice(intent.getIntExtra("EndTotalPrice", -1));
            query.setStartDate(intent.getStringExtra("StartDate"));
            query.setEndDate(intent.getStringExtra("EndDate"));
            query.setStartAgeInTreatyType(intent.getIntExtra("StartAgeInTreatyType", -1));
            query.setEndAgeInTreatyType(intent.getIntExtra("EndAgeInTreatyType", -1));
            new SearchByFilterController(this, retrofitViewModel).start(query,stateId);
        }
    }

    private void switchFragment(int which){
        Fragment fragment = null;
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();

        switch (which){
            case Consts.RESULTS_PAGE:
                if (treatyType == 1){
                    fragment = new ResultsFragment(this, retrofitViewModel, results, Consts.TREATY_TYPE_SELL);
                } else {
                    fragment = new ResultsFragment(this, retrofitViewModel, results, Consts.TREATY_TYPE_RENT);
                }
                transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_form_right);

                backBtn.setImageResource(R.drawable.ic_close_orange);

                toolbarTitle.setText("نتیجه جستجو");
                backBtn.setOnClickListener(v-> finish());
                backFunction = Consts.BACK_FUNTION_FINISH;
                break;
            case Consts.DETAIL_PAGE:
                if (treatyType == 1){
                    fragment = new DetailsFragment(this, selectedResult, Consts.TREATY_TYPE_SELL);
                } else {
                    fragment = new DetailsFragment(this, selectedResult, Consts.TREATY_TYPE_RENT);
                }
                transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_form_right);
                backBtn.setImageResource(R.drawable.ic_back);
                backBtn.setOnClickListener(v-> switchFragment(Consts.RESULTS_PAGE));
                toolbarTitle.setText("جزییات");
                backFunction = Consts.BACK_FUNTION_SWITCH_FRAGMENT;
                break;
        }

        transaction.replace(R.id.results_root_layout, fragment);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        switch (backFunction){
            case Consts.BACK_FUNTION_FINISH :
                retrofitViewModel.setScrollPosition(-1);
                finish();
                break;
            case Consts.BACK_FUNTION_SWITCH_FRAGMENT :
                switchFragment(Consts.RESULTS_PAGE);
                break;
        }
    }
}