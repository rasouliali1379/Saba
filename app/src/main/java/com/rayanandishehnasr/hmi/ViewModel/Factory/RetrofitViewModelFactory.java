package com.rayanandishehnasr.hmi.ViewModel.Factory;

import android.app.Application;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.rayanandishehnasr.hmi.ViewModel.RetrofitViewModel;

public class RetrofitViewModelFactory implements ViewModelProvider.Factory {
    private Application mApplication;

    public RetrofitViewModelFactory(Application application) {
        mApplication = application;
    }


    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new RetrofitViewModel(mApplication);
    }
}