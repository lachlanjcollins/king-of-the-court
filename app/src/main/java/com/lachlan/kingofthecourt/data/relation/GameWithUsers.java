package com.lachlan.kingofthecourt.data.relation;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import com.lachlan.kingofthecourt.data.entity.Game;
import com.lachlan.kingofthecourt.data.entity.User;

import java.util.List;

/**
 * Relation class which represents the relationship between games and its list of users.
 *
 * @author Lachlan Collins
 * @version 20 February 2022
 */
public class GameWithUsers {
    @Embedded
    public Game game;
    @Relation(
            parentColumn = "gameId",
            entityColumn = "userId",
            associateBy = @Junction(UserGameCrossRef.class)
    )
    public List<User> users;
}
