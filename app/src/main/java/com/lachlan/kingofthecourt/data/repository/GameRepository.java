package com.lachlan.kingofthecourt.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.lachlan.kingofthecourt.data.dao.GameDAO;
import com.lachlan.kingofthecourt.data.database.LocalDB;
import com.lachlan.kingofthecourt.data.database.RemoteDB;
import com.lachlan.kingofthecourt.data.entity.Game;
import com.lachlan.kingofthecourt.data.relation.GameWithUsers;

import java.util.List;

public class GameRepository {
    private GameDAO gameDAO;
    private LiveData<List<Game>> allGames;
    private RemoteDB remoteDB;

    public GameRepository(Application application) {
        LocalDB localDB = LocalDB.getInstance(application);
        gameDAO = localDB.gameDAO();
        allGames = gameDAO.getAll();
        remoteDB = new RemoteDB();
    }

    public LiveData<List<Game>> getAllGames() {
        remoteDB.getAllGames(this);
        return allGames;
    }

    public LiveData<GameWithUsers> getAllGameUsers(String gameId) {
        return gameDAO.getAllGameUsers(gameId);
    }

    public void insertGame(final Game game){
        LocalDB.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                gameDAO.insert(game);
            }
        });
    }

    public void deleteAll(){
        LocalDB.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                gameDAO.deleteAll();
            }
        });
    }

    public void deleteGame(final Game game){
        LocalDB.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                gameDAO.delete(game);
            }
        });
    }
}
