package com.shashankbhat.notesapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

/**
 * Created by SHASHANK BHAT on 06-Sep-20.
 */

public class MainViewModelFactory implements ViewModelProvider.Factory {

    private Application application;

    public MainViewModelFactory(Application application, int randomValue) {
        this.application = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MainActivityViewModel.class)) {
            return (T) new MainActivityViewModel(application, 2);
        }
        throw new IllegalArgumentException("Unable to construct viewmodel");
    }

}
