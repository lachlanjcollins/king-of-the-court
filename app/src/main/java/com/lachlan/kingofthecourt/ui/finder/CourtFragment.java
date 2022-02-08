package com.lachlan.kingofthecourt.ui.finder;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.lachlan.kingofthecourt.MainActivity;
import com.lachlan.kingofthecourt.databinding.FragmentCourtBinding;
import com.lachlan.kingofthecourt.model.Court;

public class CourtFragment extends Fragment {
    private FragmentCourtBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentCourtBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        Court court = CourtFragmentArgs.fromBundle(getArguments()).getCourt();
        ((MainActivity) getActivity()).setActionBarTitle(court.getLocationName());

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
