package com.lachlan.kingofthecourt.ui.game;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.lachlan.kingofthecourt.Database;
import com.lachlan.kingofthecourt.model.Court;
import com.lachlan.kingofthecourt.model.Game;
import com.lachlan.kingofthecourt.model.User;

public class GameViewModel extends ViewModel {
    private Court court;
    private Game game;
    private Database db;
    private MutableLiveData<Integer> numPlayers;

    public GameViewModel() {
        db = new Database();
        numPlayers = new MutableLiveData<>();
    }

    public boolean isCreator() {
        return (db.getCurrentUserID().equals(game.getCreator().getId()));
    }

    public boolean inGame() {
        boolean bool = false;
        for (User player : game.getPlayers()) {
            if (player.getId().equals(db.getCurrentUserID()))
                bool = true;
        }
        return bool;
    }

    public boolean isGameFull() {
        return (game.getPlayers().size() == 10);
    }

    public void joinGame() {
        if (!inGame()) {
            game.getPlayers().add(new User(db.getCurrentUserID()));
            numPlayers.setValue(game.getPlayers().size());
            db.joinGame(court, game);
        }
    }

    public void setGame(Game game) {
        this.game = game;
        numPlayers.setValue(game.getPlayers().size());
    }

    public void setCourt(Court court) {
        this.court = court;
    }

    public Game getGame() {
        return game;
    }

    public Court getCourt() {
        return court;
    }

    public MutableLiveData<Integer> getNumPlayers() {
        return numPlayers;
    }

    public void setNumPlayers(MutableLiveData<Integer> numPlayers) {
        this.numPlayers = numPlayers;
    }
}
