package com.example.myapplication.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.myapplication.models.Article;

@Database(entities = {Article.class},version = 1,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract Dao articleDao();
    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getINSTANCE(Context context) {
        if(INSTANCE == null){
            synchronized (AppDatabase.class) {
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class,"news_database").fallbackToDestructiveMigration().build();
                }
            }
        }
        return INSTANCE;
    }
}
