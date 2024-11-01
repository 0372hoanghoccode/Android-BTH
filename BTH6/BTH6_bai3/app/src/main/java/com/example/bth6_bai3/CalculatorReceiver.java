package com.example.bth6_bai3;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class CalculatorReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // Check the action
        if (MainActivity.Key.ACTION_PLUS_NUMBER.equals(intent.getAction())) {
            // Get the numbers from the Intent
            int a = intent.getIntExtra(MainActivity.Key.NUMBER_A, 0);
            int b = intent.getIntExtra(MainActivity.Key.NUMBER_B, 0);

            // Calculate the sum
            int result = a + b;

            // Display the result in a Toast
            Toast.makeText(context, "Result: " + result, Toast.LENGTH_SHORT).show();
        }
    }
}
