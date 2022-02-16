package com.lachlan.kingofthecourt.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lachlan.kingofthecourt.activities.MainActivity;
import com.lachlan.kingofthecourt.data.entity.Game;
import com.lachlan.kingofthecourt.data.relation.CourtWithGames;
import com.lachlan.kingofthecourt.databinding.FragmentCourtBinding;
import com.lachlan.kingofthecourt.data.entity.Court;
import com.lachlan.kingofthecourt.ui.adapters.CourtRecyclerAdapter;
import com.lachlan.kingofthecourt.ui.viewmodel.CourtViewModel;
import com.lachlan.kingofthecourt.ui.viewmodel.SharedViewModel;

import java.util.ArrayList;
import java.util.List;

public class CourtFragment extends Fragment {
    private FragmentCourtBinding binding;
    private CourtRecyclerAdapter adapter;
    private CourtViewModel courtViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentCourtBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        courtViewModel = ViewModelProvider
                .AndroidViewModelFactory
                .getInstance(getActivity().getApplication())
                .create(CourtViewModel.class);

        Court court = CourtFragmentArgs.fromBundle(getArguments()).getCourt();

        courtViewModel.setCurrentCourt(court.getCourtId());

        courtViewModel.getCurrentCourt().observe(getViewLifecycleOwner(), new Observer<Court>() {
            @Override
            public void onChanged(Court court) {
                ((MainActivity) getActivity()).setActionBarTitle(court.getLocationName() + " Games");
                courtViewModel.getGamesAtCourt().observe(getViewLifecycleOwner(), new Observer<CourtWithGames>() {
                    @Override
                    public void onChanged(CourtWithGames courtWithGames) {
                        RecyclerView recyclerView = binding.recyclerCourt;
                        adapter = new CourtRecyclerAdapter(courtWithGames.games, courtViewModel.getCurrentCourt());
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        recyclerView.setAdapter(adapter);
                    }
                });
            }
        });

        binding.buttonCreateGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = NavHostFragment.findNavController(getParentFragment());
                NavDirections nav = CourtFragmentDirections.actionNavigationCourtToCreateGameFragment(court);
                navController.navigate(nav);
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
