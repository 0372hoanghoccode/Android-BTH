package com.example.loyaltyapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    private EditText edtTenDangNhap, edtMatKhau;
    private Button btnDangNhap;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtTenDangNhap = findViewById(R.id.edtTenDangNhap);
        edtMatKhau = findViewById(R.id.edtMatKhau);
        btnDangNhap = findViewById(R.id.btnDangNhap);

        // Lấy thông tin tài khoản đã lưu
        sharedPreferences = getSharedPreferences("UserCredentials", MODE_PRIVATE);
        String savedPassword = sharedPreferences.getString("password", "123");  // Mật khẩu mặc định

        btnDangNhap.setOnClickListener(v -> {
            String tenDangNhap = edtTenDangNhap.getText().toString().trim();
            String matKhau = edtMatKhau.getText().toString().trim();

            if (TextUtils.isEmpty(tenDangNhap) || TextUtils.isEmpty(matKhau)) {
                Toast.makeText(LoginActivity.this, "Vui lòng nhập tên đăng nhập và mật khẩu", Toast.LENGTH_SHORT).show();
            } else if (tenDangNhap.equals("admin") && matKhau.equals(savedPassword)) {
                Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                startActivity(intent);
                finish();  // Đóng màn hình đăng nhập
            } else {
                Toast.makeText(LoginActivity.this, "Sai tên đăng nhập hoặc mật khẩu", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
