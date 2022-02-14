package com.lachlan.kingofthecourt.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.lachlan.kingofthecourt.data.dao.UserDAO;
import com.lachlan.kingofthecourt.data.database.LocalDB;
import com.lachlan.kingofthecourt.data.database.RemoteDB;
import com.lachlan.kingofthecourt.data.entity.User;

import java.util.List;

public class UserRepository {
    private UserDAO userDAO;
    private LiveData<User> currentUser;
    private RemoteDB remoteDB;

    public UserRepository(Application application) {
        LocalDB localDB = LocalDB.getInstance(application);
        userDAO = localDB.userDAO();
        currentUser = userDAO.findByID(FirebaseAuth.getInstance().getUid());
        remoteDB = new RemoteDB();
    }

    public LiveData<User> getCurrentUser() {
        remoteDB.getCurrentUser(this);

        return currentUser;
    }

    public void insertUser(final User user){
        LocalDB.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                userDAO.insert(user);
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
