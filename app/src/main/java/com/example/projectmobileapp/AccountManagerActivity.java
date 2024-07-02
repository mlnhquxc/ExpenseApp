package com.example.projectmobileapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AccountManagerActivity extends AppCompatActivity {
    TextView tvChangePwd, tvLogout, tvDeleteAccount, tvUsername, tvEmail;
    SharedPreferences sharedPreferences;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_account_manager);
        tvChangePwd = findViewById(R.id.btn_change_password);
        tvLogout = findViewById(R.id.btn_logout);
        tvDeleteAccount = findViewById(R.id.btn_delete_account);
        tvUsername = findViewById(R.id.tvUsernameLoggedIn);
        tvEmail = findViewById(R.id.tvEmailLoggedIn);
        databaseHelper = new DatabaseHelper(this);

        sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);

        String username = sharedPreferences.getString("currentUsername", "");
        tvUsername.setText(username);

        String email = databaseHelper.getUserEmail(username);
        tvEmail.setText(email);

        tvChangePwd.setOnClickListener(v -> {
            Intent intent = new Intent(AccountManagerActivity.this, ChangePasswordActivity.class);
            startActivity(intent);
        });

        tvLogout.setOnClickListener(v -> {
            SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove("isLoggedIn");
            editor.apply();

            Intent intent = new Intent(AccountManagerActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });





        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.accountManager), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
