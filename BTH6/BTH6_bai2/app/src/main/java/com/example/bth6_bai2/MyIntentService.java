package com.example.bth6_bai2;


import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class MyIntentService extends IntentService {

    private static final String TAG = "MyIntentService";

    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // Log bắt đầu xử lý
        Log.d(TAG, "Task in progress");

        // Giả lập tác vụ bằng cách ngủ trong 2 giây
        try {
            Thread.sleep(2000);  // 2000ms = 2 giây
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Log hoàn thành tác vụ
        Log.d(TAG, "Task completed");
    }
}
