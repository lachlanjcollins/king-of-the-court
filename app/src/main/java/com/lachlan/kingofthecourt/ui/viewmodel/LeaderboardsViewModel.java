package com.lachlan.kingofthecourt.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LeaderboardsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public LeaderboardsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is leaderboards fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}