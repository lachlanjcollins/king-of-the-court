package com.lachlan.kingofthecourt.data.relation;

import androidx.annotation.NonNull;
import androidx.room.Entity;

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
