package com.lachlan.kingofthecourt.data.relation;

import androidx.annotation.NonNull;
import androidx.room.Entity;

/**
 * Entity class which represents the many to many relationship between games and users.
 *
 * @author Lachlan Collins
 * @version 20 February 2022
 */
@Entity(primaryKeys = {"userId", "gameId"})
public class UserGameCrossRef {

    @NonNull
    public String userId;

    @NonNull
    public String gameId;

    public UserGameCrossRef(String userId, String gameId) {
        this.userId = userId;
        this.gameId = gameId;
    }
}
