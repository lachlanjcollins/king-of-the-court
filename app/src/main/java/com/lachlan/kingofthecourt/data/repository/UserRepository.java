package com.lachlan.kingofthecourt.data.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.lachlan.kingofthecourt.data.dao.UserDAO;
import com.lachlan.kingofthecourt.data.dao.UserGameRefDAO;
import com.lachlan.kingofthecourt.data.database.LocalDB;
import com.lachlan.kingofthecourt.data.database.RemoteDB;
import com.lachlan.kingofthecourt.data.entity.Game;
import com.lachlan.kingofthecourt.data.entity.User;
import com.lachlan.kingofthecourt.data.relation.GameWithUsers;
import com.lachlan.kingofthecourt.data.relation.UserGameCrossRef;
import com.lachlan.kingofthecourt.data.relation.UserWithGames;
import com.lachlan.kingofthecourt.util.Validation;

import java.util.List;

public class UserRepository {
    private UserDAO userDAO;
    private LiveData<User> currentUser;
    private RemoteDB remoteDB;
    private UserGameRefDAO userGameRefDAO;
    private Validation valid;

    public UserRepository(Application application) {
        LocalDB localDB = LocalDB.getInstance(application);
        userDAO = localDB.userDAO();
        currentUser = userDAO.findByID(FirebaseAuth.getInstance().getUid());
        userGameRefDAO = localDB.userGameRefDAO();
        remoteDB = new RemoteDB();
        valid = new Validation();
    }

    public LiveData<User> getCurrentUser() {
        remoteDB.getCurrentUser(this);
        return currentUser;
    }

    public LiveData<UserWithGames> getAllUserGames(String userId) {
        LiveData<UserWithGames> userWithGames = userDAO.getAllUserGames(userId); //TODO: might not work
        if (userWithGames.getValue() != null) {
            Log.e("TAG", "User with games not null");
            List<Game> gameList = userWithGames.getValue().games;
            for (int i = 0 ; i > gameList.size() ; i++) {
                Log.e("TAG", "Cycling through games: " + gameList.get(i));
                if (!valid.inFuture(gameList.get(i).getDateTime())) {
                    Log.e("TAG", "Game removed is: " + gameList.get(i).getDateTime());
                    userWithGames.getValue().games.remove(gameList.get(i));
                }
            }
        }
        return userWithGames;
    }

    public void insertUser(final User user){
        LocalDB.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                userDAO.insert(user);
            }
        });
    }

    public void insertUserGameRef(final String userId, final String gameId) {
        LocalDB.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                remoteDB.joinGame(userId, gameId);
                userGameRefDAO.insert(new UserGameCrossRef(userId, gameId));
            }
        });
    }

    public void deleteUserGameRef(final String userId, final String gameId) {
        LocalDB.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                remoteDB.leaveGame(userId, gameId);
                userGameRefDAO.delete(new UserGameCrossRef(userId, gameId));
            }
        });
    }

    public void deleteAll(){
        LocalDB.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                userDAO.deleteAll();
            }
        });
    }

    public void deleteAllUserRefsForGame(String gameId) {
        LocalDB.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                userGameRefDAO.deleteAllUserRefsForGame(gameId);
            }
        });
    }

    public void deleteUser(final User user){
        LocalDB.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                userDAO.delete(user);
            }
        });
    }

    public void updateUser(final User user){
        LocalDB.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                remoteDB.updateUser(user);
                userDAO.updateUser(user);
            }
        });
    }
}
