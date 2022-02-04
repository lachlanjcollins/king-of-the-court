package com.lachlan.kingofthecourt.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.lachlan.kingofthecourt.MainActivity;
import com.lachlan.kingofthecourt.Validation;
import com.lachlan.kingofthecourt.databinding.ActivityNewUserBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class NewUserActivity extends AppCompatActivity {
    private ActivityNewUserBinding binding;
    private FirebaseUser user;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNewUserBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        user = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseFirestore.getInstance();

        binding.buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstName = binding.editTextFName.getText().toString();
                String lastName = binding.editTextLName.getText().toString();
                String position = binding.editTextPosition.getText().toString();

                Validation valid = new Validation();

                if (!(valid.isBlank(firstName) || valid.isBlank(lastName) || valid.isBlank(position))) {
                    Map<String, Object> userData = new HashMap<>();
                    userData.put("fName", firstName);
                    userData.put("lName", lastName);
                    userData.put("position", position);
                    db.collection("users").document(user.getUid()).set(userData).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(NewUserActivity.this, "Data Saved", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(NewUserActivity.this, MainActivity.class));
                        }
                    });
                } else
                    Toast.makeText(NewUserActivity.this, "Please Fill All Fields", Toast.LENGTH_SHORT).show();
            }
        });



    }
}
