package com.example.receiverapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * BroadcastReceiver, перехватывающий широковещательное намерение CUSTOM_INTENT
 * и запускающий сервис отправки SMS.
 */
public class MyReceiver extends BroadcastReceiver {
    private static final String TAG = "MyReceiver";
    public static final String ACTION_CUSTOM = "com.tutorialspoint.CUSTOM_INTENT";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "Intent Detected. Ресивер перехватил broadcast сообщение!");
        Toast.makeText(context, "Intent Detected. Запуск SmsService...", Toast.LENGTH_LONG).show();

        Intent serviceIntent = new Intent(context, SmsService.class);
        if (intent != null) {
            if (intent.hasExtra(SmsService.EXTRA_PHONE_NUMBER)) {
                serviceIntent.putExtra(SmsService.EXTRA_PHONE_NUMBER, intent.getStringExtra(SmsService.EXTRA_PHONE_NUMBER));
            }
            if (intent.hasExtra(SmsService.EXTRA_SMS_MESSAGE)) {
                serviceIntent.putExtra(SmsService.EXTRA_SMS_MESSAGE, intent.getStringExtra(SmsService.EXTRA_SMS_MESSAGE));
            }
        }

        try {
            context.startService(serviceIntent);
            Log.d(TAG, "SmsService успешно запущен из ресивера");
        } catch (Exception e) {
            Log.e(TAG, "Ошибка запуска SmsService из ресивера", e);
        }
    }
}
