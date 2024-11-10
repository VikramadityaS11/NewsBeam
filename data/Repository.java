package com.example.myapplication.data;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.models.Article;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {
    private final Dao articleDao;
    private final ExecutorService executorService;


    public Repository(Application application){
        AppDatabase db = AppDatabase.getINSTANCE(application);
        articleDao = db.articleDao();
        executorService = Executors.newFixedThreadPool(2);
    }

    public LiveData<List<Article>> getAllArticles(){
        return articleDao.getAllArticles();
    }

    public void insertArticles(List<Article> articles){
        executorService.execute(()->{
            articleDao.deleteAllArticles();
            articleDao.insertArticles(articles);
        });
    }

}
