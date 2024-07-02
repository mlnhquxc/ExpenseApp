package com.example.projectmobileapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RegisterActivity extends AppCompatActivity {
    Button btnRegister, btnGoLogin;
    EditText etUsername, etEmail, etPwd, etRePwd;
    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        databaseHelper = new DatabaseHelper(this);
        etUsername = findViewById(R.id.etUsername);
        etEmail = findViewById(R.id.etEmail);
        etPwd = findViewById(R.id.etPassword);
        etRePwd = findViewById(R.id.etRePassword);
        btnRegister = findViewById(R.id.btn_register);

        btnRegister.setOnClickListener(v -> {
            String username, pwd,rePwd, email;
            username = etUsername.getText().toString();
            pwd = etPwd.getText().toString();
            rePwd = etRePwd.getText().toString();
            email = etEmail.getText().toString();
            if (username.equals("") || email.equals("") || pwd.equals("") || rePwd.equals("")) {
                Toast.makeText(RegisterActivity.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_LONG).show();
            }
            else {
                if (pwd.equals(rePwd)){
                    if(databaseHelper.checkUsername(username)){
                        Toast.makeText(RegisterActivity.this, "Người dùng đã tồn tại", Toast.LENGTH_LONG).show();
                        return;
                    }
                    boolean registeredSuccess = databaseHelper.addUser(username, pwd, email, 0);
                    if (registeredSuccess) {
                        Toast.makeText(RegisterActivity.this, "Tạo tài khoản thành công!", Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(RegisterActivity.this, "Tạo tài khoản thất bại!", Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    Toast.makeText(RegisterActivity.this, "Mật khẩu và mật khẩu nhập lại không giống nhau!", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnGoLogin = findViewById(R.id.btn_gotoLogin);
        btnGoLogin.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.register), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
