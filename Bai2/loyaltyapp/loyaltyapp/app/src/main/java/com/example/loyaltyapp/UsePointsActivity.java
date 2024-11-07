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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
import java.io.FileOutputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class UsePointsActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 1;
    private ArrayList<Customer> customersList;
    private EditText edtPhone, edtPoints;
    private Button btnSearch, btnUsePoints, btnBack;
    private TextView txtCustomerInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_use_points);

        edtPhone = findViewById(R.id.edtPhone);
        edtPoints = findViewById(R.id.edtPoints);
        btnSearch = findViewById(R.id.btnSearch);
        btnUsePoints = findViewById(R.id.btnUsePoints);
        txtCustomerInfo = findViewById(R.id.txtCustomerInfo);

        btnSearch.setOnClickListener(v -> searchCustomer());
        btnUsePoints.setOnClickListener(v -> usePointsToCustomer());
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(UsePointsActivity.this, MenuActivity.class);
            startActivity(intent);
        });

        // Kiểm tra và yêu cầu quyền truy cập bộ nhớ
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (!Environment.isExternalStorageManager()) {
                requestManageExternalStoragePermission();
            } else {
                loadCustomerData();
            }
        } else {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
            } else {
                loadCustomerData();
            }
        }
    }

    private void searchCustomer() {
        String phone = edtPhone.getText().toString().trim();
        if (phone.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập số điện thoại", Toast.LENGTH_SHORT).show();
            return;
        }

        for (Customer customer : customersList) {
            if (customer.getPhone().equals(phone)) {
                txtCustomerInfo.setText("Tên: " + customer.getName() + "\nĐiểm: " + customer.getPoints());
                return;
            }
        }

        txtCustomerInfo.setText("Không tìm thấy khách hàng với số điện thoại này");
    }

    private void usePointsToCustomer() {
        String phone = edtPhone.getText().toString().trim();
        String pointsStr = edtPoints.getText().toString().trim();

        if (phone.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập số điện thoại", Toast.LENGTH_SHORT).show();
            return;
        }

        if (pointsStr.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập số điểm", Toast.LENGTH_SHORT).show();
            return;
        }

        int pointsToUse = Integer.parseInt(pointsStr);
        for (Customer customer : customersList) {
            if (customer.getPhone().equals(phone)) {
                int currentPoints = customer.getPoints();
                if (currentPoints < pointsToUse) {
                    Toast.makeText(this, "Không đủ điểm để sử dụng!", Toast.LENGTH_SHORT).show();
                    return;
                }
                customer.setPoints(currentPoints - pointsToUse);
                txtCustomerInfo.setText("Tên: " + customer.getName() + "\nĐiểm mới: " + customer.getPoints());
                updateCustomerDataInXML();
                Toast.makeText(this, "Cập nhật điểm thành công!", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        Toast.makeText(this, "Không tìm thấy khách hàng", Toast.LENGTH_SHORT).show();
    }

    private void loadCustomerData() {
        customersList = new ArrayList<>();
        try {
            File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
            File file = new File(path, "customers.xml");

            if (file.exists()) {
                FileInputStream inputStream = new FileInputStream(file);
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document doc = builder.parse(inputStream);
                doc.getDocumentElement().normalize();

                NodeList customerNodes = doc.getElementsByTagName("customer");
                for (int i = 0; i < customerNodes.getLength(); i++) {
                    Element customerElement = (Element) customerNodes.item(i);
                    String name = customerElement.getElementsByTagName("name").item(0).getTextContent();
                    String points = customerElement.getElementsByTagName("points").item(0).getTextContent();
                    String phone = customerElement.getElementsByTagName("phone").item(0).getTextContent();
                    String createdDate = customerElement.getElementsByTagName("createdDate").item(0).getTextContent(); // Lấy createdDate

                    // Thêm khách hàng với thông tin ngày tạo
                    customersList.add(new Customer(name, phone, Integer.parseInt(points), createdDate));

                }

            } else {
                Toast.makeText(this, "File không tồn tại tại: " + file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            Log.e("UsePointsActivity", "Lỗi khi đọc file XML", e);
            Toast.makeText(this, "Lỗi khi đọc dữ liệu: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void updateCustomerDataInXML() {
        try {
            File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
            File file = new File(path, "customers.xml");

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();
            Element rootElement = doc.createElement("customers");
            doc.appendChild(rootElement);

            for (Customer customer : customersList) {
                Element customerElement = doc.createElement("customer");
                rootElement.appendChild(customerElement);

                Element nameElement = doc.createElement("name");
                nameElement.appendChild(doc.createTextNode(customer.getName()));
                customerElement.appendChild(nameElement);

                Element pointsElement = doc.createElement("points");
                pointsElement.appendChild(doc.createTextNode(String.valueOf(customer.getPoints())));
                customerElement.appendChild(pointsElement);

                Element phoneElement = doc.createElement("phone");
                phoneElement.appendChild(doc.createTextNode(customer.getPhone()));
                customerElement.appendChild(phoneElement);

                Element createdDateElement = doc.createElement("createdDate"); // Thêm trường ngày tạo
                createdDateElement.appendChild(doc.createTextNode(customer.getCreatedDate()));
                customerElement.appendChild(createdDateElement);
            }

            // Lưu tài liệu XML
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new FileOutputStream(file));
            transformer.transform(source, result);

        } catch (Exception e) {
            Log.e("UsePointsActivity", "Lỗi khi cập nhật file XML", e);
            Toast.makeText(this, "Lỗi khi cập nhật dữ liệu: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                loadCustomerData();
            } else {
                Toast.makeText(this, "Quyền truy cập bộ nhớ ngoài bị từ chối", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && Environment.isExternalStorageManager()) {
            loadCustomerData();
        }
    }
}
