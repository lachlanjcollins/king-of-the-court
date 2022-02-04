package com.lachlan.kingofthecourt.login;



import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.lachlan.kingofthecourt.R;
import com.lachlan.kingofthecourt.Validation;
import com.lachlan.kingofthecourt.databinding.FragmentSignupBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupFragment extends Fragment {

    private FragmentSignupBinding binding;
    private FirebaseAuth auth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentSignupBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        auth = FirebaseAuth.getInstance();

        binding.registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.emailEditText.getText().toString();
                String pass = binding.passEditText.getText().toString();
                String confirmPass = binding.confirmPassEditText.getText().toString();

                Validation valid = new Validation();

                if (valid.isBlank(email) || valid.isBlank(pass)) {
                    Toast.makeText(getContext(), "Please enter email and password", Toast.LENGTH_LONG).show();
                } else if (!pass.equals(confirmPass)) {
                    binding.passEditText.setBackgroundResource(R.drawable.bg_edit_text_red);
                    binding.confirmPassEditText.setBackgroundResource(R.drawable.bg_edit_text_red);
                    Toast.makeText(getContext(), "Passwords do not match", Toast.LENGTH_LONG).show();
                } else {
                    registerUser(email, pass);
                }
            }
        });

        return view;
    }

    private void registerUser(String email, String pass) {
        auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getContext(), NewUserActivity.class));
                } else {
                    Toast.makeText(getContext(), "Sign Up Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}