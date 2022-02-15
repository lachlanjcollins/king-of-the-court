package com.lachlan.kingofthecourt.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.lachlan.kingofthecourt.data.entity.Game;
import com.lachlan.kingofthecourt.data.relation.GameWithUsers;

import java.util.List;

@Dao
public interface GameDAO {
    @Query("SELECT * FROM game ORDER BY dateTime ASC")
    LiveData<List<Game>> getAll();

    @Query("SELECT * FROM game WHERE gameId = :id LIMIT 1")
    Game findByID(String id);

    @Transaction
    @Query("SELECT * FROM Game")
    LiveData<List<GameWithUsers>> getAllGameWithUsers();

    @Transaction
    @Query("SELECT * FROM Game WHERE gameId = :id")
    LiveData<GameWithUsers> getAllGameUsers(String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Game game);

    @Delete
    void delete(Game game);

    @Update
    void updateGame(Game game);

    @Query("DELETE FROM game")
    void deleteAll();
}
