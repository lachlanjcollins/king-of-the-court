package com.lachlan.kingofthecourt.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lachlan.kingofthecourt.activities.MainActivity;
import com.lachlan.kingofthecourt.databinding.FragmentCourtBinding;
import com.lachlan.kingofthecourt.data.entity.Court;
import com.lachlan.kingofthecourt.ui.adapters.CourtRecyclerAdapter;
import com.lachlan.kingofthecourt.ui.viewmodel.CourtViewModel;

public class CourtFragment extends Fragment {
    private FragmentCourtBinding binding;
    private CourtRecyclerAdapter adapter;
    private CourtViewModel courtViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentCourtBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        Court court = CourtFragmentArgs.fromBundle(getArguments()).getCourt();
        ((MainActivity) getActivity()).setActionBarTitle(court.getLocationName() + " Games");

        courtViewModel =
                new ViewModelProvider(this).get(CourtViewModel.class);
        courtViewModel.setCourt(court);
        courtViewModel.initGamesList();

        courtViewModel.getListReady().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                RecyclerView recyclerView = binding.recyclerCourt;
                adapter = new CourtRecyclerAdapter(courtViewModel.getGamesList(), court);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(adapter);
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
