package com.lachlan.kingofthecourt.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.lachlan.kingofthecourt.activities.MainActivity;
import com.lachlan.kingofthecourt.activities.NewUserActivity;
import com.lachlan.kingofthecourt.databinding.FragmentLoginBinding;
import com.lachlan.kingofthecourt.util.Validation;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;
    private FirebaseAuth auth;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        auth = FirebaseAuth.getInstance();

        binding.signinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.emailEditText.getText().toString();
                String pass = binding.passEditText.getText().toString();

                Validation valid = new Validation();

                if (valid.isBlank(email) || valid.isBlank(pass)) {
                    Toast.makeText(getContext(), "Please Enter Email and Password", Toast.LENGTH_SHORT).show();
                } else {
                    loginUser(email, pass);
                }
            }
        });

        return view;
    }

    private void loginUser(String email, String pass) {
        auth.signInWithEmailAndPassword(email, pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(getContext(), "Login Success", Toast.LENGTH_SHORT).show();
                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
                firebaseFirestore.collection("users").document(firebaseUser.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot doc = task.getResult();
                            if (doc.exists()) {
                                startActivity(new Intent(getContext(), MainActivity.class));
                            } else
                                startActivity(new Intent(getContext(), NewUserActivity.class));
                        }
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Invalid Email / Password", Toast.LENGTH_SHORT).show();
            }
        });
    }
}