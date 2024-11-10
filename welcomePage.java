package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.myapplication.databinding.ActivityWelcomePageBinding;

public class welcomePage extends AppCompatActivity {
    private ActivityWelcomePageBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);

        binding = ActivityWelcomePageBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return WindowInsetsCompat.CONSUMED;
        });

        Glide.with(welcomePage.this)
                .load(R.drawable.welcome)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)  // Caching strategy
                .into(binding.logo);

        binding.loginButton.setOnClickListener(v -> {
            Intent i = new Intent(welcomePage.this, Login.class);
            startActivity(i);
            finish();
        });

        binding.signuptext.setOnClickListener(v -> {
            Intent i = new Intent(welcomePage.this, signUp.class);
            startActivity(i);
            finish();
        });


    }
    protected void onStop() {
        super.onStop();
        Glide.get(this).clearMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        new Thread(() -> Glide.get(this).clearDiskCache()).start();
    }
}
