package com.example.bth4;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
// hello
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this); // Đặt chế độ toàn màn hình
        setContentView(R.layout.activity_main); // Đặt layout từ tệp XML

        // Xử lý để padding cho hệ thống system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Đăng ký context menu cho ImageView
        ImageView im = (ImageView) findViewById(R.id.myImageView);
        registerForContextMenu(im);

        // Tìm Button và xử lý Popup Menu
        View btnPopupMenu = findViewById(R.id.btn);

        btnPopupMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Tạo PopupMenu
                PopupMenu popupMenu = new PopupMenu(MainActivity.this, btnPopupMenu);

                // Nạp menu từ tệp XML
                popupMenu.getMenuInflater().inflate(R.menu.popupmenu, popupMenu.getMenu());

                // Xử lý khi chọn một mục trong PopupMenu
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        // Hiển thị thông báo khi chọn menu
                        Toast.makeText(MainActivity.this, "Bạn vừa chọn popup menu", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });

                // Hiển thị PopupMenu
                popupMenu.show();
            }
        });
    }

    // Tạo Options Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return true;
    }

    // Xử lý sự kiện khi một tùy chọn trong Options Menu được chọn
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
        return true;
    }

    // Tạo Context Menu
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.context_menu, menu);
    }

    // Xử lý khi chọn một mục trong Context Menu
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
        return true;
    }
}
