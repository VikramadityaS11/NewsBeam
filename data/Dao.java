package com.example.myapplication.data;

import androidx.lifecycle.LiveData;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.myapplication.models.Article;

import java.util.List;

@androidx.room.Dao
public interface Dao {
    @Insert
    void insertArticles(List<Article> articles);
    @Query("SELECT * FROM articles")
    LiveData<List<Article>> getAllArticles();
    @Query("DELETE FROM articles")
    void deleteAllArticles();

}
