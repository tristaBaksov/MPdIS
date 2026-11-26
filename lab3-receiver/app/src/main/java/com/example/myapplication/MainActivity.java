package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_SMS = 101;

    private EditText edPhoneNumber;
    private EditText edSmsMessage;
    private Button btnBroadcastIntent;
    private Button btnQuit;
    private MyReceiver myReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edPhoneNumber = findViewById(R.id.edPhoneNumber);
        edSmsMessage = findViewById(R.id.edSmsMessage);
        btnBroadcastIntent = findViewById(R.id.btnBroadcastIntent);
        btnQuit = findViewById(R.id.btnQuit);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.SEND_SMS},
                    REQUEST_CODE_SMS);
        }

        btnBroadcastIntent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                broadcastIntent(v);
            }
        });

        btnQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quitApp();
            }
        });
    }

    public void broadcastIntent(View view) {
        String phone = edPhoneNumber != null ? edPhoneNumber.getText().toString().trim() : "";
        if (phone.isEmpty()) {
            phone = SmsService.DEFAULT_PHONE_NUMBER;
        }

        String message = edSmsMessage != null ? edSmsMessage.getText().toString().trim() : "";
        if (message.isEmpty()) {
            message = SmsService.DEFAULT_SMS_MESSAGE;
        }

        Intent intent = new Intent();
        intent.setAction(MyReceiver.ACTION_CUSTOM);
        intent.setPackage(getPackageName());
        intent.putExtra(SmsService.EXTRA_PHONE_NUMBER, phone);
        intent.putExtra(SmsService.EXTRA_SMS_MESSAGE, message);

        sendBroadcast(intent);
    }

    public void quitApp() {
        stopService(new Intent(this, SmsService.class));
        unregisterMyReceiver();
        Toast.makeText(this, "Приложение закрыто", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (myReceiver == null) {
            myReceiver = new MyReceiver();
            IntentFilter filter = new IntentFilter(MyReceiver.ACTION_CUSTOM);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                registerReceiver(myReceiver, filter, ContextCompat.RECEIVER_NOT_EXPORTED);
            } else {
                registerReceiver(myReceiver, filter);
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterMyReceiver();
    }

    private void unregisterMyReceiver() {
        if (myReceiver != null) {
            try {
                unregisterReceiver(myReceiver);
            } catch (IllegalArgumentException ignored) {
            }
            myReceiver = null;
        }
    }
}