package com.lachlan.kingofthecourt.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.lachlan.kingofthecourt.data.entity.Game;

import java.util.List;

@Dao
public interface GameDAO {
    @Query("SELECT * FROM game ORDER BY dateTime ASC")
    LiveData<List<Game>> getAll();

    @Query("SELECT * FROM game WHERE gameId = :id LIMIT 1")
    Game findByID(String id);

    @Insert
    void insert(Game game);

    @Delete
    void delete(Game game);

    @Update
    void updateGame(Game game);

    @Query("DELETE FROM game")
    void deleteAll();
}
