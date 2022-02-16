package com.lachlan.kingofthecourt.ui.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.lachlan.kingofthecourt.data.entity.Court;
import com.lachlan.kingofthecourt.data.repository.GameRepository;

import java.util.Date;

public class CreateGameViewModel extends AndroidViewModel {
    private MutableLiveData<Date> dateTime;
    private GameRepository gameRepository;

    public CreateGameViewModel(Application application) {
        super(application);
        gameRepository = new GameRepository(application);
        dateTime = new MutableLiveData<>();
    }

    public void setDateTime(int year, int month, int day, int hour, int minute) {
        dateTime.setValue(new Date(year-1900, month, day, hour, minute));
    }

    public void createGame(Court court) {
        gameRepository.createNewGame(court.getCourtId(), FirebaseAuth.getInstance().getCurrentUser().getUid(), dateTime.getValue());
    }
}
