package com.lachlan.kingofthecourt.ui.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.lachlan.kingofthecourt.data.entity.Game;
import com.lachlan.kingofthecourt.data.relation.UserWithGames;
import com.lachlan.kingofthecourt.data.repository.UserRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Comparator;

public class HomeViewModel extends AndroidViewModel {

    private final MutableLiveData<String> mText;
    private final UserRepository userRepository;
    private final LiveData<UserWithGames> userWithGames;
    private final MutableLiveData<String> date;

    public HomeViewModel(Application application) {
        super(application);
        mText = new MutableLiveData<>();
        date = new MutableLiveData<>();
        mText.setValue("This is home fragment");
        userRepository = new UserRepository(application);
        userWithGames = userRepository.getAllUserGames(FirebaseAuth.getInstance().getCurrentUser().getUid());
        setDate();
    }

    public LiveData<UserWithGames> getUserWithGames() {
        return userWithGames;
    }

    public void sortGameList() {
        Collections.sort(userWithGames.getValue().games, new Comparator<Game>() {
            @Override
            public int compare(Game game, Game t1) {
                return game.getDateTime().compareTo(t1.getDateTime());
            }
        });
    }

    public MutableLiveData<String> getDate() {
        return date;
    }

    public void setDate() {
        LocalDate localDate = LocalDate.now();
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        date.setValue(dateFormat.format(localDate));
    }


    public LiveData<String> getText() {
        return mText;
    }
}