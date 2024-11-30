package com.example.bth6_bai3;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    public static class Key {
        public static final String ACTION_PLUS_NUMBER = "com.example.broadcastcalculator.PLUS_NUMBER";
        public static final String NUMBER_A = "number_a";
        public static final String NUMBER_B = "number_b";
    }

    private EditText editText1, editText2;
    private Button btn;
    private CalculatorReceiver receiver;

    @SuppressLint("UnspecifiedRegisterReceiverFlag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ánh xạ các thành phần giao diện
        editText1 = findViewById(R.id.editText1);
        editText2 = findViewById(R.id.editText2);
        btn = findViewById(R.id.btn);

        // Thiết lập hành động khi nhấn nút
        btn.setOnClickListener(this::onClick);

        // Đăng ký BroadcastReceiver
        receiver = new CalculatorReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Key.ACTION_PLUS_NUMBER);
        registerReceiver(receiver, filter);
    }

    public void onClick(View v) {
        try {
            // Lấy giá trị từ các ô nhập liệu
            int a = Integer.parseInt(editText1.getText().toString());
            int b = Integer.parseInt(editText2.getText().toString());

            // Gửi Intent với dữ liệu
            Intent intent = new Intent(Key.ACTION_PLUS_NUMBER);
            intent.putExtra(Key.NUMBER_A, a);
            intent.putExtra(Key.NUMBER_B, b);
            sendBroadcast(intent);

        } catch (NumberFormatException e) {
            // Xử lý khi nhập liệu không hợp lệ
            Toast.makeText(this, "Vui lòng nhập số hợp lệ!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Hủy đăng ký BroadcastReceiver khi không cần thiết
        unregisterReceiver(receiver);
    }
}
