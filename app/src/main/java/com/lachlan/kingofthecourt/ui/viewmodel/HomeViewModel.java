package com.lachlan.kingofthecourt.ui.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.lachlan.kingofthecourt.data.entity.User;
import com.lachlan.kingofthecourt.data.relation.UserWithGames;
import com.lachlan.kingofthecourt.data.repository.GameRepository;
import com.lachlan.kingofthecourt.data.repository.UserRepository;

public class HomeViewModel extends AndroidViewModel {

    private MutableLiveData<String> mText;
    private GameRepository gameRepository;
    private UserRepository userRepository;
    private LiveData<UserWithGames> userWithGames;

    public HomeViewModel(Application application) {
        super(application);
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
        userRepository = new UserRepository(application);
        gameRepository = new GameRepository(application);
        gameRepository.getAllGames(); //@TODO: Check this works
        userWithGames = userRepository.getAllUserGames(FirebaseAuth.getInstance().getCurrentUser().getUid());
    }

    public LiveData<UserWithGames> getUserWithGames() {
        return userWithGames;
    }

    public LiveData<String> getText() {
        return mText;
    }
}