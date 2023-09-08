package com.example.gatask;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {

    @Query(" select * from user ")
    List<UserEntity> getAllUsers();

    @Query("SELECT * FROM user WHERE Email = :email")
    UserEntity getUserByEmail(String email);

    @Query("SELECT * FROM user WHERE Email = :email AND Password = :password")
    UserEntity getUserByEmailAndPassword(String email, String password);


    @Insert
    void adduser(UserEntity user);

    @Update
    void updateUser(UserEntity user);

    @Delete
    void deleteUser(UserEntity user);

}
