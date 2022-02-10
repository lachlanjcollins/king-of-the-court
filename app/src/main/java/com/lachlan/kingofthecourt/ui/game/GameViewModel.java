package com.lachlan.kingofthecourt.ui.game;

import androidx.lifecycle.ViewModel;

import com.lachlan.kingofthecourt.Database;
import com.lachlan.kingofthecourt.model.Court;
import com.lachlan.kingofthecourt.model.Game;
import com.lachlan.kingofthecourt.model.User;

public class GameViewModel extends ViewModel {
    private Court court;
    private Game game;
    private Database db;

    public GameViewModel() {
        db = new Database();
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

    public void setGame(Game game) {
        this.game = game;
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
}
