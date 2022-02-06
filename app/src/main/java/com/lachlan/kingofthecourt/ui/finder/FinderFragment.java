package com.lachlan.kingofthecourt.ui.finder;



import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.lachlan.kingofthecourt.R;
import com.lachlan.kingofthecourt.databinding.FragmentFinderBinding;


import java.util.List;

public class FinderFragment extends Fragment {

    private FinderViewModel finderViewModel;
    private FragmentFinderBinding binding;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        finderViewModel =
                new ViewModelProvider(this).get(FinderViewModel.class);

        binding = FragmentFinderBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        return view;
    }

}