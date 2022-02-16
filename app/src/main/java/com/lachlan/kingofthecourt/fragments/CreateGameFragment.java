package com.lachlan.kingofthecourt.fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.lachlan.kingofthecourt.R;
import com.lachlan.kingofthecourt.activities.MainActivity;
import com.lachlan.kingofthecourt.data.entity.Court;
import com.lachlan.kingofthecourt.databinding.FragmentCreateGameBinding;
import com.lachlan.kingofthecourt.ui.viewmodel.CourtViewModel;
import com.lachlan.kingofthecourt.ui.viewmodel.CreateGameViewModel;

public class CreateGameFragment extends Fragment implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {
    private FragmentCreateGameBinding binding;
    private CreateGameViewModel createGameViewModel;
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCreateGameBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

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
                //@TODO: Check if date is in the past
                if (binding.editDate.getText().toString() != "" && binding.editTime.getText().toString() != "") {
                    createGameViewModel.setDateTime(year, month, day, hour, minute);
                    createGameViewModel.createGame(court);
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
        binding.editTime.setText("" + hour + ":" + minute);
        this.hour = hour;
        this.minute = minute;
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        binding.editDate.setText(day + "/" + month + "/" + year);
        this.year = year;
        this.month = month;
        this.day = day;
    }
}