package com.lachlan.kingofthecourt.data.database;

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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.lachlan.kingofthecourt.activities.MainActivity;
import com.lachlan.kingofthecourt.activities.NewUserActivity;
import com.lachlan.kingofthecourt.data.entity.Court;
import com.lachlan.kingofthecourt.data.entity.Game;
import com.lachlan.kingofthecourt.data.entity.Location;
import com.lachlan.kingofthecourt.data.entity.User;
import com.lachlan.kingofthecourt.data.relation.UserGameCrossRef;
import com.lachlan.kingofthecourt.data.repository.CourtRepository;
import com.lachlan.kingofthecourt.data.repository.GameRepository;
import com.lachlan.kingofthecourt.data.repository.UserRepository;
import com.lachlan.kingofthecourt.ui.viewmodel.CourtViewModel;
import com.lachlan.kingofthecourt.ui.viewmodel.FinderViewModel;
import com.lachlan.kingofthecourt.fragments.EditProfileFragment;
import com.lachlan.kingofthecourt.util.Validation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RemoteDB {
    private FirebaseFirestore firebaseFirestore;
    private Validation valid;

    public RemoteDB() {
        firebaseFirestore = FirebaseFirestore.getInstance();
        valid = new Validation();
    }

    public void registerUser(NewUserActivity activity, User userInfo) {
        firebaseFirestore.collection("users")
                .document(userInfo.getUserId())
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

        public void getCurrentUser(UserRepository userRepository) {
        firebaseFirestore.collection("users").document(getCurrentUserID()).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                User user = documentSnapshot.toObject(User.class);
                userRepository.insertUser(user);
            }
        });
    }

    public void updateUser(User user) {
        firebaseFirestore.collection("users")
                .document(user.getUserId())
                .set(user, SetOptions.merge())
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("DATABASE FAIL", "Failed to add information to database.");
                    }
                });
    }

    public void getAllCourts(CourtRepository courtRepository) {
        firebaseFirestore.collection("courts").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String id = document.getId();
                                String locationName = document.getData().get("locationName").toString();
                                Location location = convertGeoToLocation((GeoPoint) document.getData().get("latLng"));
                                courtRepository.insertCourt(new Court(id, locationName, location));
                            }
                        } else {
                            Log.d("FAIL", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    public void getAllGames(GameRepository gameRepository, UserRepository userRepository) {
//        gameRepository.deleteAll();
        firebaseFirestore.collection("games").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String gameId = document.getId();
                                String creatorId = document.getData().get("creatorId").toString();
                                Timestamp timestamp = (Timestamp) document.getData().get("dateTime");
                                Date dateTime = timestamp.toDate();
                                String courtId = document.getData().get("courtId").toString();

                                if (valid.inFuture(dateTime)) {
                                    gameRepository.insertGame(new Game(gameId, creatorId, dateTime, courtId));

                                    List<String> players = (List<String>) document.get("players");
                                    for (String playerId : players) {
                                        firebaseFirestore.collection("users").document(playerId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                            @Override
                                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                User user = documentSnapshot.toObject(User.class);
                                                userRepository.insertUser(user);
                                                userRepository.insertUserGameRef(user.getUserId(), gameId);
                                            }
                                        });
                                    }
                                }
                            }
                        } else {
                            Log.d("FAIL", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    public void getAllGameUsers(String gameId, UserRepository userRepository) {
        userRepository.deleteAllUserRefsForGame(gameId);
        firebaseFirestore.collection("games")
                .document(gameId)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnap) {
                        List<String> players = (List<String>) documentSnap.get("players");
                        for (String playerId : players) {
                            firebaseFirestore.collection("users").document(playerId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    User user = documentSnapshot.toObject(User.class);
                                    userRepository.insertUser(user);
                                    userRepository.insertUserGameRef(user.getUserId(), gameId);
                                }
                            });
                        }
                    }
                });
    }

    public void createNewGame(String courtId, String creatorId, Date dateTime, GameRepository gameRepository) {
        Timestamp timestamp = new Timestamp(dateTime);

        Map<String, Object> game = new HashMap<>();
        game.put("courtId", courtId);
        game.put("creatorId", creatorId);
        game.put("dateTime", timestamp);
        game.put("players", Arrays.asList(creatorId));

        firebaseFirestore.collection("games").add(game).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if (task.isSuccessful()) {
                    Game newGame = new Game(task.getResult().getId(), creatorId, dateTime, courtId);
                    gameRepository.insertGame(newGame);
                }
            }
        });
    }

    public void joinGame(String userId, String gameId) {
        firebaseFirestore.collection("games")
                .document(gameId)
                .update("players", FieldValue.arrayUnion(userId));
    }

    public void leaveGame(String userId, String gameId) {
        firebaseFirestore.collection("games")
                .document(gameId)
                .update("players", FieldValue.arrayRemove(userId));
    }

    public Location convertGeoToLocation(GeoPoint geoPoint) {
        double lat = geoPoint.getLatitude();
        double lon = geoPoint.getLongitude();
        return new Location(lat, lon);
    }
}
