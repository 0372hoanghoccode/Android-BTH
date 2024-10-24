package com.example.bth3;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class Bai3Activity extends AppCompatActivity {
    private View fullScreenView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bai3);

        fullScreenView = findViewById(R.id.fullScreenView);

        fullScreenView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Toast.makeText(Bai3Activity.this, "Bạn đã chạm vào màn hình!", Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }
        });
    }
}
