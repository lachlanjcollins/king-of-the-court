package com.lachlan.kingofthecourt.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.lachlan.kingofthecourt.data.dao.GameDAO;
import com.lachlan.kingofthecourt.data.dao.UserGameRefDAO;
import com.lachlan.kingofthecourt.data.database.LocalDB;
import com.lachlan.kingofthecourt.data.database.RemoteDB;
import com.lachlan.kingofthecourt.data.entity.Game;
import com.lachlan.kingofthecourt.data.relation.GameWithUsers;

import java.util.Date;
import java.util.List;

/**
 * A repository class that provides a level of abstraction between game data and various view model classes.
 *
 * @author Lachlan Collins
 * @version 20 February 2022
 */
public class GameRepository {
    private final GameDAO gameDAO;
    private final LiveData<List<Game>> allGames;
    private final RemoteDB remoteDB;
    private final UserRepository userRepository;
    private final UserGameRefDAO userGameRefDAO;

    public GameRepository(Application application) {
        LocalDB localDB = LocalDB.getInstance(application);
        gameDAO = localDB.gameDAO();
        allGames = gameDAO.getAll();
        remoteDB = new RemoteDB();
        userRepository = new UserRepository(application);
        userGameRefDAO = localDB.userGameRefDAO();
    }

    public LiveData<List<Game>> getAllGames() {
        remoteDB.getAllGames(this, userRepository);
        return allGames;
    }

    public LiveData<Game> getGameById(String gameId) {
        return gameDAO.findByID(gameId);
    }

    public LiveData<GameWithUsers> getAllGameUsers(String gameId) {
        remoteDB.getAllGameUsers(gameId, userRepository);
        return gameDAO.getAllGameUsers(gameId);
    }

    public void createNewGame(String courtId, String creatorId, Date dateTime) {
        remoteDB.createNewGame(courtId, creatorId, dateTime, this);
    }

    public void insertGame(final Game game) {
        LocalDB.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                gameDAO.insert(game);
            }
        });
    }

    public void deleteAll() {
        LocalDB.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                gameDAO.deleteAll();
            }
        });
    }

    public void deleteGame(final Game game) {
        LocalDB.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                gameDAO.delete(game);
            }
        });
    }
}
