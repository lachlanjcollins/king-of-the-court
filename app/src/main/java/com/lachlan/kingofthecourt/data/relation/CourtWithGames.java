package com.lachlan.kingofthecourt.data.relation;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.lachlan.kingofthecourt.data.entity.Court;
import com.lachlan.kingofthecourt.data.entity.Game;

import java.util.List;

/**
 * Relation class which represents the relationship between courts and games.
 *
 * @author Lachlan Collins
 * @version 20 February 2022
 */
public class CourtWithGames {
    @Embedded
    public Court court;
    @Relation(
            parentColumn = "courtId",
            entityColumn = "locationId"
    )
    public List<Game> games;
}
