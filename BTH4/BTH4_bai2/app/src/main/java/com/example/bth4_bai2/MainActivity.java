package com.example.bth4_bai2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnXemThoiGian = findViewById(R.id.btnXemThoiGian);

        btnXemThoiGian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get current date and time
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy h:mm:ss a", Locale.getDefault());
                String formattedDateTime = dateFormat.format(calendar.getTime());

                // Show current time in a Toast
                Toast.makeText(MainActivity.this, "Thời gian hiện hành " + formattedDateTime, Toast.LENGTH_LONG).show();
            }
        });
    }
}
