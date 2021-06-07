package com.rayanandishehnasr.hmi.Fragments;

import android.app.ActionBar;
import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rayanandishehnasr.hmi.Activities.MainActivity;
import com.rayanandishehnasr.hmi.Activities.SearchByFilterActivity;
import com.rayanandishehnasr.hmi.DataHolder.GetAllZoneItem;
import com.rayanandishehnasr.hmi.DataHolder.GetAllmunZoneItem;
import com.rayanandishehnasr.hmi.DataHolder.SelectorDialogDataHolder;
import com.rayanandishehnasr.hmi.R;
import com.rayanandishehnasr.hmi.Retrofit.Controllers.LocationController;
import com.rayanandishehnasr.hmi.Utils.Consts;
import com.rayanandishehnasr.hmi.Utils.JalaliConverter;
import com.rayanandishehnasr.hmi.ViewModel.ChartsViewModel;
import com.rayanandishehnasr.hmi.ViewModel.RetrofitViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.hamsaa.persiandatepicker.Listener;
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog;
import ir.hamsaa.persiandatepicker.util.PersianCalendar;


public class ChartSettingsDialogFragment extends DialogFragment {

    @BindView(R.id.chart_settings_date_et)EditText dateEt;
    @BindView(R.id.chart_settings_start_date_et)EditText startDateEt;
    @BindView(R.id.chart_settings_end_date_et)EditText endDateEt;
    @BindView(R.id.chart_settings_year_et)EditText yearEt;

    @BindView(R.id.char_setting_section_selector_layout)LinearLayout zoneSelectorLayout;
    @BindView(R.id.chart_settings_date_layout)LinearLayout dateLayout;
    @BindView(R.id.chart_settings_date_period_layout)LinearLayout datePeriodLayout;
    @BindView(R.id.chart_settings_year_layout)LinearLayout yearLayout;


    @BindView(R.id.chart_setting_section_txt) TextView sectionTxt;

    @BindView(R.id.chart_setting_clear_zone_btn)ImageView clearZoneSelectionBtn;

    String startDate = "", endDate = "";
    int selectedSection = 0;

    //Constructor
    Context context;
    int whichChart;
    RetrofitViewModel retrofitViewModel;
    ChartsViewModel chartsViewModel;


