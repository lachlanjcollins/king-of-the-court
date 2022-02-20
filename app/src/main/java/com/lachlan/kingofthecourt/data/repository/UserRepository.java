package com.lachlan.kingofthecourt.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.lachlan.kingofthecourt.data.dao.UserDAO;
import com.lachlan.kingofthecourt.data.dao.UserGameRefDAO;
import com.lachlan.kingofthecourt.data.database.LocalDB;
import com.lachlan.kingofthecourt.data.database.RemoteDB;
import com.lachlan.kingofthecourt.data.entity.User;
import com.lachlan.kingofthecourt.data.relation.UserGameCrossRef;
import com.lachlan.kingofthecourt.data.relation.UserWithGames;
import com.lachlan.kingofthecourt.util.Validation;

/**
 * A repository class that provides a level of abstraction between user data and various view model classes.
 *
 * @author Lachlan Collins
 * @version 20 February 2022
 */
public class UserRepository {
    private final UserDAO userDAO;
    private final LiveData<User> currentUser;
    private final RemoteDB remoteDB;
    private final UserGameRefDAO userGameRefDAO;
    private final Validation valid;

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
        return userDAO.getAllUserGames(userId);
    }

    public void insertUser(final User user) {
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

    public void deleteAll() {
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

    public void deleteUser(final User user) {
        LocalDB.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                userDAO.delete(user);
            }
        });
    }

    public void updateUser(final User user) {
        LocalDB.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                remoteDB.updateUser(user);
                userDAO.updateUser(user);
            }
        });
    }
}
