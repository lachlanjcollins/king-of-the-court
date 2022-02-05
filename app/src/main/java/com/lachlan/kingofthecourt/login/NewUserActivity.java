package com.lachlan.kingofthecourt.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.lachlan.kingofthecourt.Constants;
import com.lachlan.kingofthecourt.Database;
import com.lachlan.kingofthecourt.MainActivity;
import com.lachlan.kingofthecourt.Validation;
import com.lachlan.kingofthecourt.databinding.ActivityNewUserBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.lachlan.kingofthecourt.model.User;

import java.util.HashMap;
import java.util.Map;

public class NewUserActivity extends AppCompatActivity {
    private ActivityNewUserBinding binding;
    private FirebaseUser firebaseUser;
    private User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNewUserBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        Database db = new Database();
        Validation valid = new Validation();

        binding.buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstName = binding.editTextFName.getText().toString();
                String lastName = binding.editTextLName.getText().toString();
                String position = binding.editTextPosition.getText().toString();

                if (!(valid.isBlank(firstName) || valid.isBlank(lastName) || valid.isBlank(position))) {
                    user = new User(firebaseUser.getUid(), firstName, lastName, firebaseUser.getEmail(), position);
                    db.registerUser(NewUserActivity.this, user);
                } else
                    Toast.makeText(NewUserActivity.this, "Please Fill All Fields", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onRegistrationSuccess(User user) {
        Toast.makeText(this, "Registration Success", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(NewUserActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
