package com.rayanandishehnasr.hmi.Activities;

import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.rayanandishehnasr.hmi.DataHolder.GetAllZoneItem;
import com.rayanandishehnasr.hmi.DataHolder.GetAllmunZoneItem;
import com.rayanandishehnasr.hmi.DataHolder.LocationRootDataHolder;
import com.rayanandishehnasr.hmi.DataHolder.SelectorDialogDataHolder;
import com.rayanandishehnasr.hmi.Fragments.SelectorDialogFragment;
import com.rayanandishehnasr.hmi.R;
import com.rayanandishehnasr.hmi.Retrofit.Controllers.LocationController;
import com.rayanandishehnasr.hmi.Utils.Consts;
import com.rayanandishehnasr.hmi.Utils.JalaliConverter;
import com.rayanandishehnasr.hmi.ViewModel.Factory.RetrofitViewModelFactory;
import com.rayanandishehnasr.hmi.ViewModel.RetrofitViewModel;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.hamsaa.persiandatepicker.Listener;
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog;
import ir.hamsaa.persiandatepicker.util.PersianCalendar;

public class SearchByFilterActivity extends AppCompatActivity {

    @BindView(R.id.search_by_filter_close_btn) ImageView closeBtn;
    @BindView(R.id.search_by_filter_min_price_et) EditText minPriceEt;
    @BindView(R.id.search_by_filter_max_price_et) EditText maxPriceEt;
    @BindView(R.id.search_by_filter_min_total_price_et) EditText minTotalPriceEt;
    @BindView(R.id.search_by_filter_max_total_price_et) EditText maxTotalPriceEt;
    @BindView(R.id.search_by_filter_min_age_et) EditText minAgeEt;
    @BindView(R.id.search_by_filter_max_age_et) EditText maxAgeEt;
    @BindView(R.id.search_by_filter_section_txt) TextView sectionTxt;
    @BindView(R.id.search_by_filter_zone_txt) TextView zoneTxt;
    @BindView(R.id.search_by_filter_section_remove_selection_btn)ImageView removeSectionSelection;
    @BindView(R.id.search_by_filter_zone_remove_selection_btn)ImageView removeZoneSelection;
    @BindView(R.id.search_by_filter_start_date_et) EditText startDateEt;
    @BindView(R.id.search_by_filter_end_date_et) EditText endDateEt;
    @BindView(R.id.search_by_filter_trade_type_rg)RadioGroup tradeTypeRg;
    @BindView(R.id.search_by_filter_scrollview)ScrollView scrollView;
    @BindView(R.id.search_by_filter_choose_section_error)TextView chooseSectionErrorTxt;
    @BindView(R.id.search_by_filter_date_error)TextView dateErrorTxt;
    @BindView(R.id.search_by_filter_price_label)TextView priceLayoutLabel;
    @BindView(R.id.search_by_filter_total_price_label)TextView totalPriceLayoutLabel;
    RetrofitViewModel retrofitViewModel;

    GetAllZoneItem selectedZone;
    GetAllmunZoneItem selectedSection;
    PersianCalendar selectedStartDate, selectedEndDate;
    LocationRootDataHolder locations;

    MutableLiveData<Integer> selectedZoneObserver, selectedSectionObserver;

