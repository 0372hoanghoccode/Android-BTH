package com.example.bth5;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class Bai1 extends AppCompatActivity {
    private EditText edtName, edtEmail, edtProject;
    private Button btnViewContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bai1);

        edtName = findViewById(R.id.edtName);
        edtEmail = findViewById(R.id.edtEmail);
        edtProject = findViewById(R.id.edtProject);
        btnViewContact = findViewById(R.id.btnViewContact);

        btnViewContact.setOnClickListener(v -> {
            String name = edtName.getText().toString();
            String email = edtEmail.getText().toString();
            String project = edtProject.getText().toString();

            Intent intent = new Intent(Bai1.this, ContactInfoActivity.class);

            intent.putExtra("NAME", name);
            intent.putExtra("EMAIL", email);
            intent.putExtra("PROJECT", project);

            startActivity(intent);
        });
    }
}
