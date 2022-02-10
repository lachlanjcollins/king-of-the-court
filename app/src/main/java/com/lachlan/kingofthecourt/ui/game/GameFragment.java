package com.lachlan.kingofthecourt.ui.game;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.lachlan.kingofthecourt.MainActivity;
import com.lachlan.kingofthecourt.databinding.FragmentGameBinding;
import com.lachlan.kingofthecourt.model.Court;
import com.lachlan.kingofthecourt.model.Game;
import com.lachlan.kingofthecourt.ui.game.GameFragmentArgs;

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
        

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
