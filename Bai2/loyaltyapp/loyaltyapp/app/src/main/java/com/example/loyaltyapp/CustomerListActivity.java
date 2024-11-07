package com.example.loyaltyapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class CustomerListActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 1;
    private ListView listView;
    private Button btnBack;
    private ArrayList<Customer> customers;  // Sử dụng ArrayList chứa đối tượng Customer

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_list);

        listView = findViewById(R.id.listViewCustomers);
        customers = new ArrayList<>();
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v ->{
            Intent intent = new Intent(CustomerListActivity.this, MenuActivity.class);
            startActivity(intent);
        });

        // Kiểm tra quyền truy cập bộ nhớ ngoài
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // Với Android 11 trở lên, kiểm tra quyền MANAGE_EXTERNAL_STORAGE
            if (!Environment.isExternalStorageManager()) {
                requestManageExternalStoragePermission();
            } else {
                readCustomerDataFromXML();
            }
        } else {
            // Với Android 10 và thấp hơn, sử dụng quyền READ_EXTERNAL_STORAGE
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
            } else {
                readCustomerDataFromXML();
            }
        }
    }

    // Phương thức yêu cầu quyền MANAGE_EXTERNAL_STORAGE cho Android 11+
    private void requestManageExternalStoragePermission() {
        try {
            Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
            startActivity(intent);
        } catch (Exception e) {
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
            startActivity(intent);
        }
    }

    // Phương thức đọc file XML và cập nhật danh sách khách hàng
    private void readCustomerDataFromXML() {
        try {
            // Đường dẫn đến file XML
            File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
            File file = new File(path, "customers.xml");

            if (file.exists()) {
                FileInputStream inputStream = new FileInputStream(file);
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document doc = builder.parse(inputStream);
                doc.getDocumentElement().normalize();

                // Lấy danh sách các thẻ customer từ file XML
                NodeList customerNodes = doc.getElementsByTagName("customer");
                for (int i = 0; i < customerNodes.getLength(); i++) {
                    Element customerElement = (Element) customerNodes.item(i);
                    String name = customerElement.getElementsByTagName("name").item(0).getTextContent();
                    String points = customerElement.getElementsByTagName("points").item(0).getTextContent();
                    String phone = customerElement.getElementsByTagName("phone").item(0).getTextContent();
                    String createdDate = customerElement.getElementsByTagName("createdDate").item(0).getTextContent(); // Lấy giá trị ngày tạo

                    // Thêm đối tượng Customer vào danh sách
                    customers.add(new Customer(name, phone, Integer.parseInt(points), createdDate)); // Thêm createdDate
                }

                // Hiển thị danh sách khách hàng
                updateListView();

            } else {
                Toast.makeText(this, "File không tồn tại tại: " + file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            Log.e("CustomerListActivity", "Lỗi khi đọc file XML", e);
            Toast.makeText(this, "Đọc dữ liệu thất bại: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }



    // Cập nhật ListView với thông tin khách hàng
    private void updateListView() {
        ArrayList<String> customerInfoList = new ArrayList<>();
        for (Customer customer : customers) {
            customerInfoList.add(customer.getName() + " - " + customer.getPoints() + " điểm - SĐT: " + customer.getPhone() + " - Ngày tạo: " + customer.getCreatedDate());
        }

        // Sử dụng ArrayAdapter để hiển thị danh sách khách hàng
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, customerInfoList);
        listView.setAdapter(adapter);
    }



    // Xử lý kết quả yêu cầu quyền truy cập bộ nhớ ngoài
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                readCustomerDataFromXML();
            } else {
                Toast.makeText(this, "Quyền truy cập bộ nhớ ngoài bị từ chối", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && Environment.isExternalStorageManager()) {
            readCustomerDataFromXML();
        }
    }
}