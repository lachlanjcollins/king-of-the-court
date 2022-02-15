package com.lachlan.kingofthecourt.data.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.lachlan.kingofthecourt.data.dao.CourtDAO;
import com.lachlan.kingofthecourt.data.dao.GameDAO;
import com.lachlan.kingofthecourt.data.dao.UserDAO;
import com.lachlan.kingofthecourt.data.entity.Court;
import com.lachlan.kingofthecourt.data.entity.Game;
import com.lachlan.kingofthecourt.data.entity.User;
import com.lachlan.kingofthecourt.data.relation.UserGameCrossRef;
import com.lachlan.kingofthecourt.util.Converters;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(
        entities = {
                User.class,
                Court.class,
                Game.class,
                UserGameCrossRef.class
        },
        version = 2)

@TypeConverters({Converters.class})

public abstract class LocalDB extends RoomDatabase {
    public abstract CourtDAO courtDAO();
    public abstract GameDAO gameDAO();
    public abstract UserDAO userDAO();

    private static LocalDB INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;

    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static synchronized LocalDB getInstance(final Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    LocalDB.class, "LocalDatabase")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }
}
