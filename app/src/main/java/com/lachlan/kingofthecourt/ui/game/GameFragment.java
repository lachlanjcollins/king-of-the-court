package com.lachlan.kingofthecourt.ui.game;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.lachlan.kingofthecourt.MainActivity;
import com.lachlan.kingofthecourt.databinding.FragmentCourtBinding;
import com.lachlan.kingofthecourt.databinding.FragmentGameBinding;
import com.lachlan.kingofthecourt.model.Court;
import com.lachlan.kingofthecourt.model.Game;
import com.lachlan.kingofthecourt.ui.finder.CourtFragmentArgs;

public class GameFragment extends Fragment {
    private FragmentGameBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentGameBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        Game game = GameFragmentArgs.fromBundle(getArguments()).getGame();
        ((MainActivity) getActivity()).setActionBarTitle(game.getDateTime().toString());


        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
