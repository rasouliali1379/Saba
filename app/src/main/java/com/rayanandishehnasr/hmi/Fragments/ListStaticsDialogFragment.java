package com.rayanandishehnasr.hmi.Fragments;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;
import com.rayanandishehnasr.hmi.Adapters.ListStaticsAdapter;
import com.rayanandishehnasr.hmi.DataHolder.XnYDataHolder;
import com.rayanandishehnasr.hmi.R;
import com.rayanandishehnasr.hmi.Utils.Consts;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ListStaticsDialogFragment extends DialogFragment {

    @BindView(R.id.list_statics_title)TextView title;
    @BindView(R.id.list_statics_number_label)TextView numberLabel;
    @BindView(R.id.list_statics_nothing_to_show_txt)TextView nothingToShow;

    @BindView(R.id.list_statics_recyclerview)RecyclerView recyclerView;

    Context context;
    List<XnYDataHolder> entries;
    int whichChart;

    public ListStaticsDialogFragment(Context context, List<XnYDataHolder> entries, int whichChart) {
        this.context = context;
        this.entries = entries;
        this.whichChart = whichChart;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_statics, container, false);
        ButterKnife.bind(this, view);
        setTitle();
        setLabel();

        if (entries.size() > 0){
            recyclerViewSetup();
            nothingToShow.setVisibility(View.GONE);
        }

        return view;
    }

    private void setLabel() {
        String numberContent = null;

        switch(whichChart){
            case Consts.TRADE_PER_ZONE:
                numberContent = "تعداد معامله";
                break;
            case Consts.AVERAGE_PER_ZONE:
                numberContent = "متوسط قیمت(میلیون تومان)";
                break;
        }

        numberLabel.setText(numberContent);
    }

    private void recyclerViewSetup() {
        ListStaticsAdapter listStaticsAdapter = new ListStaticsAdapter(context, entries);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(listStaticsAdapter);
    }

    private void setTitle() {
        String titleContent = null;
        switch(whichChart){
            case Consts.TRADE_PER_ZONE:
                titleContent = "نمودار تعداد معاملات به تفکیک محله";
                break;
            case Consts.AVERAGE_PER_ZONE:
                titleContent = "نمودار متوسط قیمت به تفکیک محله";
                break;
        }

        title.setText(titleContent);
    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
    }

}