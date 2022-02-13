package com.lachlan.kingofthecourt.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.lachlan.kingofthecourt.data.database.RemoteDB;
import com.lachlan.kingofthecourt.data.entity.Court;

import java.util.ArrayList;

public class FinderViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private RemoteDB db;
    private ArrayList<Court> courtsList;

    public FinderViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is finder fragment");
        db = new RemoteDB();
        db.getCourtLocations(this);
    }

    public LiveData<String> getText() {
        return mText;
    }

    public ArrayList<Court> getCourtsList() {
        return courtsList;
    }

    public void onGetCourtsResult(ArrayList<Court> courtsList) {
        this.courtsList = courtsList;
    }
}