package com.lachlan.kingofthecourt;

import android.util.Log;

import androidx.annotation.NonNull;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.lachlan.kingofthecourt.login.NewUserActivity;
import com.lachlan.kingofthecourt.model.User;


public class Database {
    private FirebaseFirestore db;
    private DocumentSnapshot doc;

    public Database() {
        db = FirebaseFirestore.getInstance();
    }

    public void registerUser(NewUserActivity activity, User userInfo) {
        db.collection("users")
                .document(userInfo.getId())
                .set(userInfo, SetOptions.merge())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        activity.onRegistrationSuccess();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                       Log.e("FAIL", "Failed to add information to database.");
                    }
                });
    }
}
