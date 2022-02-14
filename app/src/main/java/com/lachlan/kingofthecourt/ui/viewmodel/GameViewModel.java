package com.lachlan.kingofthecourt.ui.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.lachlan.kingofthecourt.data.database.RemoteDB;
import com.lachlan.kingofthecourt.data.entity.Court;
import com.lachlan.kingofthecourt.data.entity.Game;
import com.lachlan.kingofthecourt.data.entity.User;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GameViewModel extends ViewModel {
    private Court court;
    private Game game;
    private RemoteDB db;
    private boolean isCreator;
    private MutableLiveData<Integer> numPlayers;
    private MutableLiveData<Boolean> isGameFull;
    private MutableLiveData<Boolean> inGame;

    public GameViewModel() {
        db = new RemoteDB();
        numPlayers = new MutableLiveData<>();
        isGameFull = new MutableLiveData<>();
        inGame = new MutableLiveData<>();
    }

    public void updateIsGameFull() {
        isGameFull.setValue(numPlayers.getValue() == 10);
    }

    public void joinGame() {
        if (!inGame.getValue() && !isCreator && !isGameFull.getValue()) {
//            game.getPlayers().add(new User(db.getCurrentUserID()));
//            numPlayers.setValue(game.getPlayers().size());
            inGame.setValue(true);
            updateIsGameFull();
            db.joinGame(court, game);
        }
    }

    public void leaveGame() {
//        game.getPlayers().removeIf(user -> user.getUserId().equals(db.getCurrentUserID()));
    }

    public void setGame(Game game) {
        this.game = game;
//        numPlayers.setValue(game.getPlayers().size());
//        if (db.getCurrentUserID().equals(game.getCreator().getUserId())) {
//            isCreator = true;
//            inGame.setValue(true);
//        } else {
//            isCreator = false;
//            for (User player : game.getPlayers()) {
//                if (player.getUserId().equals(db.getCurrentUserID()))
//                    inGame.setValue(true);
//                else
//                    inGame.setValue(false);
//            }
//        }
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

    public boolean getIsCreator() {
        return isCreator;
    }

    public MutableLiveData<Boolean> getIsGameFull() {
        return isGameFull;
    }

    public MutableLiveData<Boolean> getInGame() {
        return inGame;
    }

    public String getFormattedDate() {
        DateFormat day = new SimpleDateFormat("EE");
        DateFormat date = new SimpleDateFormat("dd/MM/yyyy");
        Date dateTime = game.getDateTime();
        return day.format(dateTime) + " " + date.format(dateTime);
    }

    public String getFormattedTime() {
        DateFormat time = new SimpleDateFormat("hh:mm:ss a"); //@TODO: Figure out timezones
        Date dateTime = game.getDateTime();
        return time.format(dateTime);
    }


}
