package com.example.projectmobileapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.projectmobileapp.fragment.HomeFragment;
import com.example.projectmobileapp.fragment.History;



import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

public class MainActivity extends AppCompatActivity {
    LinearLayout btnHome, btnHistory, btnNotification, btnAccount;
    ExtendedFloatingActionButton btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        btnHome = findViewById(R.id.btn_home);
        btnHistory = findViewById(R.id.btn_history);
        btnNotification = findViewById(R.id.btn_notification);
        btnAccount = findViewById(R.id.btn_account);
        btnAdd = findViewById(R.id.btn_add);

        if (savedInstanceState == null) {
            replaceFragment(new HomeFragment());
        }

        View.OnClickListener onClickListener = v -> {
            if (v.getId() == R.id.btn_home) {
                replaceFragment(new HomeFragment());
            } else if (v.getId() == R.id.btn_history) {
                replaceFragment(new History());
            } else if (v.getId() == R.id.btn_notification) {
                replaceFragment(new NotificationFragment());
            } else if (v.getId() == R.id.btn_account) {
                replaceFragment(new AccountFragment());
            } else if (v.getId() == R.id.btn_add) {
                Intent intent = new Intent(this, AddTransaction.class);
                startActivity(intent);
            }
        };

        btnHome.setOnClickListener(onClickListener);
        btnHistory.setOnClickListener(onClickListener);
        btnNotification.setOnClickListener(onClickListener);
        btnAccount.setOnClickListener(onClickListener);
        btnAdd.setOnClickListener(onClickListener);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }
}