package com.lachlan.kingofthecourt.ui.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.lachlan.kingofthecourt.data.database.RemoteDB;
import com.lachlan.kingofthecourt.data.entity.Court;
import com.lachlan.kingofthecourt.data.entity.Game;
import com.lachlan.kingofthecourt.data.entity.User;
import com.lachlan.kingofthecourt.data.relation.GameWithUsers;
import com.lachlan.kingofthecourt.data.repository.CourtRepository;
import com.lachlan.kingofthecourt.data.repository.GameRepository;
import com.lachlan.kingofthecourt.data.repository.UserRepository;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class GameViewModel extends AndroidViewModel {
    private final RemoteDB remoteDB;
    private final MutableLiveData<Integer> numPlayers;
    private final MutableLiveData<Boolean> isGameFull;
    private final MutableLiveData<Boolean> inGame;
    private final MutableLiveData<User> creator;
    private final GameRepository gameRepository;
    private final UserRepository userRepository;
    private final CourtRepository courtRepository;
    private LiveData<Court> court;
    private LiveData<Game> currentGame;
    private LiveData<GameWithUsers> gameWithUsers;
    private boolean isCreator;

    public GameViewModel(Application application) {
        super(application);
        remoteDB = new RemoteDB();
        numPlayers = new MutableLiveData<>();
        isGameFull = new MutableLiveData<>();
        inGame = new MutableLiveData<>();
        gameRepository = new GameRepository(application);
        courtRepository = new CourtRepository(application);
        userRepository = new UserRepository(application);
        creator = new MutableLiveData<>();
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
        userRepository.deleteUserGameRef(remoteDB.getCurrentUserID(), currentGame.getValue().getGameId());
        gameWithUsers = gameRepository.getAllGameUsers(currentGame.getValue().getGameId());
        inGame.setValue(false);
        updateIsGameFull();
    }

    public LiveData<Game> getCurrentGame() {
        return currentGame;
    }

    public void setCurrentGame(String gameId) {
        currentGame = gameRepository.getGameById(gameId);
        gameWithUsers = gameRepository.getAllGameUsers(gameId);
    }

    public LiveData<GameWithUsers> getGameWithUsers() {
        return gameWithUsers;
    }

    public LiveData<Court> getCourt() {
        return court;
    }

    public void setCourt(String courtId) {
        this.court = courtRepository.getCourtById(courtId);
    }

    public MutableLiveData<Integer> getNumPlayers() {
        return numPlayers;
    }

    public void setNumPlayers(int numPlayers) {
        this.numPlayers.setValue(numPlayers);
    }

    public boolean getIsCreator() {
        return isCreator;
    }

    public void setIsCreator(Game game) {
        isCreator = remoteDB.getCurrentUserID().equals(game.getCreatorId());
    }

    public void setCreator() {
        if (gameWithUsers.getValue() != null && gameWithUsers.getValue().users.size() > 0) {
            for (User user : gameWithUsers.getValue().users) {
                if (user.getUserId().equals(gameWithUsers.getValue().game.getCreatorId())) {
                    creator.setValue(user);
                }
            }
        }
    }

    public MutableLiveData<User> getCreator() {
        return creator;
    }

    public MutableLiveData<Boolean> getIsGameFull() {
        return isGameFull;
    }

    public MutableLiveData<Boolean> getInGame() {
        return inGame;
    }

    public void setInGame(List<User> users) {
        for (User player : users) {
            if (player.getUserId().equals(remoteDB.getCurrentUserID()))
                inGame.setValue(true);
            else
                inGame.setValue(false);
        }
    }

    public String getFormattedDate(Game game) {
        DateFormat day = new SimpleDateFormat("EE");
        DateFormat date = new SimpleDateFormat("dd/MM/yyyy");
        Date dateTime = game.getDateTime();
        return day.format(dateTime) + " " + date.format(dateTime);
    }

    public String getFormattedTime(Game game) {
        DateFormat time = new SimpleDateFormat("hh:mm:ss a");
        Date dateTime = game.getDateTime();
        return time.format(dateTime);
    }


}
