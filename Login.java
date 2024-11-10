package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.LiveData;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.myapplication.data.AppDatabase;
import com.example.myapplication.data.UserDao;
import com.example.myapplication.data.UserDatabase;
import com.example.myapplication.models.User;
import com.example.myapplication.databinding.LoginActivityBinding;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Login extends AppCompatActivity {
    private ExecutorService executorService;
    private LoginActivityBinding binding;
    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = LoginActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        executorService = Executors.newSingleThreadExecutor();

        userDao = UserDatabase.getInstance(this).userDao();

        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

//        Glide.with(Login.this)
//                .asGif()
//                .load(R.raw.login_logo)
//                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
//                .into(binding.imageView);

        binding.loginbtn.setOnClickListener(v -> {
            executorService.execute(() -> {
                if (validateForm()) {
                    String email = binding.userEmail.getText().toString().trim();
                    String password = binding.userPassword.getText().toString().trim();

                    // Query the database for the user synchronously
                    User user = userDao.getUserByEmailSync(email);

                    // Check if the user exists and the password matches
                    if (user != null && user.getPassword().equals(password)) {
                        runOnUiThread(() -> {
                            Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Login.this, BrowsePage.class);
                            startActivity(intent);
                            finish();
                        });
                    } else {
                        runOnUiThread(() -> {
                            Toast.makeText(Login.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                        });
                    }
                }
            });
        });

        binding.signupbtn.setOnClickListener(v -> {
            Intent i = new Intent(Login.this, signUp.class);
            startActivity(i);
        });
    }

    private boolean validateForm() {
        String email = binding.userEmail.getText().toString().trim();
        String password = binding.userPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            runOnUiThread(() -> binding.userEmail.setError("Email is required"));
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            runOnUiThread(() -> binding.userEmail.setError("Invalid email format"));
            return false;
        }
        if (TextUtils.isEmpty(password)) {
            runOnUiThread(() -> binding.userPassword.setError("Password is required"));
            return false;
        } else if (password.length() < 6) {
            runOnUiThread(() -> binding.userPassword.setError("Password must be at least 6 characters long"));
            return false;
        }
        return true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        Glide.get(this).clearMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (executorService != null) {
            executorService.shutdownNow(); // Ensure all tasks are canceled and resources are freed
        }
        // Clear Glide's disk cache in a separate thread to avoid blocking the UI thread
        new Thread(() -> Glide.get(this).clearDiskCache()).start();
    }

}