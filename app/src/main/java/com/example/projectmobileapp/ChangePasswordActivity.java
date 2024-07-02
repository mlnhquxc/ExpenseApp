package com.example.projectmobileapp;

import android.content.SharedPreferences;
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

public class ChangePasswordActivity extends AppCompatActivity {
    EditText etOldPwd, etNewPwd, etNewRePwd;
    Button btn_save, btn_cancel;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_change_password);
        etOldPwd = findViewById(R.id.etOldPassword);
        etNewPwd = findViewById(R.id.etNewPassword);
        etNewRePwd = findViewById(R.id.etReNewPassword);
        btn_save = findViewById(R.id.btn_saveChange);
        btn_cancel = findViewById(R.id.btn_cancel);

        databaseHelper = new DatabaseHelper(this);

        btn_save.setOnClickListener(v -> {
            String oldPwd = etOldPwd.getText().toString();
            String newPwd = etNewPwd.getText().toString();
            String reNewPwd = etNewRePwd.getText().toString();

            if (oldPwd.isEmpty() || newPwd.isEmpty() || reNewPwd.isEmpty()) {
                Toast.makeText(ChangePasswordActivity.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_LONG).show();
            } else if (!newPwd.equals(reNewPwd)) {
                Toast.makeText(ChangePasswordActivity.this, "Mật khẩu và mật khẩu nhập lại không giống nhau!", Toast.LENGTH_LONG).show();
            } else {
                String currentUsername = getCurrentUsername();
                if (currentUsername == null) {
                    Toast.makeText(ChangePasswordActivity.this, "Người dùng không hợp lệ", Toast.LENGTH_LONG).show();
                    return;
                }
                boolean isUserValid = databaseHelper.checkUser(currentUsername, oldPwd);
                if (!isUserValid) {
                    Toast.makeText(ChangePasswordActivity.this, "Mật khẩu cũ không đúng", Toast.LENGTH_LONG).show();
                    return;
                }
                boolean updateSuccess = databaseHelper.updatePassword(currentUsername, newPwd);
                if (updateSuccess) {
                    Toast.makeText(ChangePasswordActivity.this, "Thay đổi mật khẩu thành công!", Toast.LENGTH_LONG).show();
                    finish(); // Hoặc chuyển sang Activity khác nếu cần
                } else {
                    Toast.makeText(ChangePasswordActivity.this, "Thay đổi mật khẩu thất bại!", Toast.LENGTH_LONG).show();
                }
            }
        });

        btn_cancel.setOnClickListener(v -> finish());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.change_password), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private String getCurrentUsername() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        return sharedPreferences.getString("currentUsername", "");
    }

}
