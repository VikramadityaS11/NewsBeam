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

import com.example.myapplication.models.User;
import com.example.myapplication.data.UserDatabase;
import com.example.myapplication.databinding.ActivitySignUpBinding;

public class signUp extends AppCompatActivity {
    private ActivitySignUpBinding binding;
    private UserDatabase userDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        userDatabase = UserDatabase.getInstance(this);

        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return WindowInsetsCompat.CONSUMED;
        });

        binding.signUpButton.setOnClickListener(v -> {
            if (validateForm()) {
                saveUserToDatabase();
            }
        });
    }

    private boolean validateForm() {
        if (!isValidName(binding.firstName.getText().toString().trim(), "First Name")) return false;
        if (!isValidName(binding.lastName.getText().toString().trim(), "Last Name")) return false;
        if (!isValidEmail(binding.email.getText().toString().trim())) return false;
        return isValidPassword(binding.password.getText().toString().trim());
    }

    private boolean isValidName(String name, String fieldName) {
        if (TextUtils.isEmpty(name)) {
            setError(binding.firstName, fieldName + " is required");
            return false;
        }
        return true;
    }

    private boolean isValidEmail(String email) {
        if (TextUtils.isEmpty(email)) {
            setError(binding.email, "Email is required");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            setError(binding.email, "Email is invalid");
            return false;
        }
        return true;
    }

    private boolean isValidPassword(String password) {
        if (TextUtils.isEmpty(password)) {
            setError(binding.password, "Password is required");
            return false;
        } else if (password.length() < 6) {
            setError(binding.password, "Password must be at least 6 characters");
            return false;
        } else if (!password.matches(".*\\d.*")) {
            setError(binding.password, "Password must contain at least one number");
            return false;
        }
        return true;
    }

    private void setError(View view, String message) {
        if (view instanceof androidx.appcompat.widget.AppCompatEditText) {
            ((androidx.appcompat.widget.AppCompatEditText) view).setError(message);
        }
    }

    private void saveUserToDatabase() {
        String firstName = binding.firstName.getText().toString().trim();
        String lastName = binding.lastName.getText().toString().trim();
        String email = binding.email.getText().toString().trim();
        String password = binding.password.getText().toString().trim();

        User user = new User(firstName, lastName, email, password);

        new Thread(() -> {
            userDatabase.userDao().insertUser(user);

            // After saving the user, navigate to the Login activity
            runOnUiThread(() -> {
                Toast.makeText(signUp.this, "Sign up successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(signUp.this, Login.class);
                startActivity(intent);
                finish();  // Optional: Close the SignUp activity
            });
        }).start();
    }
}
