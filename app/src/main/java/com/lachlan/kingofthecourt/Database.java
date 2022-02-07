package com.lachlan.kingofthecourt;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.lachlan.kingofthecourt.login.NewUserActivity;
import com.lachlan.kingofthecourt.model.User;
import com.lachlan.kingofthecourt.ui.finder.FinderFragment;
import com.lachlan.kingofthecourt.ui.profile.EditProfileFragment;
import com.lachlan.kingofthecourt.ui.profile.EditProfileViewModel;

public class Database {
    private FirebaseFirestore firebaseFirestore;

    public Database() {
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    public void registerUser(NewUserActivity activity, User userInfo) {
        firebaseFirestore.collection("users")
                .document(userInfo.getId())
                .set(userInfo, SetOptions.merge())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        activity.onRegistrationSuccess(userInfo);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                       Log.e("FAIL", "Failed to add information to database.");
                    }
                });
    }

    public String getCurrentUserID() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String currentUserID = "";
        if (firebaseUser != null) {
            currentUserID = firebaseUser.getUid();
        }
        return currentUserID;
    }

    public void getCurrentUser(MainActivity activity) {
        firebaseFirestore.collection("users").document(getCurrentUserID()).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                User user = documentSnapshot.toObject(User.class);
                activity.setCurrentUser(user);
            }
        });
    }

    public void updateUser(EditProfileFragment editProfileFragment, User user) {
        firebaseFirestore.collection("users")
                .document(user.getId())
                .set(user, SetOptions.merge())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        editProfileFragment.onUpdateSuccess();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("FAIL", "Failed to add information to database.");
                    }
                });
    }

    public void getCourtLocations(FinderFragment finderFragment) {
        firebaseFirestore.collection("cities").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("TAG", document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}
