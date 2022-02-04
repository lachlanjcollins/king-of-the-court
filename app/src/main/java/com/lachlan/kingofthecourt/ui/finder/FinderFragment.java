package com.lachlan.kingofthecourt.ui.finder;

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

import com.lachlan.kingofthecourt.databinding.FragmentFinderBinding;

public class FinderFragment extends Fragment {

    // @TODO: Update class to use bindings on layout elements?
    // @TODO: Clean up class to only use necessary stuff.

    private FinderViewModel finderViewModel;
    private FragmentFinderBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        finderViewModel =
                new ViewModelProvider(this).get(FinderViewModel.class);

        binding = FragmentFinderBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        final TextView textView = binding.textFinder;
        finderViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
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