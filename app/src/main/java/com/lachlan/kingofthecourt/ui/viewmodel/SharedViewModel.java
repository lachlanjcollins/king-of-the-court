package com.lachlan.kingofthecourt.ui.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.lachlan.kingofthecourt.data.entity.User;
import com.lachlan.kingofthecourt.data.repository.CourtRepository;
import com.lachlan.kingofthecourt.data.repository.GameRepository;
import com.lachlan.kingofthecourt.data.repository.UserRepository;

public class SharedViewModel extends AndroidViewModel {
    private final LiveData<User> currentUser;
    private final UserRepository userRepository;
    private final GameRepository gameRepository;
    private final CourtRepository courtRepository;

    public SharedViewModel(Application application) {
        super(application);
        userRepository = new UserRepository(application);
        gameRepository = new GameRepository(application);
        courtRepository = new CourtRepository(application);
        currentUser = userRepository.getCurrentUser();
        gameRepository.getAllGames();
        courtRepository.getAllCourts();
    }

    public LiveData<User> getUser() {
        return currentUser;
    }

    public void updateUser(User user) {
        userRepository.updateUser(user);
    }

}