    int stateId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_by_filter);
        ButterKnife.bind(this);
        tradeTypeRg.check(R.id.search_by_filter_buy_rb);
        Intent intent = getIntent();
        stateId = intent.getIntExtra("stateId", 1);
        new Handler().postDelayed(()-> {
            initListeners();
            initViewModels();
            initObservers();
            getLocations();
        }, 500);
    }

    private void fillDefaultData() {
        selectSection(1);
        PersianCalendar endDate = new PersianCalendar();
        String[] todayDate = JalaliConverter.getCurrentShamsidate().split("/");
        endDate.setPersianDate(Integer.parseInt(todayDate[0]), Integer.parseInt(todayDate[1]), Integer.parseInt(todayDate[2]));
        selectedEndDate = endDate;
        if(String.valueOf(selectedEndDate.getPersianMonth()).length() == 1) {
            endDateEt.setText(selectedEndDate.getPersianYear() + "/0" + selectedEndDate.getPersianMonth());
        } else {
            endDateEt.setText(selectedEndDate.getPersianYear() + "/" + selectedEndDate.getPersianMonth());
        }

        PersianCalendar startDate = new PersianCalendar();
        startDate.setPersianDate(Integer.parseInt(todayDate[0]), Integer.parseInt(todayDate[1]) - 1, Integer.parseInt(todayDate[2]));
        selectedStartDate = startDate;
        if (String.valueOf(selectedStartDate.getPersianMonth()).length() == 1){
            startDateEt.setText(selectedStartDate.getPersianYear() + "/0" + selectedStartDate.getPersianMonth());
        } else {
            startDateEt.setText(selectedStartDate.getPersianYear() + "/" + selectedStartDate.getPersianMonth());
        }

    }

    private void initObservers() {
        selectedSectionObserver.observe(this, this::selectSection);

        selectedZoneObserver.observe(this, id -> {
            selectedZone = getSelectedZone(id);
            selectedSection = getSelectedSection(selectedZone.getIMunZone());
            if (selectedZone != null && selectedSection != null){
                zoneTxt.setText(selectedZone.getStrZoneName());
                sectionTxt.setText("منطقه " + selectedSection.getTiMuncipalty());
                removeZoneSelection.setVisibility(View.VISIBLE);
                removeZoneSelection.setOnClickListener(v-> removeZoneSelectionFun());
                removeSectionSelection.setVisibility(View.VISIBLE);
                removeSectionSelection.setOnClickListener(v-> removeSectionSelectionFun());
            }
        });

        retrofitViewModel.getConnectionCheckResult().observe(this, code -> {
            if (code == Consts.CONNECTION_REQUEST_CODE){
                showMessage("ارتباط با سرور بر قرار شد! تلاش برای دریافت اطلاعات");
                getLocations();
            } else {
                retrofitViewModel.setSelectedFragment(Consts.ERROR_PAGE);
                finish();
            }
        });

        retrofitViewModel.getLocations().observe(this, locations -> {
            this.locations = locations;
            fillDefaultData();
        });
    }

    private void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void selectSection(int id) {
        selectedSection = getSelectedSection(id);
        if (selectedSection != null){
            sectionTxt.setText("منطقه " + selectedSection.getTiMuncipalty());
            selectedZone = null;
            zoneTxt.setText("انتخاب کنید");
            removeSectionSelection.setVisibility(View.VISIBLE);
            removeSectionSelection.setOnClickListener(v-> removeSectionSelectionFun());
        }
    }

    private void removeSectionSelectionFun() {
        selectedSection = null;
        sectionTxt.setText("انتخاب کنید");
        removeSectionSelection.setVisibility(View.INVISIBLE);
        removeZoneSelectionFun();
    }

    private void removeZoneSelectionFun() {
        selectedZone = null;
        zoneTxt.setText("انتخاب کنید");
        removeZoneSelection.setVisibility(View.INVISIBLE);
    }

    private GetAllmunZoneItem getSelectedSection(Integer id) {
        List<GetAllmunZoneItem> sections = locations.getGetAllmunZone();
        for (int i = 0; i < sections.size(); i++){
            if (sections.get(i).getIMunZone() == id){
                return sections.get(i);
            }
        }
        return null;
    }

    private GetAllZoneItem getSelectedZone(Integer id) {
        List<GetAllZoneItem> zones = locations.getGetAllZone();
        for (int i = 0; i < zones.size(); i++){
            if (zones.get(i).getIZone() == id){
                return zones.get(i);
            }
        }
        Log.e( "getSelectedZone: ", "null");
        return null;
    }

    private void getLocations() {
        LocationController locationController = new LocationController(this, retrofitViewModel);
        locationController.start(stateId);
    }

    private void initViewModels() {
        retrofitViewModel = new ViewModelProvider(this, new RetrofitViewModelFactory(getApplication())).get(RetrofitViewModel.class);
        selectedSectionObserver = retrofitViewModel.getSelectedSection();
        selectedZoneObserver = retrofitViewModel.getSelectedZone();
    }

    private void initListeners() {
        closeBtn.setOnClickListener(v -> finish());


        tradeTypeRg.setOnCheckedChangeListener((group, checkedId) -> {
            switch (tradeTypeRg.getCheckedRadioButtonId()){
                case R.id.search_by_filter_buy_rb :
                    priceLayoutLabel.setText("محدوده قیمت هر متر (میلیون تومان)");
                    totalPriceLayoutLabel.setText("محدوده قیمت کل (میلیون تومان)");
                    break;
                case R.id.search_by_filter_sell_rb :
                    priceLayoutLabel.setText("محدوده اجاره هر متر (میلیون تومان)");
                    totalPriceLayoutLabel.setText("محدوده اجاره کل (میلیون تومان)");
                    break;
            }
        });


        minPriceEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                minPriceEt.removeTextChangedListener(this);

                try {
                    String originalString = s.toString();

                    Long longval;
                    if (originalString.contains(",")) {
                        originalString = originalString.replaceAll(",", "");
                    }
                    longval = Long.parseLong(originalString);

                    minPriceEt.setText(String.valueOf(NumberFormat.getInstance(Locale.US).format(longval)));
                    minPriceEt.setSelection(minPriceEt.getText().length());

                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                }
                minPriceEt.addTextChangedListener(this);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        maxPriceEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                maxPriceEt.removeTextChangedListener(this);

                try {
                    String originalString = s.toString();

                    Long longval;
                    if (originalString.contains(",")) {
                        originalString = originalString.replaceAll(",", "");
                    }
                    longval = Long.parseLong(originalString);

                    maxPriceEt.setText(String.valueOf(NumberFormat.getInstance(Locale.US).format(longval)));
                    maxPriceEt.setSelection(maxPriceEt.getText().length());

                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                }
                maxPriceEt.addTextChangedListener(this);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        startDateEt.setOnClickListener(v -> showDatePicker(Consts.START_DATE));
        endDateEt.setOnClickListener(v -> showDatePicker(Consts.END_DATE));
    }
    String[] todayDate;
    private void showDatePicker(int which) {
        switch (which){
            case Consts.START_DATE:
                if (selectedStartDate == null){
                    todayDate = JalaliConverter.getCurrentShamsidate().split("/");
                } else {
                    todayDate = new String[2];
                    todayDate[0] = String.valueOf(selectedStartDate.getPersianYear());
                    todayDate[1] = String.valueOf(selectedStartDate.getPersianMonth());
                }
                break;
            case Consts.END_DATE:
                if (selectedEndDate == null){
                    todayDate = JalaliConverter.getCurrentShamsidate().split("/");
                } else {
                    todayDate = new String[2];
                    todayDate[0] = String.valueOf(selectedEndDate.getPersianYear());
                    todayDate[1] = String.valueOf(selectedEndDate.getPersianMonth());
                }
                break;
        }

        PersianCalendar date = new PersianCalendar();
        date.setPersianDate(Integer.parseInt(todayDate[0]), Integer.parseInt(todayDate[1]), 0);
        PersianDatePickerDialog picker = new PersianDatePickerDialog(this)
                .setPositiveButtonString("تایید")
                .setNegativeButton("بستن")
                .setTodayButtonVisible(true)
                .setMinYear(1390)
                .setTodayButtonVisible(false)
                //.setInitDate(date)
                .setMaxYear(PersianDatePickerDialog.THIS_YEAR)
                .setActionTextColor(getResources().getColor(R.color.colorPrimaryDark))
                .setTitleType(PersianDatePickerDialog.WEEKDAY_DAY_MONTH_YEAR)
                .setShowInBottomSheet(true)
                .setListener(new Listener() {
                    @Override
                    public void onDateSelected(PersianCalendar persianCalendar) {
                        String[] todayDate = JalaliConverter.getCurrentShamsidate().split("/");
                        String monthStr;
                        int month = Integer.parseInt(todayDate[1]);

                        if (persianCalendar.getPersianYear() == Integer.parseInt(todayDate[0]) && persianCalendar.getPersianMonth() > month){
                            if (month < 10){
                                monthStr = "0" + month;
                            } else {
                                monthStr = String.valueOf(month);
                            }
                            Toast.makeText(SearchByFilterActivity.this, "تاریخ انتخاب شده نباید از تاریخ امروز جلو تر باشد",Toast.LENGTH_SHORT).show();
                        } else {
                            if (persianCalendar.getPersianMonth() < 10){
                                monthStr = "0" + persianCalendar.getPersianMonth();
                            } else {
                                monthStr = String.valueOf(persianCalendar.getPersianMonth());
                            }
                        }

                       switch (which){
                           case 1:
                               selectedStartDate = persianCalendar;
                               startDateEt.setText(persianCalendar.getPersianYear() + "/" + monthStr);
                               break;
                           case 2:
                               selectedEndDate = persianCalendar;
                               endDateEt.setText(persianCalendar.getPersianYear() + "/" + monthStr);
                               break;
                       }
                    }

                    @Override
                    public void onDismissed() {

                    }
                });

        picker.show();
    }

    @OnClick(R.id.search_by_activity_section_selector)void openSectionSelector(){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        androidx.fragment.app.Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        SelectorDialogFragment selectorDialogFragment = new SelectorDialogFragment(this, "انتخاب منطقه", prepareSectionList(retrofitViewModel.getLocations().getValue().getGetAllmunZone()), retrofitViewModel, Consts.SELECT_SECTION);
        selectorDialogFragment.show(ft, "dialog");
    }

    private List<SelectorDialogDataHolder> prepareSectionList(List<GetAllmunZoneItem> getAllmunZone) {
        List<SelectorDialogDataHolder> zones = new ArrayList<>();
        for (int i = 0; i < getAllmunZone.size(); i++){
            zones.add(new SelectorDialogDataHolder("منطقه " + getAllmunZone.get(i).getTiMuncipalty(), getAllmunZone.get(i).getIMunZone()));
        }
        return zones;
    }

    @OnClick(R.id.search_by_activity_zone_selector)void openZoneSelector(){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        androidx.fragment.app.Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        SelectorDialogFragment selectorDialogFragment = new SelectorDialogFragment(this, "انتخاب محله", prepareZoneList(retrofitViewModel.getLocations().getValue().getGetAllZone()),retrofitViewModel, Consts.SELECT_ZONE);
        selectorDialogFragment.show(ft, "dialog");
    }

    private List<SelectorDialogDataHolder> prepareZoneList(List<GetAllZoneItem> getAllZone) {
        List<SelectorDialogDataHolder> zones = new ArrayList<>();
        if (selectedSection != null) {
            for (int i = 0; i < getAllZone.size(); i++){
                if (selectedSection.getIMunZone() == getAllZone.get(i).getIMunZone()){
                    zones.add(new SelectorDialogDataHolder(String.valueOf(getAllZone.get(i).getStrZoneName()), getAllZone.get(i).getIZone()));
                }
            }
        } else {
            for (int i = 0; i < getAllZone.size(); i++){
                zones.add(new SelectorDialogDataHolder(String.valueOf(getAllZone.get(i).getStrZoneName()), getAllZone.get(i).getIZone()));
            }
        }

        return zones;
    }

    @OnClick(R.id.search_by_filter_search_btn)void search(){
        Intent intent = new Intent(this, SearchResultsActivity.class);

        if(selectedSection != null){
            intent.putExtra("MunZone", selectedSection.getIMunZone());
            chooseSectionErrorTxt.setVisibility(View.GONE);
        } else {
            chooseSectionErrorTxt.setVisibility(View.VISIBLE);
            return;
        }

        if (startDateEt.getText() != null){
            if (startDateEt.getText().toString().length() > 0){
                intent.putExtra("StartDate", startDateEt.getText().toString());
                dateErrorTxt.setVisibility(View.GONE);
            } else {
                dateErrorTxt.setVisibility(View.VISIBLE);
                return;
            }
        } else {
            dateErrorTxt.setVisibility(View.VISIBLE);
            return;
        }

        if (endDateEt.getText() != null){
            if (endDateEt.getText().toString().length() > 0){
                intent.putExtra("EndDate", endDateEt.getText().toString());
                dateErrorTxt.setVisibility(View.GONE);
            } else {
                dateErrorTxt.setVisibility(View.VISIBLE);
                return;
            }
        } else {
            dateErrorTxt.setVisibility(View.VISIBLE);
            return;
        }

        switch (tradeTypeRg.getCheckedRadioButtonId()){
            case R.id.search_by_filter_buy_rb :
                intent.putExtra("TreatyType", 1);
                break;
            case R.id.search_by_filter_sell_rb :
                intent.putExtra("TreatyType", 2);
                break;
        }

        if (selectedZone != null){
            intent.putExtra("Zone", selectedZone.getIZone());
        } else {
            intent.putExtra("Zone", -1);
        }

        if (minPriceEt.getText() != null){
            if (minPriceEt.getText().toString().length() > 0){
                intent.putExtra("StartPrice", Integer.parseInt(minPriceEt.getText().toString().replaceAll(",", "")));
            } else {
                intent.putExtra("StartPrice", -1);
            }
        } else {
            intent.putExtra("StartPrice", -1);
        }

        if (maxPriceEt.getText() != null){
            if (maxPriceEt.getText().toString().length() > 0){
                intent.putExtra("EndPrice", Integer.parseInt(maxPriceEt.getText().toString().replaceAll(",", "")));
            } else {
                intent.putExtra("EndPrice", -1);
            }
        } else {
            intent.putExtra("EndPrice", -1);
        }

        if (minTotalPriceEt.getText() != null){
            if (minTotalPriceEt.getText().toString().length() > 0){
                intent.putExtra("StartTotalPrice", Integer.parseInt(minTotalPriceEt.getText().toString().replaceAll(",", "")));
            } else {
                intent.putExtra("StartTotalPrice", -1);
            }
        } else {
            intent.putExtra("StartTotalPrice", -1);
        }

        if (maxTotalPriceEt.getText() != null){
            if (maxTotalPriceEt.getText().toString().length() > 0){
                intent.putExtra("EndTotalPrice", Integer.parseInt(maxTotalPriceEt.getText().toString().replaceAll(",", "")));
            } else {
                intent.putExtra("EndTotalPrice", -1);
            }
        } else {
            intent.putExtra("EndTotalPrice", -1);
        }

        if (minAgeEt.getText() != null){
            if (minAgeEt.getText().toString().length() > 0){
                intent.putExtra("StartAgeInTreatyType", Integer.parseInt(minAgeEt.getText().toString().replaceAll(",", "")));
            } else {
                intent.putExtra("StartAgeInTreatyType", -1);
            }
        } else {
            intent.putExtra("StartAgeInTreatyType", -1);
        }

        if (maxAgeEt.getText() != null){
            if (maxAgeEt.getText().toString().length() > 0){
                intent.putExtra("EndAgeInTreatyType", Integer.parseInt(maxAgeEt.getText().toString().replaceAll(",", "")));
            } else {
                intent.putExtra("EndAgeInTreatyType", -1);
            }
        } else {
            intent.putExtra("EndAgeInTreatyType", -1);
        }
        intent.putExtra("stateId", stateId);
        startActivity(intent);
    }
}