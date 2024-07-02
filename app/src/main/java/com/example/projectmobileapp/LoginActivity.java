package com.example.projectmobileapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity {
    Button btnLogin, btnGoRegister;
    EditText etUsername, etPwd;
    TextView tvForgotPwd;
    DatabaseHelper databaseHelper;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    boolean isLoggedIn = false;
    String currentUsername;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        databaseHelper = new DatabaseHelper(this);

        sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        etUsername = findViewById(R.id.etUsername);
        etPwd = findViewById(R.id.etPassword);
        tvForgotPwd = findViewById(R.id.tv_forgotPassword);

        checkLoginStatus();

        btnLogin = findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(v -> {
            boolean isLoggedIn = databaseHelper.checkUser(etUsername.getText().toString(), etPwd.getText().toString());
            if (isLoggedIn) {
                saveLoginStatus(true);
                saveCurrentUsername(etUsername.getText().toString());
                this.isLoggedIn = true;
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
            else {
                Toast.makeText(LoginActivity.this, "Login Failed!", Toast.LENGTH_LONG).show();
            }
        });

        btnGoRegister = findViewById(R.id.btn_gotoRegister);
        btnGoRegister.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.login), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void saveCurrentUsername(String username) {
        editor.putString("currentUsername", username);
        editor.apply();
    }

    private void saveLoginStatus(boolean isLoggedIn) {
        editor.putBoolean("isLoggedIn", isLoggedIn);
        editor.apply();
    }

    private void checkLoginStatus() {
        isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);
        if (isLoggedIn) {
            currentUsername = sharedPreferences.getString("currentUsername", "");
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
