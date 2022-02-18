package com.lachlan.kingofthecourt.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.lachlan.kingofthecourt.data.entity.User;
import com.lachlan.kingofthecourt.data.relation.UserWithGames;

import java.util.List;

@Dao
public interface UserDAO {

    @Query("SELECT * FROM user ORDER BY lastName ASC")
    LiveData<List<User>> getAll();

    @Query("SELECT * FROM user WHERE userId = :id LIMIT 1")
    LiveData<User> findByID(String id);

    @Transaction
    @Query("SELECT * FROM User")
    LiveData<List<UserWithGames>> getAllUserWithGames();

    @Transaction
    @Query("SELECT * FROM User WHERE userId = :id")
    LiveData<UserWithGames> getAllUserGames(String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User user);

    @Delete
    void delete(User user);

    @Update
    void updateUser(User user);

    @Query("DELETE FROM user")
    void deleteAll();
}
