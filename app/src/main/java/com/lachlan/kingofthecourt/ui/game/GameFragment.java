package com.lachlan.kingofthecourt.ui.game;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseAuth;
import com.lachlan.kingofthecourt.Database;
import com.lachlan.kingofthecourt.MainActivity;
import com.lachlan.kingofthecourt.R;
import com.lachlan.kingofthecourt.databinding.FragmentGameBinding;
import com.lachlan.kingofthecourt.model.Court;
import com.lachlan.kingofthecourt.model.Game;
import com.lachlan.kingofthecourt.ui.game.GameFragmentArgs;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class GameFragment extends Fragment {
    private FragmentGameBinding binding;
    private GameViewModel gameViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentGameBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        Game game = GameFragmentArgs.fromBundle(getArguments()).getGame();
        Court court = GameFragmentArgs.fromBundle(getArguments()).getCourt();
        ((MainActivity) getActivity()).setActionBarTitle("Game Details");

        gameViewModel = new ViewModelProvider(this).get(GameViewModel.class);
        gameViewModel.setGame(game);
        gameViewModel.setCourt(court);

        binding.textLocationName.setText(court.getLocationName());

        DateFormat day = new SimpleDateFormat("EE");
        DateFormat date = new SimpleDateFormat("dd/MM/yyyy");
        DateFormat time = new SimpleDateFormat("hh:mm:ss a"); //@TODO: Figure out timezones
        Date dateTime = game.getDateTime();


        binding.textDate.setText(day.format(dateTime) + " " + date.format(dateTime));
        binding.textTime.setText(time.format(dateTime));
        binding.textNumPlayers.setText(game.getPlayers().size() + " / 10");

        if (gameViewModel.isCreator() || gameViewModel.inGame()) {
            binding.buttonJoinGame.setClickable(false);
            binding.buttonJoinGame.setBackgroundResource(R.drawable.bg_button_grey);
            binding.buttonJoinGame.setVisibility(View.GONE); //@TODO: Figure out the best method for this
        }

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
