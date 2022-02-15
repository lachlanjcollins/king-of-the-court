package com.lachlan.kingofthecourt.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.lachlan.kingofthecourt.data.entity.Court;
import com.lachlan.kingofthecourt.data.entity.Game;
import com.lachlan.kingofthecourt.data.relation.CourtWithGames;

import java.util.List;

@Dao
public interface CourtDAO {
    @Query("SELECT * FROM court ORDER BY locationName ASC")
    LiveData<List<Court>> getAll();

    @Query("SELECT * FROM court WHERE courtId = :id LIMIT 1")
    LiveData<Court> findByID(String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Court court);

    @Insert
    void insertGame(Game game);

    @Delete
    void delete(Court court);

    @Update
    void updateCourt(Court court);

    @Transaction
    @Query("SELECT * FROM court")
    LiveData<List<CourtWithGames>> getAllCourtWithGames();

    @Transaction
    @Query("SELECT * FROM court WHERE courtId = :id")
    LiveData<CourtWithGames> getGamesAtCourt(String id);

    @Query("DELETE FROM court")
    void deleteAll();
}
