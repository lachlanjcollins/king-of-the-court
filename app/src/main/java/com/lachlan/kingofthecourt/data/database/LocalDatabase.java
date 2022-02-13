package com.lachlan.kingofthecourt.data.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.lachlan.kingofthecourt.data.entity.Court;
import com.lachlan.kingofthecourt.data.entity.Game;
import com.lachlan.kingofthecourt.data.entity.User;


@Database(
    entities = {
            User.class,
            Court.class,
            Game.class
    },
    version = 1)

public abstract class LocalDatabase extends RoomDatabase {
}
