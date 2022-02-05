package com.lachlan.kingofthecourt.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.lachlan.kingofthecourt.R;
import com.lachlan.kingofthecourt.SharedViewModel;
import com.lachlan.kingofthecourt.databinding.FragmentEditProfileBinding;
import com.lachlan.kingofthecourt.model.User;

public class EditProfileFragment extends Fragment {

    private FragmentEditProfileBinding binding;
    private SharedViewModel sharedViewModel;
    private NavController navController;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentEditProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        navController = NavHostFragment.findNavController(this);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        final EditText editFName = binding.editFName;
        final EditText editLName = binding.editLName;
        final EditText editPosition = binding.editPosition;

        sharedViewModel.getUser().observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                editFName.setText(user.getFirstName());
                editLName.setText(user.getLastName());
                editPosition.setText(user.getPosition());
            }
        });

        binding.buttonSaveEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update(editFName.getText().toString(),
                        editLName.getText().toString(), editPosition.getText().toString());
            }
        });

        return root;
    }

    public void update(String fName, String lName, String position) {
        sharedViewModel.updateUser(this, fName, lName, position);
    }

    public void onUpdateSuccess() {
        navController.navigate(R.id.action_navigation_edit_profile_to_navigation_profile);
    }
}
