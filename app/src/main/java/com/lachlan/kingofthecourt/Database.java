package com.lachlan.kingofthecourt;

import android.util.Log;
import androidx.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.lachlan.kingofthecourt.login.NewUserActivity;
import com.lachlan.kingofthecourt.model.Court;
import com.lachlan.kingofthecourt.model.Game;
import com.lachlan.kingofthecourt.model.User;
import com.lachlan.kingofthecourt.ui.finder.CourtViewModel;
import com.lachlan.kingofthecourt.ui.finder.FinderViewModel;
import com.lachlan.kingofthecourt.ui.profile.EditProfileFragment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
                        Log.e("DATABASE FAIL", "Failed to add information to database.");
                    }
                });
    }

    public void getCourtLocations(FinderViewModel finderViewModel) {
        ArrayList<Court> courts = new ArrayList<>();
        firebaseFirestore.collection("courts").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String id = document.getId();
                                String locationName = document.getData().get("locationName").toString();
                                LatLng latLng = convertGeo((GeoPoint) document.getData().get("latLng"));
                                courts.add(new Court(id, locationName, latLng));
                            }
                            finderViewModel.onGetCourtsResult(courts);
                        } else {
                            Log.d("FAIL", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    public void getGamesList(Court court, CourtViewModel courtViewModel) {
        ArrayList<Game> games = new ArrayList<>();
        firebaseFirestore.collection("courts").document(court.getId()).collection("games").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Timestamp timestamp = (Timestamp) document.getData().get("timestamp");
                        Date dateTime = timestamp.toDate();
                        List<String> playerIDs = (List<String>) document.get("players");
                        ArrayList<User> players = new ArrayList<>();
                        for (String playerID : playerIDs) {
                            User player = new User(playerID);
                            players.add(player);
                        }
                        User creator = new User(document.getData().get("creator").toString());
                        games.add(new Game(creator, dateTime, players));
                    }
                    courtViewModel.onGamesListRetrieved(games);
                }
            }
        });
    }

    public LatLng convertGeo(GeoPoint geoPoint) {
        double lat = geoPoint.getLatitude();
        double lon = geoPoint.getLongitude();
        LatLng latLng = new LatLng(lat, lon);
        return latLng;
    }
}
