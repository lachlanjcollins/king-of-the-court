package com.lachlan.kingofthecourt.ui.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.lachlan.kingofthecourt.data.entity.Court;
import com.lachlan.kingofthecourt.data.entity.Game;
import com.lachlan.kingofthecourt.data.relation.CourtWithGames;
import com.lachlan.kingofthecourt.data.repository.CourtRepository;

import java.util.Collections;
import java.util.Comparator;

public class CourtViewModel extends AndroidViewModel {
    private final CourtRepository courtRepository;
    private LiveData<Court> currentCourt;
    private LiveData<CourtWithGames> courtWithGames;

    public CourtViewModel(Application application) {
        super(application);
        courtRepository = new CourtRepository(application);
    }

    public LiveData<Court> getCurrentCourt() {
        return currentCourt;
    }

    public void setCurrentCourt(String courtId) {
        currentCourt = courtRepository.getCourtById(courtId);
        courtWithGames = courtRepository.getGamesAtCourt(courtId);
    }

    public void sortGameList() {
        Collections.sort(courtWithGames.getValue().games, new Comparator<Game>() {
            @Override
            public int compare(Game game, Game t1) {
                return game.getDateTime().compareTo(t1.getDateTime());
            }
        });
    }

    public LiveData<CourtWithGames> getGamesAtCourt() {
        return courtWithGames;
    }

}
