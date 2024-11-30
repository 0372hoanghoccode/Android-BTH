package com.example.bth6_bai3;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class CalculatorReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        switch (intent.getAction()) {
            case MainActivity.Key.ACTION_PLUS_NUMBER:
                int a = intent.getIntExtra(MainActivity.Key.NUMBER_A, 0);
                int b = intent.getIntExtra(MainActivity.Key.NUMBER_B, 0);
                Toast.makeText(context, "Result: " + (a + b), Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}

