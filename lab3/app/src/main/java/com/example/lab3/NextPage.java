package com.example.lab3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class NextPage extends AppCompatActivity {
    private static final int REQUEST_CODE_SMS = 102;

    private EditText edPhoneNumber;
    private EditText edSmsMessage;
    private Button btnNextService;
    private Button bfinish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nextpage);

        edPhoneNumber = findViewById(R.id.edPhoneNumber);
        edSmsMessage = findViewById(R.id.edSmsMessage);
        btnNextService = findViewById(R.id.btnSmsService);
        bfinish = findViewById(R.id.bfinish);

        // permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.SEND_SMS},
                    REQUEST_CODE_SMS);
        }

        if (btnNextService != null) {
            btnNextService.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String phone = edPhoneNumber != null ? edPhoneNumber.getText().toString().trim() : "";
                    if (phone.isEmpty()) {
                        phone = NextService.DEFAULT_PHONE;
                    }

                    String message = edSmsMessage != null ? edSmsMessage.getText().toString().trim() : "";
                    if (message.isEmpty()) {
                        message = NextService.DEFAULT_MESSAGE;
                    }

                    // Формируем Intent для запуска сервиса отправки SMS и передаем данные
                    Intent intent = new Intent(NextPage.this, NextService.class);
                    intent.putExtra(NextService.EXTRA_PHONE, phone);
                    intent.putExtra(NextService.EXTRA_MESSAGE, message);
                    startService(intent);
                }
            });
        }

        if (bfinish != null) {
            bfinish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Остановка сервиса при выходе
                    stopService(new Intent(NextPage.this, NextService.class));
                    finish();
                }
            });
        }
    }
}
