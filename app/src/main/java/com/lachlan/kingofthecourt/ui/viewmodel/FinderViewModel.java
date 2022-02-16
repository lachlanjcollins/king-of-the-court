package com.lachlan.kingofthecourt.ui.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.lachlan.kingofthecourt.data.database.RemoteDB;
import com.lachlan.kingofthecourt.data.entity.Court;
import com.lachlan.kingofthecourt.data.repository.CourtRepository;

import java.util.ArrayList;
import java.util.List;

public class FinderViewModel extends AndroidViewModel {

    private MutableLiveData<String> mText;
    private LiveData<List<Court>> courtsList;
    private CourtRepository courtRepository;

    public FinderViewModel(Application application) {
        super(application);
        mText = new MutableLiveData<>();
        mText.setValue("This is finder fragment");
        courtRepository = new CourtRepository(application);
        courtsList = courtRepository.getAllCourts();
    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<List<Court>> getCourtsList() {
        return courtsList;
    }
}