package com.example.gatask;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = UserEntity.class,exportSchema = false,version = 1)
public abstract class DB extends RoomDatabase {
     private static final String DB_NAME = "UserDb";
     private static DB instance;

     public static synchronized DB getDB(Context context){
         if(instance==null){
             instance = Room.databaseBuilder(context, DB.class,DB_NAME)
                     .fallbackToDestructiveMigration()
                     .allowMainThreadQueries()
                     .build();
         }
         return instance;
     }
    public abstract UserDao userDao();
}
