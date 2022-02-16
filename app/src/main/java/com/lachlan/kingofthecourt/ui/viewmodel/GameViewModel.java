package com.lachlan.kingofthecourt.ui.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.lachlan.kingofthecourt.data.database.RemoteDB;
import com.lachlan.kingofthecourt.data.entity.Court;
import com.lachlan.kingofthecourt.data.entity.Game;
import com.lachlan.kingofthecourt.data.entity.User;
import com.lachlan.kingofthecourt.data.relation.GameWithUsers;
import com.lachlan.kingofthecourt.data.repository.GameRepository;
import com.lachlan.kingofthecourt.data.repository.UserRepository;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class GameViewModel extends AndroidViewModel {
    private Court court;
    private LiveData<Game> currentGame;
    private LiveData<GameWithUsers> gameWithUsers;

    private RemoteDB remoteDB;
    private boolean isCreator;
    private MutableLiveData<Integer> numPlayers;
    private MutableLiveData<Boolean> isGameFull;
    private MutableLiveData<Boolean> inGame;

    private GameRepository gameRepository;
    private UserRepository userRepository;

    public GameViewModel(Application application) {
        super(application);
        remoteDB = new RemoteDB();
        numPlayers = new MutableLiveData<>();
        isGameFull = new MutableLiveData<>();
        inGame = new MutableLiveData<>();
        gameRepository = new GameRepository(application);
        userRepository = new UserRepository(application);
    }

    public void updateIsGameFull() {
        isGameFull.setValue(numPlayers.getValue() == 10);
    }

    public void joinGame() {
        if (!inGame.getValue() && !isCreator && !isGameFull.getValue()) {
            userRepository.insertUserGameRef(remoteDB.getCurrentUserID(), currentGame.getValue().getGameId());
            gameWithUsers = gameRepository.getAllGameUsers(currentGame.getValue().getGameId());
            inGame.setValue(true);
            updateIsGameFull();
        }
    }

    public void leaveGame() {
//        game.getPlayers().removeIf(user -> user.getUserId().equals(db.getCurrentUserID()));
    }

    public void setCurrentGame(String gameId) {
        currentGame = gameRepository.getGameById(gameId);
        gameWithUsers = gameRepository.getAllGameUsers(gameId);
    }

    public void setCourt(Court court) {
        this.court = court;
    }

    public LiveData<Game> getCurrentGame() {
        return currentGame;
    }

    public LiveData<GameWithUsers> getGameWithUsers() {
        return gameWithUsers;
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

    public void setIsCreator() {
        if (remoteDB.getCurrentUserID().equals(currentGame.getValue().getCreatorId())) {
            isCreator = true;
        } else {
            isCreator = false;
        }
    }

    public void setInGame(List<User> users) {
        for (User player : users) {
            if (player.getUserId().equals(remoteDB.getCurrentUserID()))
                inGame.setValue(true);
            else
                inGame.setValue(false);
        }
    }

    public MutableLiveData<Boolean> getIsGameFull() {
        return isGameFull;
    }

    public MutableLiveData<Boolean> getInGame() {
        return inGame;
    }

    public void setNumPlayers(int numPlayers) {
        this.numPlayers.setValue(numPlayers);
    }

    public String getFormattedDate() {
        DateFormat day = new SimpleDateFormat("EE");
        DateFormat date = new SimpleDateFormat("dd/MM/yyyy");
        Date dateTime = currentGame.getValue().getDateTime();
        return day.format(dateTime) + " " + date.format(dateTime);
    }

    public String getFormattedTime() {
        DateFormat time = new SimpleDateFormat("hh:mm:ss a"); //@TODO: Figure out timezones
        Date dateTime = currentGame.getValue().getDateTime();
        return time.format(dateTime);
    }


}
