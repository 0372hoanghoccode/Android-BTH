package com.example.bth5;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Bai2 extends AppCompatActivity {
    private Button btnOpenWeb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bai2);

        btnOpenWeb = findViewById(R.id.btnOpenWeb);

        btnOpenWeb.setOnClickListener(v -> {
            try {
                String url = "https://www.google.com";
                Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                browserIntent.setData(Uri.parse(url));
                startActivity(browserIntent);
            } catch (Exception e) {
                Toast.makeText(this, "Không thể mở trình duyệt!", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        });
    }
}