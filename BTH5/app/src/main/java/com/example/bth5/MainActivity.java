package com.example.bth5;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Button btnBai1, btnBai2, btnBai3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnBai1 = findViewById(R.id.btnBai1);
        btnBai2 = findViewById(R.id.btnBai2);
        btnBai3 = findViewById(R.id.btnBai3);

        btnBai1.setOnClickListener(v -> {
            Intent intentBai1 = new Intent(MainActivity.this, Bai1.class);
            startActivity(intentBai1);
        });

        btnBai2.setOnClickListener(v -> {
            Intent intentBai2 = new Intent(MainActivity.this, Bai2.class);
            startActivity(intentBai2);
        });

        btnBai3.setOnClickListener(v -> {
            Intent intentBai3 = new Intent(MainActivity.this, Bai3.class);
            startActivity(intentBai3);
        });
    }
}
