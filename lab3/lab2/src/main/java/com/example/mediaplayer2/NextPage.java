package com.example.mediaplayer2;

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
import android.widget.Toast;

public class NextPage extends AppCompatActivity {
    private static final int REQUEST_CODE_SMS = 102;

    private EditText edPhoneNumber;
    private EditText edSmsMessage;
    private Button btnSmsService;
    private Button bfinish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);

        edPhoneNumber = findViewById(R.id.edPhoneNumber);
        edSmsMessage = findViewById(R.id.edSmsMessage);
        btnSmsService = findViewById(R.id.btnSmsService);
        bfinish = findViewById(R.id.bfinish);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.SEND_SMS},
                    REQUEST_CODE_SMS);
        }

        if (btnSmsService != null) {
            btnSmsService.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String phone = edPhoneNumber != null ? edPhoneNumber.getText().toString().trim() : "";
                    String message = edSmsMessage != null ? edSmsMessage.getText().toString().trim() : "";

                    if (phone.isEmpty() || message.isEmpty()) {
                        Toast.makeText(NextPage.this, "Введите номер и текст сообщения", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Intent intent = new Intent(NextPage.this, SmsService.class);
                    intent.putExtra(SmsService.EXTRA_PHONE, phone);
                    intent.putExtra(SmsService.EXTRA_MESSAGE, message);
                    startService(intent);
                }
            });
        }

        if (bfinish != null) {
            bfinish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    stopService(new Intent(NextPage.this, SmsService.class));
                    finish();
                }
            });
        }
    }
}