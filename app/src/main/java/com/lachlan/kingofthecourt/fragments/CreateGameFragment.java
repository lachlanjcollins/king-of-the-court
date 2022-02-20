package com.lachlan.kingofthecourt.fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import com.lachlan.kingofthecourt.activities.MainActivity;
import com.lachlan.kingofthecourt.data.entity.Court;
import com.lachlan.kingofthecourt.databinding.FragmentCreateGameBinding;
import com.lachlan.kingofthecourt.ui.viewmodel.CreateGameViewModel;
import com.lachlan.kingofthecourt.util.Validation;

public class CreateGameFragment extends Fragment implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {
    private FragmentCreateGameBinding binding;
    private CreateGameViewModel createGameViewModel;
    private Validation valid;
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCreateGameBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        valid = new Validation();

        createGameViewModel = ViewModelProvider
                .AndroidViewModelFactory
                .getInstance(getActivity().getApplication())
                .create(CreateGameViewModel.class);

        Court court = CreateGameFragmentArgs.fromBundle(getArguments()).getCourt();
        ((MainActivity) getActivity()).setActionBarTitle("Create Game");

        binding.editLocation.setText(court.getLocationName());

        binding.buttonTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new TimePickerFragment();
                newFragment.show(getChildFragmentManager(), "timePicker");
            }
        });

        binding.buttonDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getChildFragmentManager(), "datePicker");
            }
        });

        binding.buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (valid.isBlank(binding.editDate.getText().toString()) || valid.isBlank(binding.editTime.getText().toString())) {
                    // User has not entered data in all fields
                    Toast.makeText(getContext(), "Enter All Fields", Toast.LENGTH_SHORT).show();

                } else if (!createGameViewModel.setDateTime(year, month, day, hour, minute)) {
                    // Game date / time is in the past
                    Toast.makeText(getContext(), "Game must be in the future", Toast.LENGTH_SHORT).show();

                } else {
                    // Create game and navigate to home screen
                    createGameViewModel.createGame(court);
                    Toast.makeText(getContext(), "Game Created", Toast.LENGTH_SHORT).show();
                    NavController navController = NavHostFragment.findNavController(getParentFragment());
                    NavDirections nav = CreateGameFragmentDirections.actionNavigationCreateGameToNavigationHome();
                    navController.navigate(nav);
                }
            }
        });

        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
        // Triggered when a time is selected in the time picker dialog
        binding.editTime.setText("" + hour + ":" + minute);
        this.hour = hour;
        this.minute = minute;
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        // Triggered when a date is selected in the date picker dialog
        binding.editDate.setText(day + "/" + month + "/" + year);
        this.year = year;
        this.month = month;
        this.day = day;
    }
}