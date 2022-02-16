package com.lachlan.kingofthecourt.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.lachlan.kingofthecourt.data.relation.UserGameCrossRef;

import java.util.List;

@Dao
public interface UserGameRefDAO {
    @Query("SELECT * FROM usergamecrossref")
    LiveData<List<UserGameCrossRef>> getAll();

    @Transaction
    @Query("SELECT * FROM usergamecrossref WHERE userId = :id LIMIT 1")
    LiveData<List<UserGameCrossRef>> findByUserId(String id);

    @Transaction
    @Query("SELECT * FROM usergamecrossref WHERE gameId = :id LIMIT 1")
    LiveData<List<UserGameCrossRef>> findByGameId(String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(UserGameCrossRef userGameCrossRef);

    @Delete
    void delete(UserGameCrossRef userGameCrossRef);

    @Transaction
    @Query("DELETE FROM usergamecrossref WHERE gameId = :id")
    void deleteAllUserRefsForGame(String id);

    @Update
    void updateUserGameCrossRef(UserGameCrossRef userGameCrossRef);

    @Query("DELETE FROM usergamecrossref")
    void deleteAll();
}
