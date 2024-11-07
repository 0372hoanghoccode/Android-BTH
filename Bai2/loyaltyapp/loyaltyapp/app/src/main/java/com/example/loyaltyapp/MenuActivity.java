package com.example.loyaltyapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MenuActivity extends AppCompatActivity {
    Button btnCustomerList, btnAddPoints, btnUsePoints, btnChangeAccount;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnCustomerList = findViewById(R.id.btnCustomerList);
        btnAddPoints = findViewById(R.id.btnAddPoints);
        btnUsePoints = findViewById(R.id.btnUsePoints);

        btnChangeAccount = findViewById(R.id.btnChangeAccount);



        btnChangeAccount.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, ChangePasswordActivity.class);
            startActivity(intent);
        });


        btnCustomerList.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, CustomerListActivity.class);
            startActivity(intent);
        });

        btnAddPoints.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, AddPointsActivity.class);
            startActivity(intent);
        });

        btnUsePoints.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, UsePointsActivity.class);
            startActivity(intent);
        });


    }
}
