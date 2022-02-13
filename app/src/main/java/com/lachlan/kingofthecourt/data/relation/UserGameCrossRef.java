package com.lachlan.kingofthecourt.data.relation;

import androidx.room.Entity;

@Entity(primaryKeys = {"userId", "gameId"})
public class UserGameCrossRef {
    public String userId;
    public String gameId;
}
