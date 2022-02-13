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

import com.lachlan.kingofthecourt.databinding.FragmentLeaderboardsBinding;
import com.lachlan.kingofthecourt.ui.viewmodel.LeaderboardsViewModel;

public class LeaderboardsFragment extends Fragment {

    // @TODO: Update class to use bindings on layout elements?
    // @TODO: Clean up class to only use necessary stuff.

    private LeaderboardsViewModel leaderboardsViewModel;
    private FragmentLeaderboardsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        leaderboardsViewModel =
                new ViewModelProvider(this).get(LeaderboardsViewModel.class);

        binding = FragmentLeaderboardsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textDashboard;
        leaderboardsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}