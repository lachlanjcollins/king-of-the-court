package com.lachlan.kingofthecourt.fragments;

import android.graphics.Color;
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
import com.lachlan.kingofthecourt.data.entity.User;
import com.lachlan.kingofthecourt.data.relation.GameWithUsers;
import com.lachlan.kingofthecourt.databinding.FragmentGameBinding;
import com.lachlan.kingofthecourt.data.entity.Court;
import com.lachlan.kingofthecourt.data.entity.Game;
import com.lachlan.kingofthecourt.ui.viewmodel.CourtViewModel;
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
        String courtId = GameFragmentArgs.fromBundle(getArguments()).getCourtId();
        ((MainActivity) getActivity()).setActionBarTitle("Game Details");

        gameViewModel = ViewModelProvider
                .AndroidViewModelFactory
                .getInstance(getActivity().getApplication())
                .create(GameViewModel.class);

        gameViewModel.setCurrentGame(game.getGameId());
        gameViewModel.setCourt(courtId);

        binding.buttonLeaveGame.setVisibility(View.INVISIBLE);

        gameViewModel.getCurrentGame().observe(getViewLifecycleOwner(), new Observer<Game>() {
            @Override
            public void onChanged(Game game) {
                binding.textDate.setText(gameViewModel.getFormattedDate(game));
                binding.textTime.setText(gameViewModel.getFormattedTime(game));
            }
        });

        gameViewModel.getCourt().observe(getViewLifecycleOwner(), new Observer<Court>() {
            @Override
            public void onChanged(Court court) {
                binding.textLocationName.setText(court.getLocationName());
            }
        });

        gameViewModel.getGameWithUsers().observe(getViewLifecycleOwner(), new Observer<GameWithUsers>() {
            @Override
            public void onChanged(GameWithUsers gameWithUsers) {
                if (gameWithUsers.users != null && gameViewModel.getCurrentGame() != null) {
                    gameViewModel.setIsCreator(game);
                    gameViewModel.setInGame(gameWithUsers.users);
                    gameViewModel.setNumPlayers(gameWithUsers.users.size());
                    gameViewModel.setCreator();
                    if (gameViewModel.getIsCreator()) {
                        binding.buttonJoinGame.setClickable(false);
                        binding.buttonJoinGame.setBackgroundResource(R.drawable.bg_button_grey);
                        binding.textGameCreator.setTextColor(getResources().getColor(R.color.green));
                    }
                }
                gameViewModel.getNumPlayers().observe(getViewLifecycleOwner(), new Observer<Integer>() {
                    @Override
                    public void onChanged(Integer integer) {
                        if (integer != 0) {
                            gameViewModel.updateIsGameFull();
                            binding.textNumPlayers.setText(integer + " / 10");
                        }
                    }
                });
            }
        });

        gameViewModel.getCreator().observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                binding.textGameCreator.setText(user.getFirstName() + " " + user.getLastName());
            }
        });

        gameViewModel.getIsGameFull().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    binding.buttonJoinGame.setClickable(false);
                    binding.buttonJoinGame.setBackgroundResource(R.drawable.bg_button_grey);
                }
            }
        });

        gameViewModel.getInGame().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    binding.buttonJoinGame.setClickable(false);
                    binding.buttonJoinGame.setBackgroundResource(R.drawable.bg_button_grey);
                    if (!gameViewModel.getIsCreator()) {
                        binding.buttonLeaveGame.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        binding.buttonJoinGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameViewModel.joinGame();
                Toast.makeText(getActivity(), "Joined Game", Toast.LENGTH_SHORT).show();
                binding.buttonJoinGame.setClickable(false);
                binding.buttonJoinGame.setBackgroundResource(R.drawable.bg_button_grey);
            }
        });

        binding.buttonLeaveGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameViewModel.leaveGame();
                Toast.makeText(getActivity(), "Left Game", Toast.LENGTH_SHORT).show();
                binding.buttonLeaveGame.setVisibility(View.INVISIBLE);
                binding.buttonJoinGame.setClickable(true);
                binding.buttonJoinGame.setBackgroundResource(R.drawable.bg_button);
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
