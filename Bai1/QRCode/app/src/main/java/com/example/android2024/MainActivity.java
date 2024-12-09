package com.example.android2024;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.journeyapps.barcodescanner.CaptureActivity;
import com.journeyapps.barcodescanner.ScanOptions;
import com.journeyapps.barcodescanner.ScanContract;

import androidx.activity.result.contract.ActivityResultContracts;
import android.app.Activity;

public class MainActivity extends AppCompatActivity {
    private ActivityResultLauncher<Intent> contactPickerLauncher;
    private Uri selectedContactUri;

    private final ActivityResultLauncher<ScanOptions> qrScanLauncher = registerForActivityResult(
            new ScanContract(),
            result -> {
                if (result.getContents() != null) {
                    String qrContent = result.getContents();
                    handleQRData(qrContent);
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnPickContact = findViewById(R.id.btn_pick_contact);
        Button btnViewContact = findViewById(R.id.btn_view_contact);
        Button btnScanQR = findViewById(R.id.btn_scan_qr);


        contactPickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            if (data != null && data.getData() != null) {
                                selectedContactUri = data.getData();

                                // Hien nut xem sau khi chon
                                btnViewContact.setEnabled(true);
                            }
                        }
                    }
                }
                );


        btnPickContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Kiem tra quyen
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_CONTACTS)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_CONTACTS}, 100);
                } else {
                    openContacts();
                }
            }
        });

        btnViewContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedContactUri != null) {
                    // Use Intent to view the selected contact
                    Intent intent = new Intent(Intent.ACTION_VIEW, selectedContactUri);
                    startActivity(intent);
                }
            }
        });

        btnScanQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanQRCode();
            }
        });
    }

    private void openContacts() {
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        contactPickerLauncher.launch(intent);
    }

    private void scanQRCode() {
        ScanOptions options = new ScanOptions();
        options.setPrompt("Quét mã QR");
        options.setOrientationLocked(false);
        options.setCaptureActivity(CaptureActivity.class);
        qrScanLauncher.launch(options);
    }

    private void handleQRData(String data) {
        if (data.startsWith("http://") || data.startsWith("https://")) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(data));
            startActivity(browserIntent);
        } else if (data.startsWith("mailto:")) {
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse(data));
            startActivity(emailIntent);
        } else if (data.startsWith("smsto:")) {
            Intent smsIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse(data));
            startActivity(smsIntent);
        }
    }
}