    public ChartSettingsDialogFragment(Context context, int whichChart, RetrofitViewModel retrofitViewModel, ChartsViewModel chartsViewModel) {
        this.context = context;
        this.whichChart = whichChart;
        this.chartsViewModel = chartsViewModel;
        this.retrofitViewModel = retrofitViewModel;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chart_settings_dialog, container, false);
        ButterKnife.bind(this, view);
        clickListeners();
        defineEnvironment();
        return view;
    }

    private void clickListeners() {
        yearEt.setOnClickListener(this::openPopUPYearPicker);
        dateEt.setOnClickListener(v -> openDatePicker(Consts.DATE));
        startDateEt.setOnClickListener(v -> openDatePicker(Consts.DATE_PERIOD_START));
        endDateEt.setOnClickListener(v -> openDatePicker(Consts.DATE_PERIOD_END));
    }

    private void openPopUPYearPicker(View v) {
        PopupMenu popup = new PopupMenu(context, v);
        int year = Integer.parseInt(yearEt.getText().toString()) - 1395;
        for(int i = 0 ; i <= year; i++){
            int cal = 1395 + i;
            popup.getMenu().add(i, i, year - i, String.valueOf(cal));
        }

        popup.setOnMenuItemClickListener(item -> {
            yearEt.setText(item.getTitle());
            return false;
        });
        popup.show();
    }

    private void openDatePicker(int which) {
        PersianCalendar date = new PersianCalendar();

        switch(which){
            case Consts.DATE:
            case Consts.DATE_PERIOD_START:
                date.setPersianDate(Integer.parseInt(startDate.split("/")[0]),  Integer.parseInt(startDate.split("/")[1]), 0);
                break;
            case Consts.DATE_PERIOD_END:
                date.setPersianDate(Integer.parseInt(endDate.split("/")[0]),  Integer.parseInt(endDate.split("/")[1]), 0);
                break;
        }
        PersianDatePickerDialog picker = new PersianDatePickerDialog(context)
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
                            Toast.makeText(context, "تاریخ انتخاب شده نباید از تاریخ امروز جلو تر باشد",Toast.LENGTH_SHORT).show();
                        } else {
                            if (persianCalendar.getPersianMonth() < 10){
                                monthStr = "0" + persianCalendar.getPersianMonth();
                            } else {
                                monthStr = String.valueOf(persianCalendar.getPersianMonth());
                            }
                        }

                        switch (which){
                            case Consts.DATE:
                                dateEt.setText(persianCalendar.getPersianYear() + "/" + monthStr);
                                break;
                            case Consts.DATE_PERIOD_START:
                                startDateEt.setText(persianCalendar.getPersianYear() + "/" + monthStr);
                                break;
                            case Consts.DATE_PERIOD_END:
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

    private String getTodayDate() {
        String[] todayDate = JalaliConverter.getCurrentShamsidate().split("/");
        String date;
        if(Integer.parseInt(todayDate[1]) < 10){
            date = Integer.parseInt(todayDate[0]) + "/0" +  Integer.parseInt(todayDate[1]);
        } else {
            date = Integer.parseInt(todayDate[0]) + "/" +  Integer.parseInt(todayDate[1]);
        }
        return date;
    }

    private String getCurrentYear() {
        String[] todayDate = JalaliConverter.getCurrentShamsidate().split("/");
        return todayDate[0];
    }

    private String getPreviousMonthDate() {
        String[] todayDate = JalaliConverter.getCurrentShamsidate().split("/");
        String date;
        int month = Integer.parseInt(todayDate[1]) - 1;
        if( month < 10){
            date = Integer.parseInt(todayDate[0]) + "/0" +  month;
        } else {
            date = Integer.parseInt(todayDate[0]) + "/" +  month;
        }
        return date;
    }

    private void defineEnvironment() {
        switch (whichChart){
            case Consts.TRADE_PER_SECTION:
            case Consts.AVERAGE_PER_SECTION:
                firstEnvironment();
                break;
            case Consts.AVERAGE_PER_MONTH:
            case Consts.TRADE_PER_MONTH:
                secondEnvironment();
                break;
            case Consts.TRADE_PER_ZONE:
            case Consts.AVERAGE_PER_ZONE:
                thirdEnvironment();
                break;
        }
    }



    private void firstEnvironment() {
        datePeriodLayout.setVisibility(View.GONE);
        zoneSelectorLayout.setVisibility(View.GONE);
        yearLayout.setVisibility(View.GONE);
        startDate = getTodayDate();
        dateEt.setText(startDate);
    }

    private void secondEnvironment() {
        datePeriodLayout.setVisibility(View.GONE);
        zoneSelectorLayout.setVisibility(View.GONE);
        dateLayout.setVisibility(View.GONE);
        yearEt.setText(getCurrentYear());
    }



    private void thirdEnvironment(){
        dateLayout.setVisibility(View.GONE);
        yearLayout.setVisibility(View.GONE);
        endDate = getTodayDate();
        startDate = getPreviousMonthDate();

        Log.e("startDate", startDate);
        Log.e("endDate", endDate);
        endDateEt.setText(endDate);
        startDateEt.setText(startDate);
        initObserver();
        getLocations();
    }

    private void initObserver() {
        retrofitViewModel.getSelectedSection().observe(this, selectedZoneId -> {
            if (selectedZoneId > 0){
                selectedSection = selectedZoneId;
                sectionTxt.setText(getSelectedSection(selectedZoneId));

                clearZoneSelectionBtn.setVisibility(View.VISIBLE);
                clearZoneSelectionBtn.setOnClickListener(v -> {
                    selectedSection = 0;
                    sectionTxt.setText("انتخاب کنید");
                    clearZoneSelectionBtn.setVisibility(View.INVISIBLE);
                    clearZoneSelectionBtn.setOnClickListener(null);
                });
            } else {
                selectedSection = 0;
                sectionTxt.setText("انتخاب کنید");
            }
        });
    }

    private String getSelectedSection(Integer id) {
        List<GetAllmunZoneItem> sections = retrofitViewModel.getLocations().getValue().getGetAllmunZone();
        for (int i = 0; i < sections.size(); i++){
            if (sections.get(i).getIMunZone() == id){
                return "منطقه " + sections.get(i).getTiMuncipalty();
            }
        }
        return null;
    }

    private void getLocations() {
        LocationController locationController = new LocationController(context, retrofitViewModel);
        locationController.start(MainActivity.selectedCityId);
    }

    @OnClick(R.id.chart_settings_cancel_btn)void cancelSettings(){ dismiss(); }

    @OnClick(R.id.chart_settings_ok_btn)void executeSettings(){

        switch (whichChart){
            case Consts.TRADE_PER_SECTION:
                chartsViewModel.setTradePerSectionSelectedDate(dateEt.getText().toString());
                break;
            case Consts.AVERAGE_PER_SECTION:
                chartsViewModel.setAveragePerSectionSelectedDate(dateEt.getText().toString());
                break;
            case Consts.TRADE_PER_MONTH:
                chartsViewModel.setTradePerMonthSelectedDate(yearEt.getText().toString());
                break;
            case Consts.AVERAGE_PER_MONTH:
                chartsViewModel.setAveragePerMonthSelectedDate(yearEt.getText().toString());
                break;
            case Consts.TRADE_PER_ZONE:
                chartsViewModel.setTradePerZoneSelectedDate(startDateEt.getText().toString());
                chartsViewModel.setTradePerZoneSelectedEndDate(endDateEt.getText().toString());
                if (selectedSection > 0){
                    chartsViewModel.setTradePerZoneSelectedZone(selectedSection);
                } else {
                    Toast.makeText(context,"یک منطقه انتخاب کنید!", Toast.LENGTH_SHORT).show();
                    return;
                }
                break;
            case Consts.AVERAGE_PER_ZONE:
                chartsViewModel.setAveragePerZoneSelectedDate(startDateEt.getText().toString());
                chartsViewModel.setAveragePerZoneSelectedEndDate(endDateEt.getText().toString());
                if (selectedSection > 0){
                    chartsViewModel.setAveragePerZoneSelectedZone(selectedSection);
                } else {
                    Toast.makeText(context,"یک منطقه انتخاب کنید!", Toast.LENGTH_SHORT).show();
                    return;
                }
                break;
        }
        chartsViewModel.setOpenChart(whichChart);
        dismiss();
    }

    @OnClick(R.id.chart_settings_section_selector_btn)void openZoneSelector(){
        if (retrofitViewModel.getLocations().getValue().getGetAllmunZone() != null){
            FragmentTransaction ft = getChildFragmentManager().beginTransaction();
            androidx.fragment.app.Fragment prev = getChildFragmentManager().findFragmentByTag("dialog");
            if (prev != null) {
                ft.remove(prev);
            }
            ft.addToBackStack(null);
            SelectorDialogFragment selectorDialogFragment = new SelectorDialogFragment(context, "انتخاب محله", prepareSectionList(retrofitViewModel.getLocations().getValue().getGetAllmunZone()),retrofitViewModel, Consts.CHART_SELECT_SECTION);
            selectorDialogFragment.show(ft, "dialog");
        } else {
            Toast.makeText(context,"اطلاعاتی از سرور دریافت نشد . اتصال خود با اینترنت را بررسی کنید", Toast.LENGTH_LONG).show();
            dismiss();
        }
    }

    private List<SelectorDialogDataHolder> prepareSectionList(List<GetAllmunZoneItem> getAllmunZone) {
        List<SelectorDialogDataHolder> sections = new ArrayList<>();
        for (int i = 0; i < getAllmunZone.size(); i++){
            sections.add(new SelectorDialogDataHolder( "منطقه " + getAllmunZone.get(i).getTiMuncipalty(), getAllmunZone.get(i).getIMunZone()));
        }
        return sections;
    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
    }
}