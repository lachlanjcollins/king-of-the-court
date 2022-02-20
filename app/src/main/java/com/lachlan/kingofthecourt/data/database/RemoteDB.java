package com.lachlan.kingofthecourt.data.database;

import android.util.Log;

import androidx.annotation.NonNull;

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
import com.lachlan.kingofthecourt.activities.NewUserActivity;
import com.lachlan.kingofthecourt.data.entity.Court;
import com.lachlan.kingofthecourt.data.entity.Game;
import com.lachlan.kingofthecourt.data.entity.Location;
import com.lachlan.kingofthecourt.data.entity.User;
import com.lachlan.kingofthecourt.data.repository.CourtRepository;
import com.lachlan.kingofthecourt.data.repository.GameRepository;
import com.lachlan.kingofthecourt.data.repository.UserRepository;
import com.lachlan.kingofthecourt.util.Validation;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class that facilitates interaction with remote Firestore Database.
 *
 * @author Lachlan Collins
 * @version 20 February 2022
 */
public class RemoteDB {
    private final FirebaseFirestore firebaseFirestore;
    private final Validation valid;

    /**
     * Default constructor to initialise object of the RemoteDB class.
     */
    public RemoteDB() {
        firebaseFirestore = FirebaseFirestore.getInstance();
        valid = new Validation();
    }

    /**
     * Method that registers a user by inputting their data into the Firestore database.
     *
     * @param activity An object of the NewUserActivity to return to upon successful registration.
     * @param userInfo An object of the User class representing the user to be registered.
     */
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

    /**
     * Method that returns the current logged in users ID.
     *
     * @return A string representing the logged in user's UID.
     */
    public String getCurrentUserID() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String currentUserID = "";
        if (firebaseUser != null) {
            currentUserID = firebaseUser.getUid();
        }
        return currentUserID;
    }

    /**
     * Method that updates the local android room database with information on the current logged in user.
     *
     * @param userRepository An object of the UserRepository class.
     */
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

    /**
     * Method that updates the remote Firestore database with new user information.
     *
     * @param user An object of the User class that represents the updated data.
     */
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

    /**
     * Method that updates the local android room database with information on all of the courts.
     *
     * @param courtRepository An object of the CourtRepository class.
     */
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

    /**
     * Method that updates the local android room database with information on all games and users associated with each game.
     *
     * @param gameRepository An object of the GameRepository class.
     * @param userRepository An object of the UserRepository class.
     */
    public void getAllGames(GameRepository gameRepository, UserRepository userRepository) {
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
                                        // Cycles through each user associated with the games and inserts the user data as well as the UserGame reference
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

    /**
     * Method that updates the local android room database with information on a specific game and all users associated with the game.
     *
     * @param gameId A string representing the gameId of the game data being retrieved.
     * @param userRepository An object of the UserRepository class.
     */
    public void getAllGameUsers(String gameId, UserRepository userRepository) {
        // Need to delete and refresh all GameUser refs so in case a user has left the specified game
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

    /**
     * Method that inserts a new game into the remote Firestore database.
     *
     * @param courtId A string representing the courtId at which the game is being created.
     * @param creatorId A string representing the creatorId of the game's creator.
     * @param dateTime A date object representing the date and time at which the game is occurring.
     * @param gameRepository An object of the GameRepository class.
     */
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

    /**
     * Method that inserts a user into the specified game in the remote Firestore database.
     *
     * @param userId A string representing the userId of the user joining the game.
     * @param gameId A string representing the gameId of the game being joined.
     */
    public void joinGame(String userId, String gameId) {
        firebaseFirestore.collection("games")
                .document(gameId)
                .update("players", FieldValue.arrayUnion(userId));
    }

    /**
     * Method that deletes a user from the specified game in the remote Firestore database.
     *
     * @param userId A string representing the userId of the user leaving the game.
     * @param gameId A string representing the gameId of the game being left.
     */
    public void leaveGame(String userId, String gameId) {
        firebaseFirestore.collection("games")
                .document(gameId)
                .update("players", FieldValue.arrayRemove(userId));
    }

    /**
     * Method used to convert a GeoPoint object retrieved from Firestore into a Location object.
     *
     * @param geoPoint A GeoPoint object.
     * @return A location object.
     */
    public Location convertGeoToLocation(GeoPoint geoPoint) {
        double lat = geoPoint.getLatitude();
        double lon = geoPoint.getLongitude();
        return new Location(lat, lon);
    }
}
