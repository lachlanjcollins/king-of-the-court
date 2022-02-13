package com.lachlan.kingofthecourt.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.lachlan.kingofthecourt.R;
import com.lachlan.kingofthecourt.ui.viewmodel.ProfileViewModel;
import com.lachlan.kingofthecourt.ui.viewmodel.SharedViewModel;
import com.lachlan.kingofthecourt.databinding.FragmentProfileBinding;
import com.lachlan.kingofthecourt.activities.LoginActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.lachlan.kingofthecourt.data.entity.User;

public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;
    private SharedViewModel sharedViewModel;
    private FragmentProfileBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        NavController navController = NavHostFragment.findNavController(this);

        final TextView textProfile = binding.textProfile;

        sharedViewModel.getUser().observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                textProfile.setText(user.getFirstName() + " " + user.getLastName());
            }
        });

        binding.logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Sign out google client.
                GoogleSignIn.getClient(getContext(), new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build()).signOut();
                // Sign out firebase client.
                FirebaseAuth.getInstance().signOut();

                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });

        binding.recentGamesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        binding.editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_navigation_profile_to_navigation_edit_profile);
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