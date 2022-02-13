package com.lachlan.kingofthecourt.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.lachlan.kingofthecourt.activities.MainActivity;
import com.lachlan.kingofthecourt.R;
import com.lachlan.kingofthecourt.databinding.FragmentGameBinding;
import com.lachlan.kingofthecourt.data.entity.Court;
import com.lachlan.kingofthecourt.data.entity.Game;
import com.lachlan.kingofthecourt.ui.viewmodel.GameViewModel;

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
        binding.textDate.setText(gameViewModel.getFormattedDate());
        binding.textTime.setText(gameViewModel.getFormattedTime());

        gameViewModel.getNumPlayers().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                binding.textNumPlayers.setText(integer + " / 10");
            }
        });

        binding.buttonJoinGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameViewModel.joinGame();
                Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
                binding.buttonJoinGame.setClickable(false);
                binding.buttonJoinGame.setBackgroundResource(R.drawable.bg_button_grey);
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
