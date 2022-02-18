package com.lachlan.kingofthecourt.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lachlan.kingofthecourt.data.entity.User;
import com.lachlan.kingofthecourt.data.relation.UserWithGames;
import com.lachlan.kingofthecourt.databinding.FragmentHomeBinding;
import com.lachlan.kingofthecourt.ui.adapters.CourtRecyclerAdapter;
import com.lachlan.kingofthecourt.ui.adapters.HomeRecyclerAdapter;
import com.lachlan.kingofthecourt.ui.viewmodel.CourtViewModel;
import com.lachlan.kingofthecourt.ui.viewmodel.HomeViewModel;
import com.lachlan.kingofthecourt.ui.viewmodel.SharedViewModel;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private SharedViewModel sharedViewModel;
    private FragmentHomeBinding binding;
    private HomeRecyclerAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        homeViewModel = ViewModelProvider
                .AndroidViewModelFactory
                .getInstance(getActivity().getApplication())
                .create(HomeViewModel.class);

        sharedViewModel = ViewModelProvider
                .AndroidViewModelFactory
                .getInstance(getActivity().getApplication())
                .create(SharedViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        final TextView textView = binding.textHome;

        homeViewModel.getUserWithGames().observe(getViewLifecycleOwner(), new Observer<UserWithGames>() {
            @Override
            public void onChanged(UserWithGames userWithGames) {
                if (userWithGames.games != null && userWithGames.games.size() > 0) {
                    homeViewModel.sortGameList();
                    RecyclerView recyclerView = binding.recyclerHome;
                    adapter = new HomeRecyclerAdapter(userWithGames.games);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(adapter);
                }
            }
        });

        homeViewModel.getDate().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.textTodayDate.setText(s);
            }
        });

        sharedViewModel.getUser().observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                textView.setText("Welcome " + user.getFirstName() + ", here's a list of your upcoming games.");
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