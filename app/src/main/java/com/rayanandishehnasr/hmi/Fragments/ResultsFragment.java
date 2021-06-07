package com.rayanandishehnasr.hmi.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rayanandishehnasr.hmi.Adapters.SearchResultsListAdapter;
import com.rayanandishehnasr.hmi.DataHolder.SearchResultDataHolder;
import com.rayanandishehnasr.hmi.R;
import com.rayanandishehnasr.hmi.ViewModel.RetrofitViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ResultsFragment extends Fragment {

    @BindView(R.id.search_results_recyclerview)RecyclerView recyclerView;
    @BindView(R.id.search_results_nothing_found)TextView nothingFound;
    
    Context context;
    RetrofitViewModel retrofitViewModel;
    SearchResultsListAdapter searchResultsListAdapter;
    List<SearchResultDataHolder> results;
    int treatyType;

    public ResultsFragment(Context context, RetrofitViewModel retrofitViewModel, List<SearchResultDataHolder> results, int treatyType) {
        this.context = context;
        this.retrofitViewModel = retrofitViewModel;
        this.results = results;
        this.treatyType = treatyType;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_results, container, false);
        ButterKnife.bind(this,view);
        if (results != null){
            if (results.size() > 0){
                recyclerviewSetup();
                nothingFound.setVisibility(View.INVISIBLE);
                searchResultsListAdapter.setData(results);
            } else {
                nothingFound.setVisibility(View.VISIBLE);
            }
        } else {
            nothingFound.setVisibility(View.VISIBLE);
        }

        return view;
    }

    private void recyclerviewSetup() {
        searchResultsListAdapter = new SearchResultsListAdapter(context, this::showDetails, treatyType);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(searchResultsListAdapter);
        if (retrofitViewModel.getScrollPosition().getValue() > -1){
            recyclerView.scrollToPosition(retrofitViewModel.getScrollPosition().getValue());
        }
    }

    private void showDetails(int position) {
        retrofitViewModel.setScrollPosition(position);
        retrofitViewModel.setSelectedSearchResult(position);
    }
}