package com.lachlan.kingofthecourt.ui.finder;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.lachlan.kingofthecourt.Database;
import com.lachlan.kingofthecourt.model.Court;

import java.util.ArrayList;

public class FinderViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private Database db;
    private ArrayList<Court> courtsList;

    public FinderViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is finder fragment");
        db = new Database();
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