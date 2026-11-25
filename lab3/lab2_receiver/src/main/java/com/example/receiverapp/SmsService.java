package com.example.receiverapp;

import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

public class SmsService extends Service {
    private static final String TAG = "SmsService";
    public static final String EXTRA_PHONE_NUMBER = "phone_number";
    public static final String EXTRA_SMS_MESSAGE = "sms_text";

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "SmsService создан в проекте ReceiverApp (onCreate)");
        Toast.makeText(this, "SmsService создан (ReceiverApp)", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String phoneNumber = "";
        String smsMessage = "";

        if (intent != null) {
            if (intent.hasExtra(EXTRA_PHONE_NUMBER)) {
                String num = intent.getStringExtra(EXTRA_PHONE_NUMBER);
                if (num != null && !num.trim().isEmpty()) {
                    phoneNumber = num.trim();
                }
            }
            if (intent.hasExtra(EXTRA_SMS_MESSAGE)) {
                String msg = intent.getStringExtra(EXTRA_SMS_MESSAGE);
                if (msg != null && !msg.trim().isEmpty()) {
                    smsMessage = msg.trim();
                }
            }
        }

        if (phoneNumber.isEmpty() || smsMessage.isEmpty()) {
            Log.w(TAG, "SmsService: не передан номер телефона или текст SMS");
            Toast.makeText(this, "Введите номер телефона и текст сообщения", Toast.LENGTH_SHORT).show();
            return START_NOT_STICKY;
        }

        Log.d(TAG, "SmsService: получен запуск из ресивера на номер " + phoneNumber + ", текст: \"" + smsMessage + "\"");
        Toast.makeText(this, "Сервис SMS: отправка на " + phoneNumber, Toast.LENGTH_SHORT).show();

        sendSms(phoneNumber, smsMessage);

        return START_NOT_STICKY;
    }

    private void sendSms(String phone, String message) {
        try {
            SmsManager smsManager;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                smsManager = getSystemService(SmsManager.class);
            } else {
                smsManager = SmsManager.getDefault();
            }

            smsManager.sendTextMessage(phone, null, message, null, null);
            Log.d(TAG, "SMS успешно отправлено через SmsManager на номер " + phone);
            Toast.makeText(this, "SMS успешно отправлено на номер " + phone, Toast.LENGTH_LONG).show();
        } catch (SecurityException e) {
            Log.w(TAG, "Разрешение SEND_SMS не предоставлено, открываем системное приложение SMS", e);
            try {
                Intent smsIntent = new Intent(Intent.ACTION_SENDTO);
                smsIntent.setData(Uri.parse("smsto:" + Uri.encode(phone)));
                smsIntent.putExtra("sms_body", message);
                smsIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(smsIntent);
            } catch (Exception ex) {
                Log.e(TAG, "Ошибка при открытии внешнего SMS приложения", ex);
            }
        } catch (Exception e) {
            Log.e(TAG, "Ошибка при отправке SMS через SmsManager", e);
            Toast.makeText(this, "Ошибка отправки SMS: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "SmsService остановлен (ReceiverApp)");
        Toast.makeText(this, "SmsService остановлен", Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }
}