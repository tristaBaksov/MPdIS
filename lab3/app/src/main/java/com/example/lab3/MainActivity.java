package com.example.lab3;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;



public class MainActivity extends AppCompatActivity implements OnClickListener {
    Button buttonStart, buttonStop,buttonNext;
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
    public void onClick(View src) {
        if (src.getId() == R.id.buttonStart) {
            startService(new Intent(this, MyService.class));
        } else if (src.getId() == R.id.buttonStop) {
            stopService(new Intent(this, MyService.class));
        } else {
            Intent intent = new Intent(MainActivity.this, NextPage.class);
            startActivity(intent);
        }
    }
}
