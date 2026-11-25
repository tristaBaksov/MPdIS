package com.example.mediaplayer2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements OnClickListener {
    Button buttonStart, buttonStop, buttonNext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonStart = (Button) findViewById(R.id.buttonStart);
        buttonStop = (Button) findViewById(R.id.buttonStop);
        buttonNext = (Button) findViewById(R.id.buttonNext);

        buttonStart.setOnClickListener(this);
        buttonStop.setOnClickListener(this);
        buttonNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View src) {
        int id = src.getId();
        if (id == R.id.buttonStart) {
            startService(new Intent(this, MyService.class));
        } else if (id == R.id.buttonStop) {
            stopService(new Intent(this, MyService.class));
        } else if (id == R.id.buttonNext) {
            startActivity(new Intent(this, NextPage.class));
        }
    }
}
