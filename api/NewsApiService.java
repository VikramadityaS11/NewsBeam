package com.example.myapplication.api;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import com.example.myapplication.models.NewsResponse;

public interface NewsApiService {
    @GET("v2/top-headlines")
    Call<NewsResponse> getBreakingNews(
            @Query("country") String country,
            @Query("category") String category,
            @Query("apiKey") String apiKey,
            @Query("pageSize") int pageSize
    );
}
