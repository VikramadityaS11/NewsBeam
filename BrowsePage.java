package com.example.myapplication;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myapplication.api.NewsApiService;
import com.example.myapplication.api.RetroFitClient;
import com.example.myapplication.databinding.ActivityBrowsePageBinding;
import com.example.myapplication.models.NewsResponse;
import com.example.myapplication.models.Article;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BrowsePage extends AppCompatActivity {
    private ActivityBrowsePageBinding binding;
    private ArticleAdapter articleAdapter;
    private List<Article> articleList;
    private BrowseViewModel browseViewModel;

    long currentTime = System.currentTimeMillis();
    private long lastFetchTime = 0;
    private final long CACHE_DURATION = 3600000;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBrowsePageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        articleList = new ArrayList<>();

        // Pass `this` as the context to ArticleAdapter
        articleAdapter = new ArticleAdapter(articleList, this);

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(articleAdapter);

        browseViewModel = new ViewModelProvider(this).get(BrowseViewModel.class);

        browseViewModel.getArticles().observe(this, articles -> {
            articleAdapter.updateArticles(articles); // Update the RecyclerView
        });

        browseViewModel.fetchBreakingNews();
    }
}

