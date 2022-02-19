package com.lachlan.kingofthecourt.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.lachlan.kingofthecourt.R;
import com.lachlan.kingofthecourt.activities.LoginActivity;
import com.lachlan.kingofthecourt.data.entity.User;
import com.lachlan.kingofthecourt.databinding.FragmentProfileBinding;
import com.lachlan.kingofthecourt.ui.viewmodel.SharedViewModel;
import com.lachlan.kingofthecourt.util.Validation;

public class ProfileFragment extends Fragment {

    private SharedViewModel sharedViewModel;
    private FragmentProfileBinding binding;
    private Validation valid;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        sharedViewModel = ViewModelProvider
                .AndroidViewModelFactory
                .getInstance(getActivity().getApplication())
                .create(SharedViewModel.class);

        valid = new Validation();

        final TextView textProfile = binding.textProfile;
        final EditText editFName = binding.editFName;
        final EditText editLName = binding.editLName;
        final EditText editPosition = binding.editPosition;
        final AppCompatButton buttonEditProfile = binding.buttonEditProfile;
        final AppCompatButton buttonUpdate = binding.buttonUpdate;
        final AppCompatButton buttonLogOut = binding.buttonLogOut;
        final AppCompatButton buttonAbout = binding.buttonAbout;

        NavController navController = NavHostFragment.findNavController(this);


        sharedViewModel.getUser().observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                textProfile.setText(user.getFirstName() + " " + user.getLastName());
            }
        });

        buttonLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Sign out google client.
                GoogleSignIn.getClient(getContext(), new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build()).signOut();
                // Sign out firebase client.
                FirebaseAuth.getInstance().signOut();

                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });

        sharedViewModel.getUser().observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                editFName.setText(user.getFirstName());
                editLName.setText(user.getLastName());
                editPosition.setText(user.getPosition());
            }
        });

        buttonEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.buttonUpdate.setVisibility(View.VISIBLE);
                view.setBackgroundResource(R.drawable.bg_button_grey);
                view.setClickable(false);
                editFName.setTextColor(Color.BLACK);
                editLName.setTextColor(Color.BLACK);
                editPosition.setTextColor(Color.BLACK);
                editFName.setFocusable(true);
                editFName.setFocusableInTouchMode(true);
                editFName.setClickable(true);
                editLName.setFocusable(true);
                editLName.setFocusableInTouchMode(true);
                editLName.setClickable(true);
                editPosition.setFocusable(true);
                editPosition.setClickable(true);
                editPosition.setFocusableInTouchMode(true);
            }
        });

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fName = editFName.getText().toString();
                String lName = editLName.getText().toString();
                String position = editPosition.getText().toString();

                if (valid.isBlank(fName) || valid.isBlank(lName) || valid.isBlank(position)) {
                    Toast.makeText(getContext(), "Please enter valid details", Toast.LENGTH_SHORT);
                } else {
                    view.setVisibility(View.INVISIBLE);
                    buttonEditProfile.setBackgroundResource(R.drawable.bg_button_white);
                    buttonEditProfile.setClickable(true);
                    editFName.setTextColor(getResources().getColor(R.color.grey));
                    editLName.setTextColor(getResources().getColor(R.color.grey));
                    editPosition.setTextColor(getResources().getColor(R.color.grey));
                    editFName.setFocusable(false);
                    editFName.setFocusableInTouchMode(false);
                    editFName.setClickable(false);
                    editLName.setFocusable(false);
                    editLName.setFocusableInTouchMode(false);
                    editLName.setClickable(false);
                    editPosition.setFocusable(false);
                    editPosition.setClickable(false);
                    editPosition.setFocusableInTouchMode(false);
                    User user = sharedViewModel.getUser().getValue();
                    user.setFirstName(fName);
                    user.setLastName(lName);
                    user.setPosition(position);
                    sharedViewModel.updateUser(user);
                    Toast.makeText(getContext(), "Details updated", Toast.LENGTH_SHORT);
                }
            }
        });

        buttonAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavDirections nav = ProfileFragmentDirections.actionNavigationProfileToAboutFragment();
                navController.navigate(nav);
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