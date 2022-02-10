package com.lachlan.kingofthecourt.ui.game;

import androidx.lifecycle.ViewModel;

import com.lachlan.kingofthecourt.Database;
import com.lachlan.kingofthecourt.model.Court;
import com.lachlan.kingofthecourt.model.Game;

public class GameViewModel extends ViewModel {
    private Court court;
    private Game game;
    private Database db;

    public GameViewModel() {
        db = new Database();
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
