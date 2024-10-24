package com.example.bth3;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class Bai1Activity extends AppCompatActivity {

    private Button buttonShowToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bai1);

        buttonShowToast = findViewById(R.id.buttonShowToast);

        buttonShowToast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Bai1Activity.this, "Sự kiện click Button", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
