package com.example.bth3;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class Bai2Activity extends AppCompatActivity {
    private EditText editTextInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bai2);

        editTextInput = findViewById(R.id.editTextInput);

        editTextInput.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    String inputText = editTextInput.getText().toString();
                    if (!inputText.isEmpty()) {
                        Toast.makeText(Bai2Activity.this,
                                  inputText,
                                Toast.LENGTH_SHORT).show();
                        editTextInput.setText(inputText);
                        editTextInput.setSelection(inputText.length());
                    } else {
                        Toast.makeText(Bai2Activity.this,
                                "Vui lòng nhập nội dung",
                                Toast.LENGTH_SHORT).show();
                    }
                    return true;
                }
                return false;
            }
        });
    }
}