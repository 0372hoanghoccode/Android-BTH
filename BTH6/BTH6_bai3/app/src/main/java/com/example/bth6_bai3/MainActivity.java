package com.example.bth6_bai3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText editTextA, editTextB;
    private Button buttonPlus, buttonEquals, buttonClear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextA = findViewById(R.id.editTextA);
        editTextB = findViewById(R.id.editTextB);
        buttonPlus = findViewById(R.id.buttonPlus);
        buttonEquals = findViewById(R.id.buttonEquals);
        buttonClear = findViewById(R.id.buttonClear);

        // Implement button functionality
        buttonPlus.setOnClickListener(v -> performAddition());
        buttonEquals.setOnClickListener(v -> showResult());
        buttonClear.setOnClickListener(v -> clearInputs());

        // Number and operator buttons
        setButtonListeners();
    }

    private void performAddition() {
        int numberA = Integer.parseInt(editTextA.getText().toString());
        int numberB = Integer.parseInt(editTextB.getText().toString());

        Intent intent = new Intent(MainActivity.Key.ACTION_PLUS_NUMBER);
        intent.putExtra(MainActivity.Key.NUMBER_A, numberA);
        intent.putExtra(MainActivity.Key.NUMBER_B, numberB);

        sendBroadcast(intent);
    }

    private void showResult() {
        // Logic to calculate and display result can go here
    }

    private void clearInputs() {
        editTextA.setText("");
        editTextB.setText("");
    }

    private void setButtonListeners() {
        // Define onClick listeners for each button to handle numeric inputs
        // Example:
        findViewById(R.id.button0).setOnClickListener(v -> appendToCurrentInput("0"));
        findViewById(R.id.button1).setOnClickListener(v -> appendToCurrentInput("1"));
        findViewById(R.id.button2).setOnClickListener(v -> appendToCurrentInput("2"));
        // Repeat for other number buttons...
    }

    private void appendToCurrentInput(String text) {
        if (editTextA.isFocused()) {
            editTextA.append(text);
        } else if (editTextB.isFocused()) {
            editTextB.append(text);
        }
    }

    public static class Key {
        public static final String ACTION_PLUS_NUMBER = "com.example.bth6_bai3.ACTION_PLUS_NUMBER";
        public static final String NUMBER_A = "com.example.bth6_bai3.NUMBER_A";
        public static final String NUMBER_B = "com.example.bth6_bai3.NUMBER_B";
    }
}
