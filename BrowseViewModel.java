package com.example.myapplication;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.myapplication.api.NewsApiService;
import com.example.myapplication.api.RetroFitClient;
import com.example.myapplication.data.Repository;
import com.example.myapplication.models.Article;
import com.example.myapplication.models.NewsResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BrowseViewModel extends AndroidViewModel {
    private final Repository repository;
    private final LiveData<List<Article>> allArticles;

    public BrowseViewModel(Application application) {
        super(application);
        repository = new Repository(application);
        allArticles = repository.getAllArticles();
    }

    public LiveData<List<Article>> getAllArticles() {
        return allArticles;
    }

    public LiveData<List<Article>> getArticles() {
        return allArticles;
    }

    public void fetchBreakingNews() {
        // Create the API service instance
        NewsApiService apiService = RetroFitClient.getClient().create(NewsApiService.class);

        // Fetch breaking news specific to India
        Call<NewsResponse> call = apiService.getBreakingNews("us", null, "aed1d9ff8db94f00b76d96dd1b6ea037", 10);

        call.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Insert the fetched articles into the repository
                    repository.insertArticles(response.body().getArticles());
                }
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                // Log the error (you might want to handle it in your UI)
            }
        });
    }
}
