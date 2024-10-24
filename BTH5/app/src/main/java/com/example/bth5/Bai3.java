package com.example.bth5;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class Bai3 extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CALL_PHONE = 1;
    private static final int PERMISSION_REQUEST_SEND_SMS = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bai3);

        // gọi điện
        Button btnDial = findViewById(R.id.btnDial);
        btnDial.setOnClickListener(v -> {
            // dùng ACTION_DIAL không cần quyền
            Intent dialIntent = new Intent(Intent.ACTION_DIAL);
            dialIntent.setData(Uri.parse("tel:0123456789"));
            startActivity(dialIntent);
        });

        // xem danh bạ
        Button btnViewContacts = findViewById(R.id.btnViewContacts);
        btnViewContacts.setOnClickListener(v -> {
            Intent contactsIntent = new Intent(Intent.ACTION_VIEW);
            contactsIntent.setData(Uri.parse("content://contacts/people/"));
            startActivity(contactsIntent);
        });

        // gửi SMS
        Button btnSendSMS = findViewById(R.id.btnSendSMS);
        btnSendSMS.setOnClickListener(v -> {
            // dùng ACTION_SENDTO thay vì ACTION_SEND
            try {
                Intent smsIntent = new Intent(Intent.ACTION_SENDTO);
                smsIntent.setData(Uri.parse("smsto:0123456789"));
                smsIntent.putExtra("sms_body", "Xin chào!");
                startActivity(smsIntent);
            } catch (Exception e) {
                Toast.makeText(this, "Không thể mở ứng dụng SMS", Toast.LENGTH_SHORT).show();
            }
        });

        // xem ảnh
        Button btnViewImage = findViewById(R.id.btnViewImage);
        btnViewImage.setOnClickListener(v -> {
            Intent imageIntent = new Intent(Intent.ACTION_VIEW);
            imageIntent.setType("image/pictures/*");
            startActivity(Intent.createChooser(imageIntent, "Chọn ứng dụng xem ảnh"));
        });

        // nghe nhạc
        Button btnPlayMusic = findViewById(R.id.btnPlayMusic);
        btnPlayMusic.setOnClickListener(v -> {
            Intent musicIntent = new Intent(Intent.ACTION_VIEW);
            musicIntent.setType("audio/*");
            startActivity(Intent.createChooser(musicIntent, "Chọn ứng dụng nghe nhạc"));
        });

        // mở Maps
        Button btnOpenMaps = findViewById(R.id.btnOpenMaps);
        btnOpenMaps.setOnClickListener(v -> {
            Uri gmmIntentUri = Uri.parse("http://maps.google.com/maps?saddr=Hanoi&daddr=Ho+Chi+Minh+City");
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);

            try {
                startActivity(mapIntent);
            } catch (Exception e) {
                Toast.makeText(this, "Không thể mở Google Maps", Toast.LENGTH_SHORT).show();
            }
        });
    }
}