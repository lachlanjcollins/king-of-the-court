package com.lachlan.kingofthecourt.ui.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.lachlan.kingofthecourt.data.database.RemoteDB;
import com.lachlan.kingofthecourt.data.entity.User;
import com.lachlan.kingofthecourt.data.repository.GameRepository;
import com.lachlan.kingofthecourt.data.repository.UserRepository;
import com.lachlan.kingofthecourt.fragments.EditProfileFragment;

import java.util.List;

public class SharedViewModel extends AndroidViewModel {
    private LiveData<User> currentUser;
    private UserRepository userRepository;
    private GameRepository gameRepository;

    public SharedViewModel(Application application) {
        super(application);
        userRepository = new UserRepository(application);
        gameRepository = new GameRepository(application);
        currentUser = userRepository.getCurrentUser();
        gameRepository.getAllGames();
    }

    public LiveData<User> getUser() {
        return currentUser;
    }

    public void updateUser(User user) {
        userRepository.updateUser(user);
    }

}
