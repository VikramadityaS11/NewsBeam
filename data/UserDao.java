package com.example.myapplication.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.myapplication.models.User;

@Dao
public interface UserDao {
    @Insert
    void insertUser(User user);

    @Query("SELECT * FROM user_table WHERE email = :email")
    LiveData<User> getUserByEmail(String email);

    @Query("SELECT * FROM user_table WHERE email = :email")
    User getUserByEmailSync(String email);

    @Query("DELETE FROM user_table")
    void deleteAllUsers();
}
