package com.example.bth3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button buttonGoToBai1;
    private Button buttonGoToBai2;
    private Button buttonGoToBai3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonGoToBai1 = findViewById(R.id.buttonGoToBai1);
        buttonGoToBai1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Bai1Activity.class);
                startActivity(intent);
            }
        });

        buttonGoToBai3 = findViewById(R.id.buttonGoToBai3);
        buttonGoToBai3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Bai3Activity.class);
                startActivity(intent);
            }
        });

        buttonGoToBai2 = findViewById(R.id.buttonGoToBai2);
        buttonGoToBai2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Bai2Activity.class);
                startActivity(intent);
            }
        });
    }
}
