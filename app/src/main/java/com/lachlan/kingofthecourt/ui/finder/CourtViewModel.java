package com.lachlan.kingofthecourt.ui.finder;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.lachlan.kingofthecourt.Database;
import com.lachlan.kingofthecourt.model.Court;
import com.lachlan.kingofthecourt.model.Game;

import java.util.ArrayList;

public class CourtViewModel extends ViewModel {
    private Court court;
    private Database db;
    private ArrayList<Game> gamesList;
    private MutableLiveData<String> listReady;

    public CourtViewModel() {
        listReady = new MutableLiveData<>();
        db = new Database();
    }

    public void setCourt(Court court) {
        this.court = court;
    }

    public void initGamesList() {
        db.getGamesList(court, this);
        //@TODO: Get the user data (currently only have user id)
    }

    public void onGamesListRetrieved(ArrayList<Game> gamesList) {
        this.gamesList = gamesList;
        listReady.setValue("ready");
    }

    public ArrayList<Game> getGamesList() {
        return gamesList;
    }

    public LiveData<String> getListReady() {
        return listReady;
    }
}
