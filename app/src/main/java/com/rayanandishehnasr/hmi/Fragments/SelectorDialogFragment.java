package com.rayanandishehnasr.hmi.Fragments;

import android.app.ActionBar;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.rayanandishehnasr.hmi.Adapters.SelectorDialogAdapter;
import com.rayanandishehnasr.hmi.DataHolder.SelectorDialogDataHolder;
import com.rayanandishehnasr.hmi.R;
import com.rayanandishehnasr.hmi.Utils.Consts;
import com.rayanandishehnasr.hmi.ViewModel.RetrofitViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectorDialogFragment extends DialogFragment {

    @BindView(R.id.selector_dialog_close_btn) ImageView closeBtn;
    @BindView(R.id.selector_dialog_recyclerview) RecyclerView recyclerView;
    @BindView(R.id.selector_dialog_title_txt) TextView title;
    @BindView(R.id.selector_dialog_nothing_to_show_txt)TextView nothingToShow;
    @BindView(R.id.selector_dialog_search_et)EditText searchEt;
    @BindView(R.id.selector_dialog_clear_et_btn)ImageView clearEtBtn;

    SelectorDialogAdapter selectorDialogAdapter;

    //For Constructor
    Context context;
    private int Which;
    private String dialogTitle;
    private List<SelectorDialogDataHolder> items;
    private RetrofitViewModel retrofitViewModel;

    public SelectorDialogFragment(Context context, String title, List<SelectorDialogDataHolder> items, RetrofitViewModel retrofitViewModel, int Which) {
        this.context = context;
        dialogTitle = title;
        this.items = items;
        this.retrofitViewModel = retrofitViewModel;
        this.Which = Which;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_selector_dialog, container, false);
        ButterKnife.bind(this, view);
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        title.setText(dialogTitle);
        recyclerViewSetup();
        initListeners();

        return view;
    }

    private void initListeners() {
        closeBtn.setOnClickListener(v-> dismiss());
        searchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchEt.removeTextChangedListener(this);
                searchWord(s.toString());
                if (s.toString().length() > 0){
                    clearEtBtn.setVisibility(View.VISIBLE);
                    clearEtBtn.setOnClickListener(v-> searchEt.setText(""));
                } else {
                    clearEtBtn.setVisibility(View.GONE);
                    clearEtBtn.setOnClickListener(null);
                }
                searchEt.addTextChangedListener(this);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void searchWord(String searchText) {
        List<SelectorDialogDataHolder> searchedLocations = new ArrayList<>();

        for (int i = 0 ; i < items.size(); i++){
            if (items.get(i).getLabel().contains(searchText)){
                searchedLocations.add(items.get(i));
            }
        }

        if (searchedLocations.size() > 0 ){
            nothingToShow.setVisibility(View.INVISIBLE);
        } else {
            nothingToShow.setVisibility(View.VISIBLE);
        }

        selectorDialogAdapter.setData(searchedLocations);
    }

    private void recyclerViewSetup() {
        if(items.size() > 0){
            selectorDialogAdapter = new SelectorDialogAdapter(context, items, this::setSelectedItem);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(selectorDialogAdapter);
            nothingToShow.setVisibility(View.INVISIBLE);
        }

    }

    private void setSelectedItem(int id) {
        switch (Which){
            case Consts.SELECT_CITY:
                retrofitViewModel.setSelectedCity(id);
                break;

            case Consts.SELECT_SECTION:
            case Consts.CHART_SELECT_SECTION:
                retrofitViewModel.setSelectedSection(id);
                break;

            case Consts.SELECT_ZONE:
                retrofitViewModel.setSelectedZone(id);
                break;
        }
        dismiss();
    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
    }
}