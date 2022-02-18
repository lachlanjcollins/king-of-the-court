package com.lachlan.kingofthecourt.ui.viewmodel;

import android.app.Application;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.lachlan.kingofthecourt.data.entity.Court;
import com.lachlan.kingofthecourt.data.entity.Game;
import com.lachlan.kingofthecourt.data.entity.User;
import com.lachlan.kingofthecourt.data.relation.UserWithGames;
import com.lachlan.kingofthecourt.data.repository.CourtRepository;
import com.lachlan.kingofthecourt.data.repository.GameRepository;
import com.lachlan.kingofthecourt.data.repository.UserRepository;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class HomeViewModel extends AndroidViewModel {

    private MutableLiveData<String> mText;
    private UserRepository userRepository;
    private LiveData<UserWithGames> userWithGames;
    private MutableLiveData<String> date;

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