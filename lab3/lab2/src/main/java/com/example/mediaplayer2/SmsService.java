package com.example.mediaplayer2;

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
    public static final String EXTRA_PHONE = "phone_number";
    public static final String EXTRA_MESSAGE = "sms_text";

    public static final String DEFAULT_PHONE = "+375291234567";
    public static final String DEFAULT_MESSAGE = "Привет из SmsService! Лабораторная работа №3, Вариант 2.";

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "SmsService создан (onCreate)");
        Toast.makeText(this, "SmsService создан", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String phone = DEFAULT_PHONE;
        String message = DEFAULT_MESSAGE;

        if (intent != null) {
            if (intent.hasExtra(EXTRA_PHONE)) {
                String p = intent.getStringExtra(EXTRA_PHONE);
                if (p != null && !p.trim().isEmpty()) {
                    phone = p.trim();
                }
            }
            if (intent.hasExtra(EXTRA_MESSAGE)) {
                String m = intent.getStringExtra(EXTRA_MESSAGE);
                if (m != null && !m.trim().isEmpty()) {
                    message = m.trim();
                }
            }
        }

        Log.d(TAG, "SmsService запущен: отправка SMS на " + phone + ", текст: \"" + message + "\"");
        Toast.makeText(this, "Сервис SMS: отправка на " + phone, Toast.LENGTH_SHORT).show();

        sendSms(phone, message);

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
            Log.d(TAG, "SMS успешно отправлено на номер " + phone);
            Toast.makeText(this, "SMS успешно отправлено на " + phone, Toast.LENGTH_LONG).show();
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
        Log.d(TAG, "SmsService остановлен (onDestroy)");
        Toast.makeText(this, "SmsService остановлен", Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }
}
